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

import com.example.skylight.AirlinesProfileActivity;
import com.example.skylight.R;
import com.example.skylight.models.Airline;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private List<Airline> airlineList;
    private List<Airline> airlineListFull; // Full list to retain original data
    private Context context;
    private static SearchAdapter.OnItemClickListener clickListener;

    public SearchAdapter(List<Airline> airlineList, Context context) {
        this.airlineList = airlineList;
        this.context = context;
        this.airlineListFull = new ArrayList<>(airlineList);
    }

    public void setAirlinesList(List<Airline> airlinesList) {
        this.airlineList = airlinesList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(Airline airline);
    }

    public void setOnItemClickListener(SearchAdapter.OnItemClickListener listener) {
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_airlines, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Airline airline = airlineList.get(position);
        holder.tvAirlineName.setText(airline.getName());
        holder.images.setImageResource(R.drawable.globe);
        holder.itemView.setTag(airline);
    }

    @Override
    public int getItemCount() {
        return airlineList.size();
    }

    public void filter(String text) {
        airlineList.clear();
        if (text.isEmpty()) {
            airlineList.addAll(airlineListFull);
        } else {
            text = text.toLowerCase();
            for (Airline airline : airlineListFull) {
                if (airline.getName().toLowerCase().contains(text)) {
                    airlineList.add(airline);
                }
            }
        }
        notifyDataSetChanged();
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
