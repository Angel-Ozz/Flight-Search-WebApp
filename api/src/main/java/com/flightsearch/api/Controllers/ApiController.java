package com.flightsearch.api.Controllers;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flightsearch.api.Models.FlightResponse;
import com.flightsearch.api.Services.FlightService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/flights")
public class ApiController {

    private final FlightService flightService;

    public ApiController(FlightService flightService) {
        this.flightService = flightService;
        }
    
    //flight offers api call
    @GetMapping("/search")
    public Mono<FlightResponse> searchFlights(
            @RequestParam String originLocationCode,
            @RequestParam String destinationLocationCode,
            @RequestParam String departureDate,
            @RequestParam(required = false) String returnDate,
            @RequestParam int adults,
            @RequestParam boolean nonStop,
            @RequestParam String currencyCode) {
        
    return flightService.searchFlight(originLocationCode, destinationLocationCode, departureDate, Optional.ofNullable(returnDate), adults, nonStop, currencyCode);
    
    }

    //airline codes api call
    @GetMapping("/airline")
    public Mono<String> searchFlights(@RequestParam String airlineCodes) {
        return flightService.searchAirline(airlineCodes);
    }

    //airport codes api call
    @GetMapping("/airport")
    public Mono<String> searchAirport(@RequestParam String keyword) {
        return flightService.searchAirport(keyword);
    }
    
}
