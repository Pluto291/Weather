package com.pluto.weather.model;

public class CityItem {
    private String cityName;
    private String cityAllInfo;

    public CityItem(String cityName, String cityAllInfo) {
        this.cityName = cityName;
        this.cityAllInfo = cityAllInfo;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCityAllInfo() {
        return cityAllInfo;
    }
}
