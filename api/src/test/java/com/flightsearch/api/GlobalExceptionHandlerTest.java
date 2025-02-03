package com.flightsearch.api;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.flightsearch.api.Exceptions.FlightNotFoundException;
import com.flightsearch.api.Exceptions.GlobalExceptionHandler;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @SuppressWarnings("deprecation")
    @Test
    void testHandleFlightNotFoundException() {
        FlightNotFoundException exception = new FlightNotFoundException("Flight Not Found");
        ResponseEntity<Map<String, String>> response = handler.handleFlightNotFoundException(exception);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Flight Not Found", response.getBody().get("message"));
    }
}
