package com.flightsearch.api.Models;

public class Operating {
    private String carrierCode;

    public Operating(){

    }

    public Operating(String carrierCode){
        this.carrierCode = carrierCode;
    }

    public String getCarrierCode(){
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode){
        this.carrierCode = carrierCode;
    }

}
