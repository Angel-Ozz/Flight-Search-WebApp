package com.flightsearch.api.Models;

import java.time.Duration;

public class StopDetails {
    private String airportCode;
    private String airportName;
    private Duration duration;

    public StopDetails() {
    }

    public StopDetails(String airportCode, String airportName, Duration duration) {
        this.airportCode = airportCode;
        this.airportName = airportName;
        this.duration = duration;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }
    
    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

}
