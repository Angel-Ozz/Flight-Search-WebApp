package com.flightsearch.api.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flightsearch.api.Services.FlightService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/flights")
public class ApiController {

    private final FlightService flightService;

    public ApiController(FlightService flightService) {
        this.flightService = flightService;
        }

    @GetMapping("/search")
    public Mono<String> searchFlights(
            @RequestParam String originLocationCode,
            @RequestParam String destinationLocationCode,
            @RequestParam String departureDate,
            @RequestParam int adults) {
        
    return flightService.searchFlight(originLocationCode, destinationLocationCode, departureDate, adults);
    
    }
}
