package com.pluto.weather.util;

import com.pluto.weather.R;

public class WeatherIconUtil {
    private static final int[]WEATHER_ICONS={
        R.drawable.weather_clear_day,
        R.drawable.weather_clear_night,
        R.drawable.weather_cloudy,
        R.drawable.weather_fog,
        R.drawable.weather_hail,
        R.drawable.weather_haze,
        R.drawable.weather_partly_cloudy_day,
        R.drawable.weather_partly_cloudy_night,
        R.drawable.weather_rain,
        R.drawable.weather_sleet,
        R.drawable.weather_snow,
        R.drawable.weather_thunder,
        R.drawable.weather_thunderstorm,
        R.drawable.weather_wind};

    public static int getIcon(String condCode, boolean isNight) {
        int code=Integer.valueOf (condCode);

        if(code == 100) {
            //晴天
            if(isNight)
                return WEATHER_ICONS[1];
            else
                return WEATHER_ICONS[0];
        } else if(code == 101 || code == 102 || code == 103) {
            //有云
            if(isNight)
                return WEATHER_ICONS[7];
            else
                return WEATHER_ICONS[6];
        } else if(code == 104) {
            //阴天
            return WEATHER_ICONS[2];
        } else if(code >= 200 && code <= 213) {
            //有风
            return WEATHER_ICONS[13];
        } else if(code >= 300 && code < 400) {
            //有雨
            return WEATHER_ICONS[8];
        } else if(code >= 400 && code < 500) {
            //有雪
            if(code == 404 || code == 405 || code == 406)
            //雨雪天气
                return WEATHER_ICONS[9];
            else
                return WEATHER_ICONS[10];
        } else if(code >= 500 && code < 600) {
            //雾
            if(code == 500 || code == 501 || code == 509 || code == 510 || code == 514 || code == 515)
                return WEATHER_ICONS[3];
            else
                return WEATHER_ICONS[5];
        } else {
            return -1;
        }
    }
}
