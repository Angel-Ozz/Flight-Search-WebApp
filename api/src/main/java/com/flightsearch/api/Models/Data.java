package com.flightsearch.api.Models;

import java.util.List;

public class Data {
    private String id;
    private List<Itineraries> itineraries;
    private Price price;
    private List<TravelerPricings> travelerPricings;

    public Data() {
    }

    public Data(String id, List<Itineraries> itineraries, Price price, List<TravelerPricings> travelerPricings
    ) {
        this.id = id;
        this.itineraries = itineraries;
        this.price = price;
        this.travelerPricings = travelerPricings;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Itineraries> getItineraries() {
        return itineraries;
    }

    public void setItineraries(List<Itineraries> itineraries) {
        this.itineraries = itineraries;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }
 
    public List<TravelerPricings> getTravelerPricings() {
        return travelerPricings;
    }

    public void setTravelerPricings(List<TravelerPricings> travelerPricings) {
        this.travelerPricings = travelerPricings;
    }

}
