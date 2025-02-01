package com.flightsearch.api.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FareDetailsBySegment {
    private String segmentId;
    private String cabin;

    @JsonProperty("class")
    private String flightClass;

    private IncludedCheckedBags includedCheckedBags;

    public FareDetailsBySegment(){

    }

    public FareDetailsBySegment(String segmentId, String cabin, String flightClass, IncludedCheckedBags includedCheckedBags){
        this.segmentId = segmentId;
        this.cabin = cabin;
        this.flightClass = flightClass;
        this.includedCheckedBags = includedCheckedBags;
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

    public IncludedCheckedBags getIncludedCheckedBags() {
        return includedCheckedBags;
    }

    public void setIncludedCheckedBags(IncludedCheckedBags includedCheckedBags) {
        this.includedCheckedBags = includedCheckedBags;
    }

}
