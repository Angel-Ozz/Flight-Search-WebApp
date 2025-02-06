package com.flightsearch.api.Models;

public class Fees {

    private String amount;
    private String type;

    public Fees() {

    }

    public Fees(String amount, String type) {
        this.amount = amount;
        this.type = type;
    }

    public String getAmount() {
        return this.amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
