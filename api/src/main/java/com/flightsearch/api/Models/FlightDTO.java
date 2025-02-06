package com.flightsearch.api.Models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class FlightDTO {

    private String id;
    private String departureIata;
    private String departureName;
    private String arrivalIata;
    private String arrivalName;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String carrierCode;
    private String airlineName;
    private Duration totalDuration;
    private List<StopDetails> stops;
    private Double price;
    private Double base;
    private List<Fees> fees;
    private String currency;
    private List<Double> pricePerTraveler;
    private String returnDepartureIata;
    private String returnDepartureName;
    private String returnArrivalIata;
    private String returnArrivalName;
    private LocalDateTime returnDepartureTime;
    private LocalDateTime returnArrivalTime;
    private Duration returnTotalDuration;
    private List<StopDetails> returnStops;
    private String returnAirlineName;

    private List<SegmentDTO> segments;
    private List<SegmentDTO> returnSegments;

    public FlightDTO() {

    }

    public FlightDTO(
            String id,
            String departureIata,
            String departureName,
            String arrivalIata,
            String arrivalName,
            LocalDateTime departureTime,
            LocalDateTime arrivalTime,
            String carrierCode,
            String airlineName,
            Duration totalDuration,
            List<StopDetails> stops,
            double price,
            double base,
            List<Fees> fees,
            String currency,
            List<Double> pricePerTraveler,
            String returnDepartureIata,
            String returnDepartureName,
            String returnArrivalIata,
            String returnArrivalName,
            LocalDateTime returnDepartureTime,
            LocalDateTime returnArrivalTime,
            Duration returnTotalDuration,
            List<StopDetails> returnStops,
            String returnAirlineName,
            List<SegmentDTO> segments,
            List<SegmentDTO> returnSegments
    ) {
        this.id = id;
        this.departureIata = departureIata;
        this.departureName = departureName;
        this.arrivalIata = arrivalIata;
        this.arrivalName = arrivalName;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.carrierCode = carrierCode;
        this.airlineName = airlineName;
        this.totalDuration = totalDuration;
        this.stops = stops;
        this.price = price;
        this.base = base;
        this.fees = fees;
        this.currency = currency;
        this.pricePerTraveler = pricePerTraveler;
        this.returnDepartureIata = returnDepartureIata;
        this.returnDepartureName = returnDepartureName;
        this.returnArrivalIata = returnArrivalIata;
        this.returnArrivalName = returnArrivalName;
        this.returnDepartureTime = returnDepartureTime;
        this.returnArrivalTime = returnArrivalTime;
        this.returnTotalDuration = returnTotalDuration;
        this.returnStops = returnStops;
        this.returnAirlineName = returnAirlineName;

        this.segments = segments;
        this.returnSegments = returnSegments;
    }

    //Lombok didnt work it broke my project so I had to remove it
    // Getters
    public String getId() {
        return id;
    }

    public String getDepartureIata() {
        return departureIata;
    }

    public String getDepartureName() {
        return departureName;
    }

    public String getArrivalIata() {
        return arrivalIata;
    }

    public String getArrivalName() {
        return arrivalName;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public Duration getTotalDuration() {
        return totalDuration;
    }

    public List<StopDetails> getStops() {
        return stops;
    }

    public double getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public List<Double> getPricePerTraveler() {
        return pricePerTraveler;
    }

    public String getReturnDepartureIata() {
        return returnDepartureIata;
    }

    public String getReturnDepartureName() {
        return returnDepartureName;
    }

    public String getReturnArrivalIata() {
        return returnArrivalIata;
    }

    public String getReturnArrivalName() {
        return returnArrivalName;
    }

    public LocalDateTime getReturnDepartureTime() {
        return returnDepartureTime;
    }

    public LocalDateTime getReturnArrivalTime() {
        return returnArrivalTime;
    }

    public Duration getReturnTotalDuration() {
        return returnTotalDuration;
    }

    public List<StopDetails> getReturnStops() {
        return returnStops;
    }

    public String getReturnAirlineName() {
        return returnAirlineName;
    }

    public Double getBase() {
        return base;
    }

    public List<Fees> getFees() {
        return fees;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setDepartureIata(String departureIata) {
        this.departureIata = departureIata;
    }

    public void setDepartureName(String departureName) {
        this.departureName = departureName;
    }

    public void setArrivalIata(String arrivalIata) {
        this.arrivalIata = arrivalIata;
    }

    public void setArrivalName(String arrivalName) {
        this.arrivalName = arrivalName;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public void setTotalDuration(Duration totalDuration) {
        this.totalDuration = totalDuration;
    }

    public void setStops(List<StopDetails> stops) {
        this.stops = stops;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setPricePerTraveler(List<Double> pricePerTraveler) {
        this.pricePerTraveler = pricePerTraveler;
    }

    public void setReturnDepartureIata(String returnDepartureIata) {
        this.returnDepartureIata = returnDepartureIata;
    }

    public void setReturnDepartureName(String returnDepartureName) {
        this.returnDepartureName = returnDepartureName;
    }

    public void setReturnArrivalIata(String returnArrivalIata) {
        this.returnArrivalIata = returnArrivalIata;
    }

    public void setReturnArrivalName(String returnArrivalName) {
        this.returnArrivalName = returnArrivalName;
    }

    public void setReturnDepartureTime(LocalDateTime returnDepartureTime) {
        this.returnDepartureTime = returnDepartureTime;
    }

    public void setReturnArrivalTime(LocalDateTime returnArrivalTime) {
        this.returnArrivalTime = returnArrivalTime;
    }

    public void setReturnTotalDuration(Duration returnTotalDuration) {
        this.returnTotalDuration = returnTotalDuration;
    }

    public void setReturnStops(List<StopDetails> returnStops) {
        this.returnStops = returnStops;
    }

    public void setReturnAirlineName(String returnAirlineName) {
        this.returnAirlineName = returnAirlineName;
    }

    public void setBase(Double base) {
        this.base = base;
    }

    public void setFees(List<Fees> fees) {
        this.fees = fees;
    }

    public List<SegmentDTO> getSegments() {
        return segments;
    }

    public void setSegments(List<SegmentDTO> segments) {
        this.segments = segments;
    }

    public List<SegmentDTO> getReturnSegments() {
        return returnSegments;
    }

    public void setReturnSegments(List<SegmentDTO> returnSegments) {
        this.returnSegments = returnSegments;
    }
}
