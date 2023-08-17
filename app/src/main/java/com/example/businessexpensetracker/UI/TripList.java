package com.example.businessexpensetracker.UI;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.SearchView;

import com.example.businessexpensetracker.R;
import com.example.businessexpensetracker.database.Repository;
import com.example.businessexpensetracker.entities.Trip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TripList extends AppCompatActivity {
    private Repository repository;
    private TripAdapter tripAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);

        RecyclerView recyclerView = findViewById(R.id.triprecyclerview);
        tripAdapter = new TripAdapter(this);
        recyclerView.setAdapter(tripAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = new Repository(getApplication());
        List<Trip> allTrips = repository.getAllTrips();
        tripAdapter.setTrips(allTrips);

        SearchView searchView = findViewById(R.id.search_trip);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Not used, as we handle the updates on text change.
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("TripList", "Query text changed to: " + newText);
                filter(newText);
                return true;
            }
        });


        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TripList.this, TripDetails.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {

        super.onResume();
        List<Trip> allTrips = repository.getAllTrips();
        tripAdapter.setTrips(allTrips);

    }

    private void filter(String text) {
        List<Trip> filteredList = new ArrayList<>();

        for (Trip trip : repository.getAllTrips()) {
            if (trip.getTripName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(trip);
            }
        }
        Log.d("TripList", "Filtered trips count: " + filteredList.size());
        tripAdapter.setTrips(filteredList);
        tripAdapter.notifyDataSetChanged();


    }
}