package com.pluto.weather.gson.BasicData;

import com.google.gson.annotations.SerializedName;

public class Hourly {
    //云量
    public String cloud;
    
    //实况天气状况代码
    @SerializedName("cond_code")
    public String condCode;

    //实况天气状况描述
    @SerializedName("cond_txt")
    public String condTxt;
    
    //露点温度
    public String dew;
    
    //相对湿度
    public String hum;
    
    //降水概率
    public String pop;
    
    //大气压强
    public String pres;
    
    //预报时间
    public String time;
    
    //温度
    public String tmp;
    
    //风向角度
    @SerializedName("wind_deg")
    public String windDeg;

    //风向
    @SerializedName("wind_dir")
    public String windDir;

    //风力
    @SerializedName("wind_sc")
    public String windSc;

    //风速
    @SerializedName("wind_spd")
    public String windSpd;
}
