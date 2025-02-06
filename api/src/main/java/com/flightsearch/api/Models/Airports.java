package com.flightsearch.api.Models;

public class Airports {

    private String name;
    private String iataCode;

    public Airports() {

    }

    public Airports(String name, String iataCode) {
        this.name = name;
        this.iataCode = iataCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

}
