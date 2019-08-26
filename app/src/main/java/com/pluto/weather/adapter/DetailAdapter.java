package com.pluto.weather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.pluto.weather.R;
import com.pluto.weather.model.Detail;
import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    private List<Detail>details;
    
    public DetailAdapter(List<Detail>details){
        this.details=details;
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
    
    public void setDetails(List<Detail>details){
        this.details=details;
        notifyDataSetChanged();
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
}
