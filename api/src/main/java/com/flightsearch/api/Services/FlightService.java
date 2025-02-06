package com.flightsearch.api.Services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.flightsearch.api.Exceptions.InvalidRequestException;
import com.flightsearch.api.Exceptions.NotFoundException;
import com.flightsearch.api.Models.AirportResponse;
import com.flightsearch.api.Models.Amenities;
import com.flightsearch.api.Models.FareDetailsBySegment;
import com.flightsearch.api.Models.Fees;
import com.flightsearch.api.Models.FlightDTO;
import com.flightsearch.api.Models.FlightResponse;
import com.flightsearch.api.Models.Itineraries;
import com.flightsearch.api.Models.SegmentDTO;
import com.flightsearch.api.Models.Segments;
import com.flightsearch.api.Models.StopDetails;
import com.flightsearch.api.Models.TravelerPricings;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FlightService {

    private final WebClient webClient;

    public FlightService(WebClient webClient) {
        this.webClient = webClient;
    }

    //flight offers api call
    public Mono<List<FlightDTO>> searchFlight(
            String originLocationCode,
            String destinationLocationCode,
            String departureDate,
            Optional<String> returnDate,
            int adults,
            boolean nonStop,
            String currencyCode,
            String sortBy,
            int page,
            int pageSize) {
        if (originLocationCode.isEmpty() || destinationLocationCode.isEmpty()) {
            throw new InvalidRequestException("Origin and Destination must not be empty");
        }

        return webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/v2/shopping/flight-offers");
                    uriBuilder.queryParam("originLocationCode", originLocationCode);
                    uriBuilder.queryParam("destinationLocationCode", destinationLocationCode);
                    uriBuilder.queryParam("departureDate", departureDate);
                    returnDate.ifPresent(date -> uriBuilder.queryParam("returnDate", date));
                    uriBuilder.queryParam("adults", adults);
                    uriBuilder.queryParam("nonStop", nonStop);
                    uriBuilder.queryParam("currencyCode", currencyCode.trim());

                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToMono(FlightResponse.class)
                .flatMap(flightResponse -> { //flatmap to avoid anidation
                    if (flightResponse == null || flightResponse.getData() == null || flightResponse.getData().isEmpty()) {
                        return Mono.error(new NotFoundException("No flights found for the given criteria"));
                    }
                    return processFlightResponse(flightResponse, sortBy, page, pageSize);
                })
                .doOnTerminate(() -> System.out.println("Request completed"));
    }

    //airline codes api call
    public Mono<String> searchAirline(
            String airlineCode) {
        return webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/v1/reference-data/airlines");

                    uriBuilder.queryParam("airlineCodes", airlineCode.trim());
                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToMono(String.class)
                .doOnTerminate(() -> System.out.println("Request completed"));
    }

    //airport codes api call
    public Mono<AirportResponse> searchAirport(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new InvalidRequestException("Keyword must not be empty");
        }

        return webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/v1/reference-data/locations");
                    uriBuilder.queryParam("subType", "AIRPORT");
                    uriBuilder.queryParam("keyword", keyword.trim());
                    uriBuilder.queryParam("view", "LIGHT");
                    return uriBuilder.build();
                })
                .retrieve()
                .bodyToMono(AirportResponse.class)
                .flatMap(airportResponse -> {
                    if (airportResponse == null || airportResponse.getData() == null || airportResponse.getData().isEmpty()) {
                        return Mono.error(new NotFoundException("Airport not found for the given keyword"));
                    }
                    return Mono.just(airportResponse);
                })
                .doOnTerminate(() -> System.out.println("Request completed"));
    }

    //DTO construction
    private Mono<List<FlightDTO>> processFlightResponse(FlightResponse flightResponse, String sortBy, int page, int pageSize) {
        List<Mono<FlightDTO>> flightMonos = (List<Mono<FlightDTO>>) flightResponse.getData().stream().map(flight -> {

            List<Itineraries> itineraries = flight.getItineraries();

            // departure
            Itineraries firstItinerary = itineraries.get(0);
            Segments firstSegment = firstItinerary.getSegments().get(0);
            Segments lastSegment = firstItinerary.getSegments().get(firstItinerary.getSegments().size() - 1);

            // return opritonal
            Itineraries returnItinerary = itineraries.size() > 1 ? itineraries.get(1) : null;
            Segments returnFirstSegment = returnItinerary != null ? returnItinerary.getSegments().get(0) : null;
            Segments returnLastSegment = returnItinerary != null ? returnItinerary.getSegments().get(returnItinerary.getSegments().size() - 1) : null;

            List<Fees> fees = flight.getPrice().getFees() != null ? flight.getPrice().getFees() : new ArrayList<>();

            List<TravelerPricings> travelerPricings = flight.getTravelerPricings();
            List<Double> totalPricePerTrav = travelerPricings.stream()
                    .map(travelerPricing -> Double.valueOf(travelerPricing.getPrice().getTotal()))
                    .collect(Collectors.toList());

            String departureIata = firstSegment.getDeparture().getIataCode();
            String arrivalIata = lastSegment.getArrival().getIataCode();
            String returnDepartureIata = returnFirstSegment != null ? returnFirstSegment.getDeparture().getIataCode() : "";
            String returnArrivalIata = returnLastSegment != null ? returnLastSegment.getArrival().getIataCode() : "";

            // stops dep here i use mono, and streams instead of simple iteration because this was supposed to be merged with the endpoint call 
            //Int Stream bbasically a for loop
            List<Mono<StopDetails>> stopDetailsMonos = IntStream.range(1, firstItinerary.getSegments().size())
                    .mapToObj(i -> {
                        Segments prevSegment = firstItinerary.getSegments().get(i - 1);
                        Segments currentSegment = firstItinerary.getSegments().get(i);

                        LocalDateTime prevArrival = prevSegment.getArrival().getAt();
                        LocalDateTime currentDeparture = currentSegment.getDeparture().getAt();

                        Duration layoverDuration = Duration.between(prevArrival, currentDeparture);

                        //mono.just encapsules the obj
                        return Mono.just(new StopDetails(
                                currentSegment.getDeparture().getIataCode(),
                                "",
                                layoverDuration
                        ));
                    })
                    .collect(Collectors.toList());

            // stops return 
            List<Mono<StopDetails>> returnStopDetailsMonos = returnItinerary != null
                    ? IntStream.range(1, returnItinerary.getSegments().size())
                            .mapToObj(i -> {
                                Segments prevSegment = returnItinerary.getSegments().get(i - 1);
                                Segments currentSegment = returnItinerary.getSegments().get(i);

                                LocalDateTime prevArrival = prevSegment.getArrival().getAt();
                                LocalDateTime currentDeparture = currentSegment.getDeparture().getAt();

                                Duration layoverDuration = Duration.between(prevArrival, currentDeparture);

                                return Mono.just(new StopDetails(
                                        currentSegment.getDeparture().getIataCode(),
                                        "",
                                        layoverDuration
                                ));
                            })
                            .collect(Collectors.toList())
                    : new ArrayList<>();

            // error 429 needs handling, change this mono with endpoint calls 
            Mono<String> departureAirportNameMono = Mono.just("");
            Mono<String> arrivalAirportNameMono = Mono.just("");
            Mono<String> returnDepartureAirportNameMono = returnItinerary != null ? Mono.just("") : Mono.just("");
            Mono<String> returnArrivalAirportNameMono = returnItinerary != null ? Mono.just("") : Mono.just("");
            Mono<List<StopDetails>> stopsMono = Flux.merge(stopDetailsMonos).collectList();
            Mono<List<StopDetails>> returnStopsMono = Flux.merge(returnStopDetailsMonos).collectList();

            Mono<String> airlineName = Mono.just("");
            Mono<String> returnAirlineNameMono = returnItinerary != null ? Mono.just("") : Mono.just("");

            //handlin return info if null
            LocalDateTime returnDepartureTime = returnFirstSegment != null ? returnFirstSegment.getDeparture().getAt() : null;
            LocalDateTime returnArrivalTime = returnLastSegment != null ? (LocalDateTime) returnLastSegment.getArrival().getAt() : null;
            Duration returnTotalDuration = returnItinerary != null ? returnItinerary.getDuration() : Duration.ZERO;

            // SEGMENTS
            List<SegmentDTO> segmentDetails = new ArrayList<>();
            List<SegmentDTO> returnSegmentDetails = new ArrayList<>();

            // getting dep segs at the end i did use iterarion, it was simple
            for (int i = 0; i < firstItinerary.getSegments().size(); i++) {
                Segments segment = firstItinerary.getSegments().get(i);

                // map the segms with the details
                TravelerPricings travelerPricing = flight.getTravelerPricings().get(0);
                List<FareDetailsBySegment> fareDetails = travelerPricing.getFareDetailsBySegment();

                FareDetailsBySegment fareDetail = fareDetails.stream()
                        .filter(detail -> detail.getSegmentId().equals(segment.getId()))
                        .findFirst()
                        .orElse(null);

                List<Amenities> amenities = fareDetail != null ? fareDetail.getAmenities() : new ArrayList<>();

                // Layover
                Duration layover = Duration.ZERO;
                if (i > 0) {
                    LocalDateTime prevArrival = firstItinerary.getSegments().get(i - 1).getArrival().getAt();
                    layover = Duration.between(prevArrival, segment.getDeparture().getAt());
                }

                segmentDetails.add(new SegmentDTO(
                        segment.getId(),
                        segment.getDeparture().getAt(),
                        segment.getArrival().getAt(),
                        segment.getCarrierCode(),
                        segment.getNumber(),
                        segment.getAircraft().getCode(),
                        fareDetail != null ? fareDetail.getCabin() : "",
                        fareDetail != null ? fareDetail.getFlightClass() : "",
                        amenities,
                        layover
                ));
            }

            //RETURN segms if exist
            if (returnItinerary != null) {
                for (int i = 0; i < returnItinerary.getSegments().size(); i++) {
                    Segments segment = returnItinerary.getSegments().get(i);

                    TravelerPricings travelerPricing = flight.getTravelerPricings().get(0);
                    List<FareDetailsBySegment> fareDetails = travelerPricing.getFareDetailsBySegment();

                    FareDetailsBySegment fareDetail = fareDetails.stream()
                            .filter(detail -> detail.getSegmentId().equals(segment.getId()))
                            .findFirst()
                            .orElse(null);

                    List<Amenities> amenities = fareDetail != null ? fareDetail.getAmenities() : new ArrayList<>();

                    Duration layover = Duration.ZERO;
                    if (i > 0) {
                        LocalDateTime prevArrival = returnItinerary.getSegments().get(i - 1).getArrival().getAt();
                        layover = Duration.between(prevArrival, segment.getDeparture().getAt());
                    }

                    returnSegmentDetails.add(new SegmentDTO(
                            segment.getId(),
                            segment.getDeparture().getAt(),
                            segment.getArrival().getAt(),
                            segment.getCarrierCode(),
                            segment.getNumber(),
                            segment.getAircraft().getCode(),
                            fareDetail != null ? fareDetail.getCabin() : "",
                            fareDetail != null ? fareDetail.getFlightClass() : "",
                            amenities,
                            layover
                    ));
                }
            }

            //FLIGHT DTO BUILDDDD
            return returnAirlineNameMono.flatMap(returnAirlineName
                    -> Mono.zip(departureAirportNameMono, arrivalAirportNameMono, returnDepartureAirportNameMono, returnArrivalAirportNameMono, stopsMono, returnStopsMono, airlineName)
                            .map(tuple -> new FlightDTO(
                            flight.getId(),
                            departureIata,
                            tuple.getT1(), // departure airport name
                            arrivalIata,
                            tuple.getT2(), // arribale airport name
                            firstSegment.getDeparture().getAt(),
                            lastSegment.getArrival().getAt(),
                            firstSegment.getCarrierCode(),
                            tuple.getT7(), // carrier dep
                            firstItinerary.getDuration(),
                            tuple.getT5(), // stops dep
                            Double.parseDouble(flight.getPrice().getTotal()),
                            Double.parseDouble(flight.getPrice().getBase()),
                            fees,
                            flight.getPrice().getCurrency(),
                            totalPricePerTrav,
                            returnDepartureIata,
                            tuple.getT3(), // return airport name de
                            returnArrivalIata,
                            tuple.getT4(), // return airport name arr
                            returnDepartureTime,
                            returnArrivalTime,
                            returnTotalDuration,
                            tuple.getT6(), // stops arr
                            returnAirlineName,
                            segmentDetails,
                            returnSegmentDetails
                    ))
            );
        }).collect(Collectors.toList());

        return Flux.merge(flightMonos)
                .collectList()
                .map(flights -> {
                    // sort
                    if ("price".equalsIgnoreCase(sortBy)) {
                        flights.sort(Comparator.comparingDouble(FlightDTO::getPrice));
                    } else if ("duration".equalsIgnoreCase(sortBy)) {
                        flights.sort(Comparator.comparing(FlightDTO::getTotalDuration));
                    } else {

                    }

                    // Pag
                    int fromIndex = page * pageSize;
                    if (fromIndex >= flights.size()) {
                        return new ArrayList<FlightDTO>();
                    }
                    int toIndex = Math.min(fromIndex + pageSize, flights.size());
                    return flights.subList(fromIndex, toIndex);
                });
    }

}
