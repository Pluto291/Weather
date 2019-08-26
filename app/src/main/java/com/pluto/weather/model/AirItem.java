package com.pluto.weather.model;

public class AirItem
{
    private String contaminant;
    private String value;
    
    public AirItem(String contaminant,String value){
        this.contaminant=contaminant;
        this.value=value;
    }
    
    public String getContaminant(){
        return contaminant;
    }
    
    public String getValue(){
        return value;
    }
}
