package com.flightsearch.api.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.flightsearch.api.Models.AirportResponse;
import com.flightsearch.api.Models.FlightDTO;
import com.flightsearch.api.Services.FlightService;

import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/flights")
public class ApiController {

    private final FlightService flightService;

    public ApiController(FlightService flightService) {
        this.flightService = flightService;
    }

    //flight offers api call
    @GetMapping("/search")
    public Mono<List<FlightDTO>> searchFlights(
            @RequestParam String originLocationCode,
            @RequestParam String destinationLocationCode,
            @RequestParam String departureDate,
            @RequestParam(required = false) String returnDate,
            @RequestParam int adults,
            @RequestParam boolean nonStop,
            @RequestParam String currencyCode,
            @RequestParam(defaultValue = "null") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize) {

        Optional<String> processedReturnDate = ("null".equals(returnDate) || returnDate == null || returnDate.isEmpty())
                ? Optional.empty()
                : Optional.of(returnDate);

        return flightService.searchFlight(originLocationCode, destinationLocationCode, departureDate, processedReturnDate, adults, nonStop, currencyCode, sortBy, page, pageSize);
    }

    //airline codes api call
    @GetMapping("/airline")
    public Mono<String> searchFlights(@RequestParam String airlineCodes) {
        return flightService.searchAirline(airlineCodes);
    }

    //airport codes api call
    @GetMapping("/airport")
    public Mono<AirportResponse> searchAirport(@RequestParam String keyword) {
        return flightService.searchAirport(keyword);
    }

}
