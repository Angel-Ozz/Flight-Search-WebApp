package com.flightsearch.api.Models;

public class IncludedCheckedBags {
    private String weight;
    private String weightUnit;

    public IncludedCheckedBags(){

    }

    public IncludedCheckedBags(String weight, String weightUnit) {
        this.weight = weight;
        this.weightUnit = weightUnit;
    }  
    
    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight){
        this.weight = weight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit){
        this.weightUnit = weightUnit;
    }


}
