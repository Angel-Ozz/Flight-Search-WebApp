package com.flightsearch.api.Models;

import java.util.List;

public class Price {
    private String currency;
    private String total;
    private String base;
    private List<Fees> fees;
    private String grandTotal;

    public Price() {
    }

    public Price(String currency, String total, String base, List<Fees> fees, String grandTotal) {
        this.currency = currency;
        this.total = total;
        this.base = base;
        this.fees = fees;
        this.grandTotal = grandTotal;
    }

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

    public void setBase(String base) {
        this.base = base;
    }

    public List<Fees> getFees() {
        return fees;
    }

    public void setFees(List<Fees> fees) {
        this.fees = fees;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

}
