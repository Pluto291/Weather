package com.pluto.weather.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.pluto.weather.R;
import com.pluto.weather.adapter.AirAdapter;
import com.pluto.weather.adapter.DetailAdapter;
import com.pluto.weather.adapter.ForecastAdapter;
import com.pluto.weather.adapter.HourlyAdapter;
import com.pluto.weather.gson.AirNow;
import com.pluto.weather.gson.WeatherForecast;
import com.pluto.weather.gson.WeatherHourly;
import com.pluto.weather.gson.WeatherNow;
import com.pluto.weather.model.Detail;
import com.pluto.weather.util.HttpUtil;
import com.pluto.weather.util.HandleUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Response;

public class WeatherFragment extends Fragment {
    private final String TAG="WeatherFragment";

    private Context context;
    private TextView tv_title,tv_tmp,tv_wea,tv_fl,tv_detail_title,tv_forecast_title,tv_hourly_title,
    tv_air_title,tv_aqi,tv_level;
    private Button btn_title;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recycler_detail,recycler_forecast,recycler_hourly,recycler_air;

    private String coordinate;
    private WeatherNow weatherNow;
    private WeatherHourly weatherHourly;
    private WeatherForecast weatherForcast;
    private AirNow airNow;
    private List<Detail>details;

    public WeatherFragment(String coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate (R.layout.fragment_weather, container, false);

        context = this.getActivity ();

        tv_title = view.findViewById (R.id.tv_title);
        tv_title.setText ("定位中....");
        tv_tmp = view.findViewById (R.id.tv_tmp);
        tv_wea = view.findViewById (R.id.tv_wea);
        tv_fl = view.findViewById (R.id.tv_fl);
        tv_detail_title = view.findViewById (R.id.tv_detail_title);
        tv_detail_title.setText ("详细数据");
        tv_forecast_title = view.findViewById (R.id.tv_forecast_title);
        tv_forecast_title.setText ("每日概览");
        tv_hourly_title = view.findViewById (R.id.tv_hourly_title);
        tv_hourly_title.setText ("小时概览");
        tv_air_title = view.findViewById (R.id.tv_air_title);
        tv_air_title.setText ("空气质量");
        tv_aqi = view.findViewById (R.id.tv_aqi);
        tv_level = view.findViewById (R.id.tv_level);

        btn_title = view.findViewById (R.id.btn_title);

        swipeRefreshLayout = view.findViewById (R.id.swipe_refresh_layout_fragment);
        recycler_detail = view.findViewById (R.id.recycler_detail);
        recycler_forecast = view.findViewById (R.id.recycler_forecast);
        recycler_hourly = view.findViewById (R.id.recycler_hourly);
        recycler_air = view.findViewById (R.id.recycler_air);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated (savedInstanceState);

        addListener ();
        details = new ArrayList<> ();

        SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences (context);
        String weatherNowContent=pref.getString ("weather_now" + coordinate, null);
        String weatherForecastContent=pref.getString ("weather_forecast" + coordinate, null);
        String weatherHourlyContent=pref.getString ("weather_hourly" + coordinate, null);
        String airContent=pref.getString ("airNow" + coordinate, null);

        if(weatherNowContent != null && weatherForecastContent != null && weatherHourlyContent != null &&
           airContent != null) {
            weatherNow = HandleUtil.handleWeatherNow (weatherNowContent);
            handleDetails (weatherNow);
            showWeatherNow (weatherNow);

            weatherForcast = HandleUtil.handleWeatherForecast (weatherForecastContent);
            showWeatherForecast (weatherForcast);

            weatherHourly = HandleUtil.handleWeatherHourly (weatherHourlyContent);
            showWeatherHourly (weatherHourly);

            airNow = HandleUtil.handleAir (airContent);
            showAir (airNow);
        } else {

            if(coordinate != null) {
                requestWeatherNow (coordinate);
                requestWeatherForecast (coordinate);
                requestWeatherHourly (coordinate);
            } else {
                Toast.makeText (context, "缺少定位权限，请前往授予", Toast.LENGTH_SHORT).show ();
            }
        }
    }

