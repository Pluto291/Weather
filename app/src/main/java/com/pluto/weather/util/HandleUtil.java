package com.pluto.weather.util;

import com.google.gson.Gson;
import com.pluto.weather.gson.AirNow;
import com.pluto.weather.gson.CitySearch;
import com.pluto.weather.gson.WeatherForecast;
import com.pluto.weather.gson.WeatherHourly;
import com.pluto.weather.gson.WeatherNow;
import org.json.JSONArray;
import org.json.JSONObject;
import com.pluto.weather.gson.BaseData.Weather;

public class HandleUtil {

    public static WeatherNow handleWeatherNow(String response) {
        try {
            JSONObject jsonObject=new JSONObject (response);
            JSONArray jsonArray=jsonObject.getJSONArray ("HeWeather6");
            String weatherContent=jsonArray.get (0).toString ();

            return new Gson ().fromJson (weatherContent, WeatherNow.class);
        } catch(Exception e) {
            e.printStackTrace ();
        }

        return null;
    }

    public static WeatherHourly handleWeatherHourly(String response) {
        try {
            JSONObject jsonObject=new JSONObject (response);
            JSONArray jsonArray=jsonObject.getJSONArray ("HeWeather6");
            String weatherContent=jsonArray.get (0).toString ();

            return new Gson ().fromJson (weatherContent, WeatherHourly.class);
        } catch(Exception e) {
            e.printStackTrace ();
        }

        return null;
    }

    public static WeatherForecast handleWeatherForecast(String response) {
        try {
            JSONObject jsonObject=new JSONObject (response);
            JSONArray jsonArray=jsonObject.getJSONArray ("HeWeather6");
            String weatherContent=jsonArray.get (0).toString ();

            return new Gson ().fromJson (weatherContent, WeatherForecast.class);
        } catch(Exception e) {
            e.printStackTrace ();
        }

        return null;
    }

    public static AirNow handleAir(String response) {
        try {
            JSONObject jsonObject=new JSONObject (response);
            JSONArray jsonArray=jsonObject.getJSONArray ("HeWeather6");
            String airContent=jsonArray.get (0).toString ();

            return new Gson ().fromJson (airContent, AirNow.class);
        } catch(Exception e) {
            e.printStackTrace ();
        }

        return null;
    }

    public static CitySearch handleCitySearch(String response) {
        try {
            JSONObject jsonObject=new JSONObject (response);
            JSONArray jsonArray=jsonObject.getJSONArray ("HeWeather6");
            String airContent=jsonArray.get (0).toString ();

            return new Gson ().fromJson (airContent, CitySearch.class);
        } catch(Exception e) {
            e.printStackTrace ();
        }

        return null;
    }
}
