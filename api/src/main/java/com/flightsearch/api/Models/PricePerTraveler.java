package com.flightsearch.api.Models;

public class PricePerTraveler {

    private String currency;
    private String total;
    private String base;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getBase() {
        return base;
    }

}
