package com.pluto.weather.util;

import com.google.gson.Gson;
import com.pluto.weather.gson.BaseData.Weather;
import com.pluto.weather.gson.WeatherForecast;
import com.pluto.weather.gson.WeatherHourly;
import com.pluto.weather.gson.WeatherNow;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HandleUtil {
    public static Weather handleWeather(String responseContent, int type) {
        try {
            JSONObject jsonObject=new JSONObject (responseContent);
            JSONArray jsonArray=jsonObject.getJSONArray ("HeWeather6");
            String jsonContent=jsonArray.get (0).toString ();
            Gson gson=new Gson ();

            if(type == 0) {
                return gson.fromJson (jsonContent, WeatherNow.class);
            } else if(type == 1) {
                return gson.fromJson (jsonContent, WeatherHourly.class);
            } else if(type == 2) {
                return gson.fromJson (jsonContent, WeatherForecast.class);
            }
        } catch(JSONException e) {
            e.printStackTrace ();
        }

        return null;
    }
}
