package com.example.skylight;

import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.skylight.api.ApiConfig;
import com.example.skylight.api.ApiService;
import com.example.skylight.helper.DatabaseHelper;
import com.example.skylight.models.Airline;
import com.example.skylight.models.AirlineResponse;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AirlinesProfileActivity extends AppCompatActivity {

    private static  final String TAG = "dbHelper";
    private ImageView ivAirlinePic, saveBtn;
    private Airline airline;
    private Button btn_website;
    private TextView tvAirlineName, tvCountry, tvFounded, tvDestinations;
    private Airline currentAirline;
    private DatabaseHelper dbHelper;
    private boolean isAirlineSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airlines_profile);

        dbHelper = new DatabaseHelper(this);

        tvAirlineName = findViewById(R.id.TV_airlines);
        tvCountry = findViewById(R.id.TV_country);
        tvFounded = findViewById(R.id.TV_founded);
        tvDestinations = findViewById(R.id.TV_destinations);
        btn_website = findViewById(R.id.btn_website);
        ivAirlinePic = findViewById(R.id.IV_pic);
        saveBtn = findViewById(R.id.btn_save);

        String name = getIntent().getStringExtra("name");
        String country = getIntent().getStringExtra("country");
        String founded = getIntent().getStringExtra("founded");
        String destinations = getIntent().getStringExtra("destinations");
        String website = getIntent().getStringExtra("website");

        tvAirlineName.setText(name);
        tvCountry.setText("Country: " + country);
        tvFounded.setText("Founded: " + founded);
        tvDestinations.setText("Destinations: " + destinations);  // Corrected text label
        btn_website.setText("Go to website");

        airline = new Airline();
        airline.setName(name);
        airline.setCountry(country);
        airline.setFounded(founded);
        airline.setDestinationString(destinations);
        airline.setWebsite(website);

        isAirlineSaved = dbHelper.isAirlineSaved(airline);
        updateSavedButtonImage();

        saveBtn.setOnClickListener(v -> toggleSaveAirline());

        btn_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(website));

                startActivity(intent);
            }
        });
    }

    private void updateSavedButtonImage(){
        if(isAirlineSaved){
            saveBtn.setImageResource(R.drawable.bookmark_white);
        } else{
            saveBtn.setImageResource(R.drawable.bookmark_24);
        }
    }

    private void toggleSaveAirline(){
        if(isAirlineSaved){
            dbHelper.removeAirline(airline);
            Toast.makeText(this, "Remove airlines in collection", Toast.LENGTH_SHORT).show();
        }else {
            dbHelper.addAirline(airline);
            Toast.makeText(this, "Add airline to collection", Toast.LENGTH_SHORT).show();
        }
        isAirlineSaved = !isAirlineSaved;
        updateSavedButtonImage();
    }
}