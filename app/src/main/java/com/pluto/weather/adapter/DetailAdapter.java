package com.pluto.weather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.pluto.weather.R;
import com.pluto.weather.model.Detail;
import java.util.List;
import com.pluto.weather.gson.WeatherNow;
import java.util.ArrayList;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    private WeatherNow weatherNow;
    private List<Detail>details=new ArrayList<>();
    
    public DetailAdapter(WeatherNow weatherNow){
        this.weatherNow=weatherNow;
        handleDetails(weatherNow);
    }
    
    @Override
    public DetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(DetailAdapter.ViewHolder holder, int position) {
        Detail detail=details.get(position);
        holder.tv_item.setText(detail.getItem());
        holder.tv_info.setText(detail.getInfo());
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_item;
        TextView tv_info;

        public ViewHolder(View itemView) {
            super (itemView);

            tv_item = itemView.findViewById (R.id.tv_item);
            tv_info = itemView.findViewById (R.id.tv_info);
        }
    }
    
    private void handleDetails(WeatherNow weatherNow) {
        details.add (new Detail ("大气压强", weatherNow.now.pres + "HPA"));
        details.add (new Detail ("降水量", weatherNow.now.pcpn));
        details.add (new Detail ("相对湿度", weatherNow.now.hum + "%"));
        details.add (new Detail ("能见度", weatherNow.now.vis + "KM"));
        details.add (new Detail ("云量", weatherNow.now.cloud));
    }
}
