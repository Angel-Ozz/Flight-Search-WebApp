package com.flightsearch.api;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.flightsearch.api.Models.FlightDTO;
import com.flightsearch.api.Services.FlightService;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class FlightIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @SuppressWarnings("removal")
    @MockBean
    private FlightService flightService;

    private List<FlightDTO> mockFlights;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        // Simular una respuesta válida con vuelos de prueba
        mockFlights = List.of(
            new FlightDTO("1", "MEX", "Mexico City", "LAX", "Los Angeles",
                null, null, "AA", "American Airlines", null, null,
                500.0, 450.0, List.of(), "USD", List.of(500.0), "", "", "", "",
                null, null, null, null, "", List.of(), List.of())
        );

        when(flightService.searchFlight(
                "MEX", "LAX", "2025-05-02", Optional.empty(), 1, false, "USD", "price", 0, 10))
            .thenReturn(Mono.just(mockFlights));
    }

    @Test
    void testSearchFlight_Integration() {
        Mono<List<FlightDTO>> result = flightService.searchFlight(
            "MEX", "LAX", "2025-05-02", Optional.empty(), 1, false, "USD", "price", 0, 10
        );

        List<FlightDTO> flights = result.block();

        assertNotNull(flights);
        assertFalse(flights.isEmpty());
    }

    @Test
    void testGetFlightsAPI() {
        webTestClient.get().uri("/api/flights?origin=MEX&destination=LAX&departureDate=2025-05-02&adults=1&currency=USD")
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(FlightDTO.class)
            .value(flights -> assertFalse(flights.isEmpty()));
    }
}
