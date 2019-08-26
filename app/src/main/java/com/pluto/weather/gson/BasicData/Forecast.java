package com.pluto.weather.gson.BasicData;

import com.google.gson.annotations.SerializedName;

public class Forecast {
    //白天天气状况代码
    @SerializedName("cond_code_d")
    public String condCodeD;
    
    //夜间天气状况代码
    @SerializedName("cond_code_n")
    public String condCodeN;
    
    //白天天气状况描述
    @SerializedName("cond_txt_d")
    public String condTxtD;

    //夜间天气状况描述
    @SerializedName("cond_txt_n")
    public String condTxtN;
    
    //预报日期
    public String date;
    
    //相对湿度
    public String hum;
    
    //降水量
    public String pcpn;
    
    //降水概率
    public String pop;
    
    //大气压强
    public String pres;
    
    //日出时间
    public String sr;
    
    //日落时间
    public String ss;
    
    //最高温度
    @SerializedName("tmp_max")
    public String tmpMax;
    
    //最低温度
    @SerializedName("tmp_min")
    public String tmpMin;
    
    //紫外线强度
    @SerializedName("uv_index")
    public String uvIndex;
    
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
