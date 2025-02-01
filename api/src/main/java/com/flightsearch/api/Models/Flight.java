package com.flightsearch.api.Models;

import java.time.LocalDateTime;

public class Flight {
    private String iataCode;
    private LocalDateTime at;

    public Flight(){

    }

    public Flight(String iataCode, LocalDateTime at) {
        this.iataCode = iataCode;
        this.at = at;
    }
    
    public String getIataCode() {
        return iataCode;
    }
    
    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }
    
    public LocalDateTime getAt() {
        return at;
    }
    
    public void setAt(LocalDateTime at) {
        this.at = at;
    }
}
