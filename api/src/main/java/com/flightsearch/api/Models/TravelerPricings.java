package com.flightsearch.api.Models;

import java.util.List;

public class TravelerPricings {
    private String travelerId;
    private PricePerTraveler price;
    private List<FareDetailsBySegment> fareDetailsBySegment;

    public TravelerPricings(){

    }

    public TravelerPricings(String travelerId, PricePerTraveler price, List<FareDetailsBySegment> fareDetailsBySegment) {
        this.travelerId = travelerId;
        this.price = price;
        this.fareDetailsBySegment = fareDetailsBySegment;
        
    }

    public String getTravelerId(){
        return this.travelerId;
    }

    public void setTravelerId(String travelerId){
        this.travelerId = travelerId;
    }

    public PricePerTraveler getPrice() {
        return price;
    }

    public void setPrice(PricePerTraveler price) {
        this.price = price;
    }

    public List<FareDetailsBySegment> getFareDetailsBySegment() {
        return fareDetailsBySegment;
    }

    public void setFareDetailsBySegment(List<FareDetailsBySegment> fareDetailsBySegment) {
        this.fareDetailsBySegment = fareDetailsBySegment;
    }

    public PricePerTraveler getPricePerTraveler() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPricePerTraveler'");
    }
}
