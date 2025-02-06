package com.flightsearch.api.Models;

import java.time.Duration;

public class Segments {

    private String id;
    private Flight departure;
    private Flight arrival;
    private String carrierCode;
    private String number;
    private Aircraft aircraft;
    private Operating operating;
    private Duration duration;

    public Segments() {

    }

    public Segments(String id, Flight departure, Flight arrival, String carrierCode, String number, Aircraft aircraft, Operating operating, Duration duration) {
        this.id = id;
        this.departure = departure;
        this.arrival = arrival;
        this.carrierCode = carrierCode;
        this.number = number;
        this.aircraft = aircraft;
        this.operating = operating;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Flight getDeparture() {
        return departure;
    }

    public void setDeparture(Flight departure) {
        this.departure = departure;
    }

    public Flight getArrival() {
        return arrival;
    }

    public void setArrival(Flight arrival) {
        this.arrival = arrival;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public Operating getOperating() {
        return operating;
    }

    public void setOperating(Operating operating) {
        this.operating = operating;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
