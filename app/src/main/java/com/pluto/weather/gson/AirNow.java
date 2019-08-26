package com.pluto.weather.gson;

import com.google.gson.annotations.SerializedName;
import com.pluto.weather.gson.BaseData.Weather;
import com.pluto.weather.gson.BasicData.AirNowCity;
import com.pluto.weather.gson.BasicData.AirNowStation;
import java.util.List;

public class AirNow extends Weather
{
    @SerializedName("air_now_city")
    public AirNowCity airNowCity;
    
    @SerializedName("air_now_station")
    public List<AirNowStation>airNowStations;
}
