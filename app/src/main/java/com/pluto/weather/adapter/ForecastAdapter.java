package com.pluto.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.pluto.weather.R;
import com.pluto.weather.gson.BasicData.Forecast;
import com.pluto.weather.util.WeatherIconUtil;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {
    private static final String TAG="ForecastAdapter";

    private List<Forecast>forecastList;
    private Context context;

    public ForecastAdapter(List<Forecast>forecastList,Context context) {
        this.forecastList = forecastList;
        this.context=context;
    }

    public void setData(List<Forecast>forecastList,Context context) {
        this.forecastList = forecastList;
        this.context=context;
        notifyDataSetChanged ();
    }

    @Override
    public ForecastAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from (parent.getContext ()).inflate (R.layout.item_forecast, parent, false);
        ViewHolder holder=new ViewHolder (view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ForecastAdapter.ViewHolder holder, int position) {
        Forecast forecast=forecastList.get (position);
        holder.tv_date.setText (formatForecastDate (forecast.date));
        holder.tv_tmp_max.setText (forecast.tmpMax + "°");
        holder.tv_tmp_min.setText (forecast.tmpMin + "°");
        Glide.with(context).load(WeatherIconUtil.getIcon(forecast.condCodeD,false)).into(holder.iv_weather_d);
        Glide.with(context).load(WeatherIconUtil.getIcon(forecast.condCodeN,true)).into(holder.iv_weather_n);
    }

    @Override
    public int getItemCount() {
        Log.e (TAG, "getItemCount");

        return forecastList.size ();
    }

    private String formatForecastDate(String forecastDate) {
        return forecastDate.split ("-", 2)[1];
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_date,tv_tmp_max,tv_tmp_min;
        ImageView iv_weather_d,iv_weather_n;

        public ViewHolder(View itemView) {
            super (itemView);

            tv_date = itemView.findViewById (R.id.tv_date);
            tv_tmp_max = itemView.findViewById (R.id.tv_tmp_max);
            tv_tmp_min = itemView.findViewById (R.id.tv_tmp_min);
            iv_weather_d = itemView.findViewById (R.id.iv_weather_d);
            iv_weather_n = itemView.findViewById (R.id.iv_weather_n);
        }
    }
}
