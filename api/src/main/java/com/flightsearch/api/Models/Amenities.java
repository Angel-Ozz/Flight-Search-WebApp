package com.flightsearch.api.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Amenities {

    private String description;
    @JsonProperty("isChargeable")
    private boolean chargeable;

    public Amenities() {

    }

    public Amenities(String description, boolean chargeable) {
        this.description = description;
        this.chargeable = chargeable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getChargeable() {
        return chargeable;
    }

    public void setChargeable(boolean chargeable) {
        this.chargeable = chargeable;
    }

}
