package com.flightsearch.api.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class FlightService {

     private final WebClient webClient;

    public FlightService(WebClient webClient){
        this.webClient = webClient;
    }

    public Mono<String> searchFlight(String originLocationCode, String destinationLocationCode, String departureDate, int adults) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/v2/shopping/flight-offers")
                .queryParam("originLocationCode", originLocationCode)
                .queryParam("destinationLocationCode", destinationLocationCode)
                .queryParam("departureDate", departureDate)
                .queryParam("adults", adults)
                .build())
            .retrieve()
            .bodyToMono(String.class)
            .doOnTerminate(() -> System.out.println("Request completed"))
            .doOnSuccess(response -> System.out.println("Response: " + response));
    }
    

}
