package com.flightsearch.api.Services;

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
            List<Mono<FlightDTO>> flightMonos = flightResponse.getData().stream().map(flight -> {
                List<Itineraries> itineraries = flight.getItineraries();
                Itineraries firstItinerary = itineraries.get(0);
        
                Segments firstSegment = firstItinerary.getSegments().get(0);
                Segments lastSegment = firstItinerary.getSegments().get(firstItinerary.getSegments().size() - 1);

                List<TravelerPricings> travelerPricings = flight.getTravelerPricings();
                List<Double> totalPricePerTrav = travelerPricings.stream()
                    .map(travelerPricing -> Double.valueOf(travelerPricing.getPrice().getTotal()))
                    .collect(Collectors.toList());

                
                String departureIata = firstSegment.getDeparture().getIataCode();
                String arrivalIata = lastSegment.getArrival().getIataCode();

                //String airlineIata = firstSegment.getCarrierCode();

        
                List<Mono<StopDetails>> stopDetailsMonos = firstItinerary.getSegments().stream()
                    .skip(1)
                    .map(segment -> {
                        String stopIata = segment.getDeparture().getIataCode();
                        // need to solve error 429
                        // return searchAirport(stopIata)
                        //     .map(stopName -> new StopDetails(stopIata, stopName, segment.getDuration()));
                
                return Mono.just(new StopDetails(stopIata, "", segment.getDuration()));
                    })
                    .collect(Collectors.toList());
        
                //Mono<String> departureAirportNameMono = searchAirport(departureIata);
                //Mono<String> arrivalAirportNameMono = searchAirport(arrivalIata);
                //Mono<List<StopDetails>> stopsMono = Flux.merge(stopDetailsMonos).collectList();
                //Mono<String> airlineName = searchAirline(airlineIata);

                // need to solve error 429
                Mono<String> departureAirportNameMono = Mono.just("");
                Mono<String> arrivalAirportNameMono = Mono.just("");
                Mono<List<StopDetails>> stopsMono = Flux.merge(stopDetailsMonos).collectList();

                Mono<String> airlineName = Mono.just("");
        
                return Mono.zip(departureAirportNameMono, arrivalAirportNameMono, stopsMono, airlineName)
                    .map(tuple -> new FlightDTO(
                        flight.getId(),
                        departureIata,
                        tuple.getT1(), // departure name
                        arrivalIata,
                        tuple.getT2(),  // arrival name
                        firstSegment.getDeparture().getAt(),
                        lastSegment.getArrival().getAt(),
                        firstSegment.getCarrierCode(),
                        tuple.getT4(), // airline name
                        firstItinerary.getDuration(),
                        tuple.getT3(), // stops name
                        Double.parseDouble(flight.getPrice().getTotal()),
                        flight.getPrice().getCurrency(),
                        totalPricePerTrav
                        
                        //left the tuples to not modified a lot the code and use it in the future if i can solve error 429, also 
                     
                    ));
            }).collect(Collectors.toList());
        
            return Flux.merge(flightMonos)
                .collectList()
                .map(flights -> {
                    //sort
                    if ("price".equalsIgnoreCase(sortBy)) {
                        flights.sort(Comparator.comparingDouble(FlightDTO::getPrice));
                    } else if ("duration".equalsIgnoreCase(sortBy)) {
                    flights.sort(Comparator.comparing(FlightDTO::getTotalDuration));
                }
                /* 
                if ("desc".equalsIgnoreCase(sortOrder)) {
                    Collections.reverse(flights);
                }
                */
                // pags
                int fromIndex = page * pageSize;
                int toIndex = Math.min(fromIndex + pageSize, flights.size());
    
                return flights.subList(fromIndex, toIndex);
            });
    }
    



}
