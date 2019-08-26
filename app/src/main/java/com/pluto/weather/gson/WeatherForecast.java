package com.pluto.weather.gson;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import com.pluto.weather.gson.BaseData.Weather;
import com.pluto.weather.gson.BasicData.Forecast;

public class WeatherForecast extends Weather {
    @SerializedName("daily_forecast")
    public List<Forecast>forecastList;
}
