package com.pluto.weather.util;

import com.google.gson.Gson;
import com.pluto.weather.gson.AirNow;
import com.pluto.weather.gson.BaseData.Weather;
import com.pluto.weather.gson.CitySearch;
import com.pluto.weather.gson.WeatherForecast;
import com.pluto.weather.gson.WeatherHourly;
import com.pluto.weather.gson.WeatherNow;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherUtil {
    private static final String JSON_ARRAY_NAME="HeWeather";
    private static final String WEATHER="weather/";
    private static final String AIR="air/";

    private static final String NOW="now?";
    private static final String HOURLY="hourly?";
    private static final String FORECAST="forecast?";

    private static final String LOCATION="location=";
    private static final String KEY="key=";

    private static final String WEATHER_API_PREF="https://free-api.heweather.net/s6/";
    private static final String CITY_API_PREF="https://search.heweather.net/find?";
    private static final String API_KEY="bfa0dabcb87f44e3ab0807299a446ff3";

    private static Weather weather=null;
    
    public static Weather request(String requestCode, final Type type) {
        String url=getRequestUrl (requestCode, type);

        if(url != null) {
            HttpUtil.sendOkHttpRequest (url, new okhttp3.Callback (){
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace ();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseContent=response.body ().string ();
                        weather=handle (responseContent, type);
                    }
                });
        }
        
        return weather;
    }

    public static Weather handle(String response, Type type) {
        Class targetClass=getHandleTarget (type);

        try {
            JSONObject jsonObject=new JSONObject (response);
            JSONArray jsonArray=jsonObject.getJSONArray (JSON_ARRAY_NAME);
            String weatherContent=jsonArray.get (0).toString ();

            if(targetClass != null)
                weather = new Gson ().fromJson (weatherContent, targetClass);

        } catch(Exception e) {
            e.printStackTrace ();
        }

        return weather;
    }
    
    public static void save(Weather weather,Type type){
        
    }

    private static String getRequestUrl(String requestCode, Type type) {
        String url=null;

        switch(type) {
            case WEATHER_NOW:
                url = WEATHER_API_PREF + WEATHER + NOW + LOCATION + requestCode + "&" + KEY + API_KEY;
                break;
            case WEATHER_HOURLY:
                url = WEATHER_API_PREF + WEATHER + HOURLY + LOCATION + requestCode + "&" + KEY + API_KEY;
                break;
            case WEATHER_FORECAST:
                url = WEATHER_API_PREF + WEATHER + FORECAST + LOCATION + requestCode + "&" + KEY + API_KEY;
                break;
            case AIR_NOW:
                url = WEATHER_API_PREF + AIR + NOW + LOCATION + requestCode + "&" + KEY + API_KEY;
                break;
            case FIND_CITY:
                url = CITY_API_PREF + LOCATION + requestCode + "&" + KEY + API_KEY;
                break;
        }

        return url;
    }

    private static Class getHandleTarget(Type type) {
        Class targetClass=null;

        switch(type) {
            case WEATHER_NOW:
                targetClass = WeatherNow.class;
                break;
            case WEATHER_HOURLY:
                targetClass = WeatherHourly.class;
                break;
            case WEATHER_FORECAST:
                targetClass = WeatherForecast.class;
                break;
            case AIR_NOW:
                targetClass = AirNow.class;
                break;
            case FIND_CITY:
                targetClass = CitySearch.class;
                break;
        }

        return targetClass;
    }

    public enum Type {
        WEATHER_NOW(),

        WEATHER_HOURLY,

        WEATHER_FORECAST,

        AIR_NOW,

        FIND_CITY;
    }
}
