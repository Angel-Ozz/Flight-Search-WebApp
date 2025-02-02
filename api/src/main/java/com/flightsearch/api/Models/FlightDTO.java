package com.flightsearch.api.Models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class FlightDTO {
    private String id;
    private String departureAirportCode;
    private String airportNameDeparture;
    private String arrivalAirportCode;
    private String airportNameArrival;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String airlineCode;
    private String airlineName;
    private String operatingAirlineCode; 
    private Duration totalDuration;
    private List<StopDetails> stops;
    private double price;
    private String currency;
    private List<Double> totalPricePerTrav;

    public FlightDTO(){

    }

    public FlightDTO(String id,String departureAirportCode, String airportNameDeparture, String arrivalAirportCode, String airportNameArrival,
    LocalDateTime departureTime, LocalDateTime arrivalTime, String airlineCode, String airlineName,
    //String operatingAirlineCode,
     Duration totalDuration, List<StopDetails> stops,double price, String currency, List<Double> totalPricePerTrav) {
        this.id = id;
        this.departureAirportCode = departureAirportCode;
        this.airportNameDeparture = airportNameDeparture;
        this.arrivalAirportCode = arrivalAirportCode;
        this.airportNameArrival = airportNameArrival;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.airlineCode = airlineCode;
        this.airlineName = airlineName;
        //this.operatingAirlineCode = operatingAirlineCode;
        this.totalDuration = totalDuration;
        this.stops = stops;
        this.price = price;
        this.currency = currency;
        this.totalPricePerTrav = totalPricePerTrav;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartureAirportCode() {
        return departureAirportCode;
    }

    public void setDepartureAirportCode(String departureAirportCode) {
        this.departureAirportCode = departureAirportCode;
    }

    public String getAirportNameDeparture(){
        return airportNameDeparture;
    }

    public void setAirportNameDeparture(String airportNameDeparture){
        this.airportNameDeparture = airportNameDeparture;
    }

    public String getArrivalAirportCode() {
        return arrivalAirportCode;
    }

    public void setArrivalAirportCode(String arrivalAirportCode) {
        this.arrivalAirportCode = arrivalAirportCode;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getOperatingAirlineCode() {
        return operatingAirlineCode;
    }

    public void setOperatingAirlineCode(String operatingAirlineCode) {
        this.operatingAirlineCode = operatingAirlineCode;
    }

    public Duration getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Duration totalDuration) {
        this.totalDuration = totalDuration;
    }

    public List<StopDetails> getStops() {
        return stops;
    }

    public void setStops(List<StopDetails> stops) {
        this.stops = stops;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAirportNameArrival(){
        return airportNameArrival;
    }

    public void setAirportNameArrival(String airportNameArrival){
        this.airportNameArrival = airportNameArrival;
    }

    public List<Double> getTotalPricePerTrav(){
        return totalPricePerTrav;
    }

    public void setTotalPricePerTrav(List<Double> totalPricePerTrav){
        this.totalPricePerTrav = totalPricePerTrav;
    }    

}
