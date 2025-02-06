package com.flightsearch.api.Models;

import java.util.List;

public class FlightResponse {

    private Meta meta;
    private List<Data> data;

    public FlightResponse() {
    }

    public FlightResponse(Meta meta, List<Data> data) {
        this.meta = meta;
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

}
