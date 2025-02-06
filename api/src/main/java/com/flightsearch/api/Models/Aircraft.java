package com.flightsearch.api.Models;

public class Aircraft {

    private String code;

    public Aircraft() {

    }

    public Aircraft(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
