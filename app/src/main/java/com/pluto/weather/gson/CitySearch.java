package com.pluto.weather.gson;

import com.google.gson.annotations.SerializedName;
import com.pluto.weather.gson.BaseData.Weather;
import com.pluto.weather.gson.BasicData.Basic;
import java.util.List;

public class CitySearch extends Weather
{
    @SerializedName("basic")
    public List<Basic>basicList;
}
