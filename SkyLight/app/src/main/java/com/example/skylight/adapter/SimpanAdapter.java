package com.example.skylight.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylight.R;
import com.example.skylight.models.Airline;

import java.util.List;

public class SimpanAdapter extends RecyclerView.Adapter<SimpanAdapter.ViewHolder> {

    public static List<Airline> airlinesList;
    private Context context;
    private static OnItemClickListener clickListener;

    public SimpanAdapter(List<Airline> airlinesList, Context context) {
        this.airlinesList = airlinesList;
        this.context = context;
    }

    public void setAirlinesList(List<Airline> airlinesList) {
        this.airlinesList = airlinesList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Airline airline);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public SimpanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_airlines, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpanAdapter.ViewHolder holder, int position) {
        Airline airline = airlinesList.get(position);
        holder.tvAirlineName.setText(airline.getName());
        holder.images.setImageResource(R.drawable.globe);
        holder.itemView.setTag(airline);
    }

    @Override
    public int getItemCount() {
        return airlinesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView images;
        private TextView tvAirlineName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAirlineName = itemView.findViewById(R.id.tvAirlineName);
            images = itemView.findViewById(R.id.image);

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
