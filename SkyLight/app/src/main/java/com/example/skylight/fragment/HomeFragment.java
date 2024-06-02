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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skylight.AirlinesProfileActivity;
import com.example.skylight.R;
import com.example.skylight.adapter.AirlineAdapter;
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

public class HomeFragment extends Fragment {
    private ApiService apiService;

    private RecyclerView rvAirlineRekomendasi;
    private AirlineAdapter airlineAdapter;
    private List<Airline> airline = new ArrayList<>();
    private ProgressBar progressBar;
    private Button retry;
    private TextView error;
    private final Handler handler = new Handler(Looper.myLooper());
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvAirlineRekomendasi = view.findViewById(R.id.rv_airline_rekomendasi);
        rvAirlineRekomendasi.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        apiService = ApiConfig.getClient().create(ApiService.class);
        progressBar = view.findViewById(R.id.progressBar);
        error = view.findViewById(R.id.error);
        retry = view.findViewById(R.id.retry);

        airline = new ArrayList<>();
        airlineAdapter = new AirlineAdapter(airline, getContext());
        rvAirlineRekomendasi.setAdapter(airlineAdapter);

        airlineAdapter.setOnItemClickListener(airline -> {
            if (airline != null) {
                Intent intent = new Intent(getContext(), AirlinesProfileActivity.class);
                intent.putExtra("id", airline.getId());
                intent.putExtra("name", airline.getName());
                intent.putExtra("country", airline.getCountry());
                intent.putExtra("founded", airline.getFounded());
                intent.putExtra("destinations", airline.getDestinationString());
                intent.putExtra("website", airline.getWebsite());
                startActivity(intent);
            }
        });
        getAirlineFromApi();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView menu_explore = view.findViewById(R.id.menu_explore);
        ImageView menu_simpan = view.findViewById(R.id.menu_simpan);
        ImageView menu_profile = view.findViewById(R.id.menu_profile);

        menu_profile.setOnClickListener(v -> loadFragment(new ProfileFragment()));
        menu_simpan.setOnClickListener(v -> loadFragment(new SimpanFragment()));
        menu_explore.setOnClickListener(v -> loadFragment(new SearchFragment()));

        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void getAirlineFromApi() {
        progressBar.setVisibility(View.VISIBLE);
        Call<List<Airline>> call = apiService.getAirlines();
        call.enqueue(new Callback<List<Airline>>() {
            @Override
            public void onResponse(Call<List<Airline>> call, Response<List<Airline>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    airline = response.body();
                    executor.execute(() -> {
                        try {
                            Thread.sleep(1000);
                            handler.post(() -> {
                                progressBar.setVisibility(View.GONE);
                                rvAirlineRekomendasi.setVisibility(View.VISIBLE);
                                error.setVisibility(View.GONE);
                                retry.setVisibility(View.GONE);
                                airlineAdapter.setAirlineList(airline);

                                airlineAdapter = new AirlineAdapter(airline, getContext());
                                rvAirlineRekomendasi.setAdapter(airlineAdapter);
                            });
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } else {
                    progressBar.setVisibility(View.GONE);
                    showErrorView();
                }
            }

            @Override
            public void onFailure(Call<List<Airline>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                showErrorView();
            }
        });
    }

    private void showErrorView() {
        rvAirlineRekomendasi.setVisibility(View.GONE);
        error.setVisibility(View.VISIBLE);
        retry.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);

        retry.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                progressBar.setVisibility(View.VISIBLE);
                error.setVisibility(View.GONE);
                retry.setVisibility(View.GONE);
                handler.post(this::getAirlineFromApi);
            } else {
                Toast.makeText(getContext(), "Internet connection still unavailable", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}

