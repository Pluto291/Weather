package com.pluto.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.pluto.weather.R;
import com.pluto.weather.gson.BasicData.Hourly;
import com.pluto.weather.util.WeatherIconUtil;
import java.util.List;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.ViewHolder> {
    private List<Hourly>hourlyList;
    private Context context;

    public HourlyAdapter(List<Hourly>hourlyList,Context context) {
        this.hourlyList = hourlyList;
        this.context=context;
    }

    public void setData(List<Hourly>hourlyList,Context context) {
        this.hourlyList = hourlyList;
        this.context=context;
        notifyDataSetChanged ();
    }

    @Override
    public HourlyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from (parent.getContext ()).inflate (R.layout.item_hourly, parent, false);
        ViewHolder holder=new ViewHolder (view);

        return holder;
    }

    @Override
    public void onBindViewHolder(HourlyAdapter.ViewHolder holder, int position) {
        Hourly hourly=hourlyList.get (position);
        holder.tv_time.setText (formatHourlyTime (hourly.time));
        holder.tv_tmp.setText (hourly.tmp + "°");
        Glide.with(context).load(WeatherIconUtil.getIcon(hourly.condCode,false)).into(holder.iv_weather);
    }

    @Override
    public int getItemCount(){

        return hourlyList.size ();
    }

    private String formatHourlyTime(String hourlyTime) {
        return hourlyTime.split (" ", 2)[1];
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_time,tv_tmp;
        ImageView iv_weather;

        public ViewHolder(View itemView) {
            super (itemView);

            tv_time = itemView.findViewById (R.id.tv_time);
            tv_tmp = itemView.findViewById (R.id.tv_tmp);
            iv_weather = itemView.findViewById (R.id.iv_weather);
        }
    }
}
