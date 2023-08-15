package com.example.businessexpensetracker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.businessexpensetracker.R;
import com.example.businessexpensetracker.database.Repository;
import com.example.businessexpensetracker.entities.Expense;
import com.example.businessexpensetracker.entities.Trip;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TripList.class);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addSampleData:
                //test db
                Trip trip = new Trip(0,"Las Vegas Defcon", 3000, "Flamingo Hotel", "8-10-23", "8-13-23");
                Repository repository = new Repository(getApplication());
                repository.insert(trip);

                Expense expense = new Expense(0, "Conference registration", 1000, 1, "8-11-23");
                repository.insert(expense);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}