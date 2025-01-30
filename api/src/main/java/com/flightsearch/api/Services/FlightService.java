package com.flightsearch.api.Services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class FlightService {

     private final WebClient webClient;

    public FlightService(WebClient webClient){
        this.webClient = webClient;
    }

      //flight offers api call
    public Mono<String> searchFlight(
        String originLocationCode, 
        String destinationLocationCode, 
        String departureDate, 
        Optional<String> returnDate, 
        int adults, 
        boolean nonStop, 
        String currencyCode) 
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
        .bodyToMono(String.class)
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

    

}
