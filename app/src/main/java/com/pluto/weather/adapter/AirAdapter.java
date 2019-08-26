package com.pluto.weather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.pluto.weather.R;
import com.pluto.weather.gson.AirNow;
import com.pluto.weather.model.AirItem;
import java.util.ArrayList;
import java.util.List;

public class AirAdapter extends RecyclerView.Adapter<AirAdapter.ViewHolder>
{
    private List<AirItem>airItems;

    public AirAdapter(AirNow airNow){
     airItems=new ArrayList<>();
     handleAir(airNow);
    }

    @Override
    public AirAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_air,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AirAdapter.ViewHolder holder, int position) {
        AirItem item=airItems.get(position);
        holder.tv_contaminant.setText(item.getContaminant());
        holder.tv_value.setText(item.getValue());
    }

    @Override
    public int getItemCount() {
        return airItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_contaminant,tv_value;

        public ViewHolder(View itemView) {
            super (itemView);

            tv_contaminant = itemView.findViewById (R.id.tv_contaminant);
            tv_value = itemView.findViewById (R.id.tv_valve);
        }
    }
    
    public void setAirItems(AirNow airNow){
        airItems.clear();
        handleAir(airNow);
        
        notifyDataSetChanged();
    }
    
    private void handleAir(AirNow airNow){
        airItems.add(new AirItem("PM2.5",airNow.airNowCity.pm25+"μg/m³"));
        airItems.add(new AirItem("PM10",airNow.airNowCity.pm10+"μg/m³"));
        airItems.add(new AirItem("SO2",airNow.airNowCity.so2+"μg/m³"));
        airItems.add(new AirItem("NO2",airNow.airNowCity.no2+"μg/m³"));
        airItems.add(new AirItem("O3",airNow.airNowCity.o3+"μg/m³"));
        airItems.add(new AirItem("CO",airNow.airNowCity.co+"μg/m³"));
    }
}
