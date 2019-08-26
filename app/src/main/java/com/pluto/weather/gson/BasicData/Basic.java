package com.pluto.weather.gson.BasicData;

import com.google.gson.annotations.SerializedName;

public class Basic {
    //地区/城市ID
    @SerializedName("cid")
    public String cityId;
    
    //地区／城市名称
    @SerializedName("location")
    public String cityName;
    
    //该地区／城市的上级城市
    @SerializedName("parent_city")
    public String parentCity;
    
    //该地区／城市所属行政区域
    @SerializedName("admin_area")
    public String adminArea;
    
    //该地区／城市所属国家名称
    @SerializedName("cnty")
    public String countryName;
    
    //地区／城市纬度
    public String lat;
    
    //地区／城市纬度
    public String lon;
}
