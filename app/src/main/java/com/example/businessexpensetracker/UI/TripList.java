package com.example.businessexpensetracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.businessexpensetracker.R;
import com.example.businessexpensetracker.database.Repository;
import com.example.businessexpensetracker.entities.Trip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TripList extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_list);

        RecyclerView recyclerView = findViewById(R.id.triprecyclerview);
        final TripAdapter tripAdapter = new TripAdapter(this);
        recyclerView.setAdapter(tripAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        repository = new Repository(getApplication());
        List<Trip> allTrips = repository.getAllTrips();
        tripAdapter.setTrips(allTrips);


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
        RecyclerView recyclerView = findViewById(R.id.triprecyclerview);
        final TripAdapter tripAdapter = new TripAdapter(this);
        recyclerView.setAdapter(tripAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tripAdapter.setTrips(allTrips);

    }
}