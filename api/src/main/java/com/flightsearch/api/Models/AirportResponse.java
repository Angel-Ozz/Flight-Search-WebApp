package com.flightsearch.api.Models;

import java.util.List;

public class AirportResponse {
    private Meta meta;
    private List<Airports> data;

    public AirportResponse(){

    }

    public AirportResponse(Meta meta, List<Airports> data) {
        this.meta = meta;
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Airports> getData() {
        return data;
    }
    
    public void setData(List<Airports> data) {
        this.data = data;
    }

}