    public void requestWeatherNow(final String coordinate) {
        String weatherNowUrl="https://free-api.heweather.net/s6/weather/now?location=" + coordinate + "&key=bfa0dabcb87f44e3ab0807299a446ff3";
        HttpUtil.sendOkHttpRequest (weatherNowUrl, new okhttp3.Callback (){
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace ();

                    getActivity ().runOnUiThread (new Runnable (){
                            @Override
                            public void run() {
                                Toast.makeText (context, "获取天气信息失败", Toast.LENGTH_SHORT).show ();
                            }
                        });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseContent=response.body ().string ();
                    weatherNow = HandleUtil.handleWeatherNow (responseContent);

                    getActivity ().runOnUiThread (new Runnable (){
                            @Override
                            public void run() {
                                if(weatherNow != null && "ok".equals (weatherNow.status)) {
                                    SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences (context).edit ();
                                    editor.putString ("weather_now" + coordinate, responseContent);
                                    editor.apply ();

                                    handleDetails (weatherNow);
                                    showWeatherNow (weatherNow);
                                } else {
                                    Toast.makeText (context, "获取天气信息失败", Toast.LENGTH_SHORT).show ();
                                }
                            }
                        });
                }
            });
    }

    public void requestWeatherHourly(final String coordinate) {
        String weatherHourlyUrl="https://free-api.heweather.net/s6/weather/hourly?location=" + coordinate + "&key=bfa0dabcb87f44e3ab0807299a446ff3";
        HttpUtil.sendOkHttpRequest (weatherHourlyUrl, new okhttp3.Callback (){
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace ();

                    getActivity ().runOnUiThread (new Runnable (){
                            @Override
                            public void run() {
                                Toast.makeText (context, "获取天气信息失败", Toast.LENGTH_SHORT).show ();
                            }
                        });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseContent=response.body ().string ();
                    weatherHourly = HandleUtil.handleWeatherHourly (responseContent);

                    getActivity ().runOnUiThread (new Runnable (){
                            @Override
                            public void run() {
                                if(weatherHourly != null && "ok".equals (weatherHourly.status)) {
                                    SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences (context).edit ();
                                    editor.putString ("weather_hourly" + coordinate, responseContent);
                                    editor.apply ();

                                    showWeatherHourly (weatherHourly);
                                } else {
                                    Toast.makeText (context, "获取天气信息失败", Toast.LENGTH_SHORT).show ();
                                }
                            }
                        });
                }
            });
    }

    public void requestWeatherForecast(final String coordinate) {
        String weatherForecastUrl="https://free-api.heweather.net/s6/weather/forecast?location=" + coordinate + "&key=bfa0dabcb87f44e3ab0807299a446ff3";
        HttpUtil.sendOkHttpRequest (weatherForecastUrl, new okhttp3.Callback (){

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace ();

                    getActivity ().runOnUiThread (new Runnable (){
                            @Override
                            public void run() {
                                Toast.makeText (context, "获取天气信息失败", Toast.LENGTH_SHORT).show ();
                            }
                        });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseContent=response.body ().string ();
                    weatherForcast = HandleUtil.handleWeatherForecast (responseContent);

                    getActivity ().runOnUiThread (new Runnable (){
                            @Override
                            public void run() {
                                if(weatherForcast != null && "ok".equals (weatherForcast.status)) {
                                    SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences (context).edit ();
                                    editor.putString ("weather_forecast" + coordinate, responseContent);
                                    editor.apply ();

                                    showWeatherForecast (weatherForcast);
                                } else {
                                    Toast.makeText (context, "获取天气信息失败", Toast.LENGTH_SHORT).show ();
                                }
                            }
                        });
                }
            });
    }

    public void requestAir(final String coordinate) {
        String airUrl="https://free-api.heweather.net/s6/air/now?location=" + weatherNow.basic.parentCity + "&key=bfa0dabcb87f44e3ab0807299a446ff3";
        HttpUtil.sendOkHttpRequest (airUrl, new okhttp3.Callback (){

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace ();

                    getActivity ().runOnUiThread (new Runnable (){
                            @Override
                            public void run() {
                                Toast.makeText (context, "获取天气信息失败", Toast.LENGTH_SHORT).show ();
                            }
                        });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String responseContent=response.body ().string ();
                    airNow = HandleUtil.handleAir (responseContent);

                    getActivity ().runOnUiThread (new Runnable (){
                            @Override
                            public void run() {
                                if(airNow != null && "ok".equals (airNow.status)) {
                                    SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences (context).edit ();
                                    editor.putString ("airNow" + coordinate, responseContent);
                                    editor.apply ();

                                    showAir (airNow);
                                } else {
                                    Toast.makeText (context, "获取天气信息失败", Toast.LENGTH_SHORT).show ();
                                }
                            }
                        });
                }
            });
    }

    private void showWeatherNow(WeatherNow weatherNow) {
        tv_title.setText (weatherNow.basic.cityName);
        tv_tmp.setText (weatherNow.now.tmp + "°");
        tv_wea.setText (weatherNow.now.condTxt);
        tv_fl.setText ("体感 " + weatherNow.now.fl + " °");

        DetailAdapter detailAdapter=new DetailAdapter (details);
        LinearLayoutManager layoutManager = new LinearLayoutManager (context);
        recycler_detail.setLayoutManager (layoutManager);
        recycler_detail.setAdapter (detailAdapter);
    }

    private void showWeatherForecast(WeatherForecast weatherForecast) {
        ForecastAdapter forecastAdapter=new ForecastAdapter (weatherForecast.forecastList, context);
        LinearLayoutManager layoutManagerHorizontal=new LinearLayoutManager (context);
        layoutManagerHorizontal.setOrientation (LinearLayoutManager.HORIZONTAL);
        recycler_forecast.setLayoutManager (layoutManagerHorizontal);
        recycler_forecast.setAdapter (forecastAdapter);
    }

    private void showWeatherHourly(WeatherHourly weatherHourly) {
        HourlyAdapter hourlyAdapter=new HourlyAdapter (weatherHourly.hourlyList, context);
        LinearLayoutManager layoutManagerHorizontal=new LinearLayoutManager (context);
        layoutManagerHorizontal.setOrientation (LinearLayoutManager.HORIZONTAL);
        recycler_hourly.setLayoutManager (layoutManagerHorizontal);
        recycler_hourly.setAdapter (hourlyAdapter);
    }

    private void showAir(AirNow airNow) {
        tv_aqi.setText (airNow.airNowCity.aqi);
        tv_level.setText ("空气 " + airNow.airNowCity.qlty);

        AirAdapter airAdapter=new AirAdapter (airNow);
        LinearLayoutManager layoutManager=new LinearLayoutManager (context);
        recycler_air.setLayoutManager (layoutManager);
        recycler_air.setAdapter (airAdapter);
    }

    private void handleDetails(WeatherNow weatherNow) {
        Detail pres=new Detail ("大气压强", weatherNow.now.pres + "HPA");
        Detail pcpn=new Detail ("降水量", weatherNow.now.pcpn);
        Detail hum=new Detail ("相对湿度", weatherNow.now.hum + "%");
        Detail vis=new Detail ("能见度", weatherNow.now.vis + "KM");
        Detail cloud=new Detail ("云量", weatherNow.now.cloud);

        details.add (pres);
        details.add (pcpn);
        details.add (hum);
        details.add (vis);
        details.add (cloud);
    }

    private void addListener() {
        btn_title.setOnClickListener (new OnClickListener (){
                @Override
                public void onClick(View v) {
                    
                }
            });

        swipeRefreshLayout.setOnRefreshListener (new SwipeRefreshLayout.OnRefreshListener (){
                @Override
                public void onRefresh() {

                    if(coordinate != null) {
                        details.clear ();
                        requestWeatherNow (coordinate);
                        requestWeatherForecast (coordinate);
                        requestWeatherHourly (coordinate);
                        requestAir (coordinate);
                    } else {
                        Toast.makeText (context, "缺少定位权限，请前往授予", Toast.LENGTH_SHORT).show ();
                    }

                    swipeRefreshLayout.setRefreshing (false);

                    Snackbar.make (swipeRefreshLayout, "更新数据完成", Snackbar.LENGTH_SHORT).show ();
                }
            });

        swipeRefreshLayout.setColorSchemeResources (R.color.blue);
    }
}
