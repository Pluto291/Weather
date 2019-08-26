package com.pluto.weather.gson.BasicData;

import com.google.gson.annotations.SerializedName;

public class AirNowStation 
{
    //数据发布时间
    @SerializedName("pub_time")
    public String pubTime;
    
    //站点名称
    @SerializedName("air_sta")
    public String stationName;

    //站点ID
    public String asid;
    
    //站点纬度
    public String lat;
    
    //站点经度
    public String lon;
    
    //空气质量指数
    public String aqi;

    //主要污染物
    public String main;

    //空气质量
    public String qlty;

    //PM10
    public String pm10;

    //PM2.5
    public String pm25;

    //二氧化氮
    public String no2;

    //二氧化硫
    public String so2;

    //一氧化碳
    public String co;

    //臭氧
    public String o3;
}
