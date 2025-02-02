package com.flightsearch.api.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FareDetailsBySegment {
    private String segmentId;
    private String cabin;

    @JsonProperty("class")
    private String flightClass;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Amenities> amenities;

    public FareDetailsBySegment(){

    }

    public FareDetailsBySegment(String segmentId, String cabin, String flightClass, List<Amenities> amenities){
        this.segmentId = segmentId;
        this.cabin = cabin;
        this.flightClass = flightClass;
        this.amenities = amenities;
    }

    public String getSegmentId() {
        return segmentId;
    }
    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }

    public String getCabin() {
        return cabin;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }

    public List<Amenities> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Amenities> amenities) {
        this.amenities = amenities;
    }

}
