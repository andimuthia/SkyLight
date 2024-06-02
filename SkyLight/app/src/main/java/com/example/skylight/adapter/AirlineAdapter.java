package com.example.skylight.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylight.AirlinesProfileActivity;
import com.example.skylight.R;
import com.example.skylight.helper.DatabaseHelper;
import com.example.skylight.models.Airline;
import com.example.skylight.models.Destination;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AirlineAdapter extends RecyclerView.Adapter<AirlineAdapter.ViewHolder> {

    public static List<Airline> airlineList;
    private Context context;
    private static OnItemClickListener clickListener;

    public AirlineAdapter(List<Airline> airlineList, Context context) {
        this.airlineList = airlineList;
        this.context = context;
    }

    public void setAirlineList(List<Airline> airlineList) {
        this.airlineList = airlineList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(Airline airline);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public AirlineAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_airlines, parent, false);
        return new ViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Airline airline = airlineList.get(position);
        holder.tvAirlineName.setText(airline.getName());
        holder.imageView.setImageResource(R.drawable.globe);
        holder.itemView.setTag(airline);
    }

    @Override
    public int getItemCount() {
        return airlineList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAirlineName;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener clickListener) {
            super(itemView);
            tvAirlineName = itemView.findViewById(R.id.tvAirlineName);
            imageView = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(v -> {
                if (clickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        clickListener.onItemClick((Airline) v.getTag());
                    }
                }
            });
        }
    }
}