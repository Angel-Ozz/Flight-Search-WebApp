package com.flightsearch.api.Services;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.flightsearch.api.Models.FlightDTO;
import com.flightsearch.api.Models.FlightResponse;
import com.flightsearch.api.Models.Itineraries;
import com.flightsearch.api.Models.Segments;
import com.flightsearch.api.Models.StopDetails;
import com.flightsearch.api.Models.TravelerPricings;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FlightService {

     private final WebClient webClient;

    public FlightService(WebClient webClient){
        this.webClient = webClient;
    }

      //flight offers api call
    public Mono<List<FlightDTO>>  searchFlight(
        String originLocationCode, 
        String destinationLocationCode, 
        String departureDate, 
        Optional<String> returnDate, 
        int adults, 
        boolean nonStop, 
        String currencyCode,
        String sortBy,  
        int page,                  
        int pageSize) 
    {
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
        .flatMap(flightResponse -> processFlightResponse(flightResponse, sortBy, page, pageSize)) //FlatMap because the Mono objects were becoming anidated
        .doOnTerminate(() -> System.out.println("Request completed"));
    }

    //airline codes api call
    public Mono<String> searchAirline(
        String airlineCode) 
    {
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
    public Mono<String> searchAirport(
        String keyword) 
    {
        return webClient.get()
            .uri(uriBuilder -> {
                uriBuilder.path("/v1/reference-data/locations");
            
                uriBuilder.queryParam("subType", "AIRPORT");
                uriBuilder.queryParam("keyword", keyword.trim());
                uriBuilder.queryParam("view", "LIGHT");
            return uriBuilder.build();
        })
        .retrieve()
        .bodyToMono(String.class)
        .doOnTerminate(() -> System.out.println("Request completed"));
    }




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
            
         
            List<TravelerPricings> travelerPricings = flight.getTravelerPricings();
            List<Double> totalPricePerTrav = travelerPricings.stream()
                .map(travelerPricing -> Double.valueOf(travelerPricing.getPrice().getTotal()))
                .collect(Collectors.toList());
    
            String departureIata = firstSegment.getDeparture().getIataCode();
            String arrivalIata = lastSegment.getArrival().getIataCode();
            String returnDepartureIata = returnFirstSegment != null ? returnFirstSegment.getDeparture().getIataCode() : "";
            String returnArrivalIata = returnLastSegment != null ? returnLastSegment.getArrival().getIataCode() : "";
    
            // stops dep
            List<Mono<StopDetails>> stopDetailsMonos = firstItinerary.getSegments().stream()
                .skip(1)
                .map(segment -> Mono.just(new StopDetails(segment.getDeparture().getIataCode(), "", segment.getDuration())))
                .collect(Collectors.toList());
    
            // stops return
            List<Mono<StopDetails>> returnStopDetailsMonos = returnItinerary != null ? returnItinerary.getSegments().stream()
                .skip(1)
                .map(segment -> Mono.just(new StopDetails(segment.getDeparture().getIataCode(), "", segment.getDuration())))
                .collect(Collectors.toList()) : new ArrayList<>();
    
            // error 429 needs handling, change this mono with endpoint calls 
            Mono<String> departureAirportNameMono = Mono.just(""); 
            Mono<String> arrivalAirportNameMono = Mono.just("");
            Mono<String> returnDepartureAirportNameMono = returnItinerary != null ? Mono.just("") : Mono.just("");
            Mono<String> returnArrivalAirportNameMono = returnItinerary != null ? Mono.just("") : Mono.just("");
            Mono<List<StopDetails>> stopsMono = Flux.merge(stopDetailsMonos).collectList();
            Mono<List<StopDetails>> returnStopsMono = Flux.merge(returnStopDetailsMonos).collectList();
            
            Mono<String> airlineName = Mono.just("");
            Mono<String> returnAirlineNameMono = returnItinerary != null ? Mono.just("") : Mono.just("");

            LocalDateTime returnDepartureTime = returnFirstSegment != null ? returnFirstSegment.getDeparture().getAt() : null;
            LocalDateTime returnArrivalTime = returnLastSegment != null ? (LocalDateTime) returnLastSegment.getArrival().getAt() : null;
            Duration returnTotalDuration = returnItinerary != null ? returnItinerary.getDuration() : Duration.ZERO;

    
            return returnAirlineNameMono.flatMap(returnAirlineName -> 
                Mono.zip(departureAirportNameMono, arrivalAirportNameMono, returnDepartureAirportNameMono, returnArrivalAirportNameMono, stopsMono, returnStopsMono, airlineName)
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
                        returnAirlineName
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
                }
    
                // Pag
                int fromIndex = page * pageSize;
                int toIndex = Math.min(fromIndex + pageSize, flights.size());
                return flights.subList(fromIndex, toIndex);
            });
    }
    
    
    
    



}
