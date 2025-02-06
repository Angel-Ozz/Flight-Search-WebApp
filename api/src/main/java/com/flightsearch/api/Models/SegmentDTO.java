package com.flightsearch.api.Models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class SegmentDTO {

    private String segmentId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String airlineCode;
    private String flightNumber;
    private String aircraftType;
    private String cabin;
    private String flightClass;
    private List<Amenities> amenities;
    private Duration layoverTime;

    public SegmentDTO() {
    }

    public SegmentDTO(
            String segmentId,
            LocalDateTime departureTime,
            LocalDateTime arrivalTime,
            String airlineCode,
            String flightNumber,
            String aircraftType,
            String cabin,
            String flightClass,
            List<Amenities> amenities,
            Duration layoverTime
    ) {
        this.segmentId = segmentId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.airlineCode = airlineCode;
        this.flightNumber = flightNumber;
        this.aircraftType = aircraftType;
        this.cabin = cabin;
        this.flightClass = flightClass;
        this.amenities = amenities;
        this.layoverTime = layoverTime;
    }

    // Getters
    public String getSegmentId() {
        return segmentId;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public String getCabin() {
        return cabin;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public List<Amenities> getAmenities() {
        return amenities;
    }

    public Duration getLayoverTime() {
        return layoverTime;
    }

    // Setters
    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }

    public void setAmenities(List<Amenities> amenities) {
        this.amenities = amenities;
    }

    public void setLayoverTime(Duration layoverTime) {
        this.layoverTime = layoverTime;
    }

}
