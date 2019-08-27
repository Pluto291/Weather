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
import com.pluto.weather.R;
import com.pluto.weather.adapter.DetailAdapter;
import com.pluto.weather.adapter.ForecastAdapter;
import com.pluto.weather.adapter.HourlyAdapter;
import com.pluto.weather.gson.BaseData.Weather;
import com.pluto.weather.gson.WeatherForecast;
import com.pluto.weather.gson.WeatherHourly;
import com.pluto.weather.gson.WeatherNow;
import com.pluto.weather.util.HandleUtil;
import com.pluto.weather.util.HttpUtil;
import com.pluto.weather.util.SnackbarUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Response;

public class WeatherFragment extends Fragment {
    private final String TAG="WeatherFragment";

    private Context context;
    private String requestCode;
    private TextView tv_title,tv_tmp,tv_wea,tv_fl,tv_detail_title,tv_forecast_title,tv_hourly_title;
    private Button btn_title;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recycler_detail,recycler_forecast,recycler_hourly;

    public WeatherFragment(String requestCode) {
        this.requestCode = requestCode;
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

        btn_title = view.findViewById (R.id.btn_title);

        swipeRefreshLayout = view.findViewById (R.id.swipe_refresh_layout_fragment);
        recycler_detail = view.findViewById (R.id.recycler_detail);
        recycler_forecast = view.findViewById (R.id.recycler_forecast);
        recycler_hourly = view.findViewById (R.id.recycler_hourly);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated (savedInstanceState);

        addListener ();
        if(!readWeather (requestCode)) {
            requestWeather (requestCode);
        }
    }

    public void requestWeather(final String requestCode) {
        List<String>urlList=new ArrayList<> ();
        urlList.add ("https://free-api.heweather.net/s6/weather/now?location=" + requestCode + "&key=bfa0dabcb87f44e3ab0807299a446ff3");
        urlList.add ("https://free-api.heweather.net/s6/weather/hourly?location=" + requestCode + "&key=bfa0dabcb87f44e3ab0807299a446ff3");
        urlList.add ("https://free-api.heweather.net/s6/weather/forecast?location=" + requestCode + "&key=bfa0dabcb87f44e3ab0807299a446ff3");

        for(int i=0;i < 3;i++) {
            final int type=i;

            HttpUtil.sendOkHttpRequest (urlList.get (i), new okhttp3.Callback (){
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace ();

                        getActivity ().runOnUiThread (new Runnable (){
                                @Override
                                public void run() {
                                    SnackbarUtil.showSnackbarOnlyText (swipeRefreshLayout, "获取天气信息失败");
                                }
                            });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String responseContent=response.body ().string ();
                        final Weather weather=HandleUtil.handleWeather (responseContent, type);

                        getActivity ().runOnUiThread (new Runnable (){
                                @Override
                                public void run() {
                                    SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences (context).edit ();
                                    editor.putString (requestCode + ";" + type, responseContent);
                                    editor.apply ();

                                    showWeather (weather);

                                    SnackbarUtil.showSnackbarOnlyText (swipeRefreshLayout, "更新天气数据成功");
                                }
                            });
                    }
                });
        }
    }

    public boolean readWeather(String requestCode) {
        SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences (context);

        for(int i=0;i < 3;i++) {
            String jsonContent=pref.getString (requestCode + ";" + i, null);

            if(jsonContent != null) {
                Weather weather=HandleUtil.handleWeather (jsonContent, i);
                showWeather (weather);
            } else {
                return false;
            }
        }

        return true;
    }

    private void showWeather(Weather weather) {
        LinearLayoutManager layoutManager=new LinearLayoutManager (context);
        LinearLayoutManager layoutManagerHorizontal=new LinearLayoutManager (context);
        layoutManagerHorizontal.setOrientation (LinearLayoutManager.HORIZONTAL);

        if(weather instanceof WeatherNow) {
            WeatherNow now=(WeatherNow)weather;
            tv_title.setText (now.basic.cityName);
            tv_tmp.setText (now.now.tmp + "°");
            tv_wea.setText (now.now.condTxt);
            tv_fl.setText ("体感 " + now.now.fl + " °");

            DetailAdapter detailAdapter=new DetailAdapter (now);
            recycler_detail.setLayoutManager (layoutManager);
            recycler_detail.setAdapter (detailAdapter);
        } else if(weather instanceof WeatherHourly) {
            WeatherHourly hourly=(WeatherHourly)weather;

            HourlyAdapter hourlyAdapter=new HourlyAdapter (hourly.hourlyList, context);
            recycler_hourly.setLayoutManager (layoutManagerHorizontal);
            recycler_hourly.setAdapter (hourlyAdapter);
        } else if(weather instanceof WeatherForecast) {
            WeatherForecast forecast=(WeatherForecast)weather;

            ForecastAdapter forecastAdapter=new ForecastAdapter (forecast.forecastList, context);
            recycler_forecast.setLayoutManager (layoutManagerHorizontal);
            recycler_forecast.setAdapter (forecastAdapter);
        }
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
                    requestWeather (requestCode);

                    swipeRefreshLayout.setRefreshing (false);
                }
            });

        swipeRefreshLayout.setColorSchemeResources (R.color.blue);
    }
}
