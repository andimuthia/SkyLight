package com.example.skylight.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylight.AirlinesProfileActivity;
import com.example.skylight.R;
import com.example.skylight.adapter.SimpanAdapter;
import com.example.skylight.helper.DatabaseHelper;
import com.example.skylight.models.Airline;

import java.util.ArrayList;
import java.util.List;

public class SimpanFragment extends Fragment {

    private RecyclerView recyclerView;
    private SimpanAdapter simpanAdapter;
    private List<Airline> airline;
    private DatabaseHelper dbHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simpan, container, false);

        recyclerView = view.findViewById(R.id.rv_bookmarks);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DatabaseHelper(getContext());
        airline = new ArrayList<>();
        simpanAdapter = new SimpanAdapter(airline, getContext());
        recyclerView.setAdapter(simpanAdapter);
        simpanAdapter.setAirlinesList(airline);
        loadSavedAirline();

        simpanAdapter.setOnItemClickListener(airline -> {
            Intent intent = new Intent(getContext(), AirlinesProfileActivity.class);
//            intent.putExtra("id", airline.getId());
            intent.putExtra("name", airline.getName());
            intent.putExtra("country", airline.getCountry());
            intent.putExtra("founded", airline.getFounded());
            intent.putExtra("destinations", airline.getDestinationString());
            intent.putExtra("website", airline.getWebsite());
            startActivity(intent);
        });

        ImageView iv_profile = view.findViewById(R.id.menu_profile);
        ImageView iv_search = view.findViewById(R.id.menu_explore);
        ImageView iv_home = view.findViewById(R.id.menu_home);

        iv_profile.setOnClickListener(v -> loadFragment(new ProfileFragment()));
        iv_search.setOnClickListener(v -> loadFragment(new SearchFragment()));
        iv_home.setOnClickListener(v -> loadFragment(new HomeFragment()));
        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSavedAirline();
    }

    private void loadSavedAirline() {
        List<Airline> savedAirline = dbHelper.getAllAirlines();
        airline.clear();
        airline.addAll(savedAirline);
        simpanAdapter.setAirlinesList(airline);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
