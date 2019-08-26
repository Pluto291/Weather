package com.pluto.weather.gson;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import com.pluto.weather.gson.BasicData.Basic;
import com.pluto.weather.gson.BaseData.Weather;
import com.pluto.weather.gson.BasicData.Hourly;

public class WeatherHourly extends Weather {

    @SerializedName("hourly")
    public List<Hourly>hourlyList;
}
