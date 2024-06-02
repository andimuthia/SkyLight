package com.example.skylight.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylight.AirlinesProfileActivity;
import com.example.skylight.R;
import com.example.skylight.adapter.AirlineAdapter;
import com.example.skylight.adapter.SearchAdapter;
import com.example.skylight.adapter.SimpanAdapter;
import com.example.skylight.api.ApiConfig;
import com.example.skylight.api.ApiService;
import com.example.skylight.models.Airline;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private SearchView searchView;
    private ApiService apiService;
    private RecyclerView rv_search;
    private SearchAdapter searchAdapter;
    private List<Airline> airline = new ArrayList<>();
    private ProgressBar progressBar;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        rv_search = view.findViewById(R.id.rv_search);
        rv_search.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        searchView = view.findViewById(R.id.searchView);
        searchAdapter = new SearchAdapter(airline, getContext());
        rv_search.setAdapter(searchAdapter);
        searchAdapter.setAirlinesList(airline);


        searchAdapter.setOnItemClickListener(airline -> {
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
        ImageView iv_simpan = view.findViewById(R.id.menu_simpan);
        ImageView iv_home = view.findViewById(R.id.menu_home);

        iv_profile.setOnClickListener(v -> loadFragment(new ProfileFragment()));
        iv_simpan.setOnClickListener(v -> loadFragment(new SimpanFragment()));
        iv_home.setOnClickListener(v -> loadFragment(new HomeFragment()));

        loadAirlines();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchAdapter != null) {
                    searchAdapter.filter(newText);
                }
                return true;
            }
        });
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void loadAirlines() {
        ApiService apiService = ApiConfig.getApiService();
        Call<List<Airline>> call = apiService.getAirlines();

        call.enqueue(new Callback<List<Airline>>() {
            @Override
            public void onResponse(Call<List<Airline>> call, Response<List<Airline>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Airline> airlineList = response.body();
                    searchAdapter = new SearchAdapter(airlineList, getContext());
                    rv_search.setAdapter(searchAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Airline>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                showError("Internet connection is unavailable");
            }

            private void showError(String message) {
                if (getContext() != null) {
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}



