package com.example.skylight.api;

import com.example.skylight.models.Airline;
import com.example.skylight.models.AirlineResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("airlines")
    Call<List<Airline>> getAirlines();

    @GET("airlines/{id}")
    Call<Airline> getId(@Path("id") String id);

    @GET("airlines")
    Call<List<Airline>> searchAirlines(@Query("search") String query);
}
