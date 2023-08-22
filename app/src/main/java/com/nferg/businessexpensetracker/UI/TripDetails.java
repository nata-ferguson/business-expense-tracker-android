package com.nferg.businessexpensetracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nferg.businessexpensetracker.R;
import com.nferg.businessexpensetracker.database.Repository;
import com.nferg.businessexpensetracker.entities.Expense;
import com.nferg.businessexpensetracker.entities.Trip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TripDetails extends AppCompatActivity {
    EditText editName;
    EditText editBudget;
    EditText editLodging;
    EditText editStartDate;
    EditText editEndDate;

    String name;
    double budget;
    int id;
    String lodging;
    String startDate;
    String endDate;

    Trip trip;
    Trip currentTrip;
    int numExpenses;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);

        editName = findViewById(R.id.tripname);
        editBudget = findViewById(R.id.budget);
        editLodging = findViewById(R.id.lodging);
        editStartDate = findViewById(R.id.start_date);
        editEndDate = findViewById(R.id.end_date);

        // Retrieve data from the intent extras
        id = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        budget = getIntent().getDoubleExtra("budget", 0);
        lodging = getIntent().getStringExtra("lodging");
        startDate = getIntent().getStringExtra("start_date");
        endDate = getIntent().getStringExtra("end_date");

        // Set the retrieved values in the EditText fields
        if (id != -1) {
            editName.setText(name);
            editLodging.setText(lodging);
            editBudget.setText(Double.toString(budget));
            editStartDate.setText(startDate);
            editEndDate.setText(endDate);
        }

        repository = new Repository(getApplication());

        RecyclerView recyclerView = findViewById(R.id.expenserecyclerview);
        repository = new Repository(getApplication());
        final ExpenseAdapter expenseAdapter = new ExpenseAdapter(this);
        recyclerView.setAdapter(expenseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Expense> filteredExpenses = new ArrayList<>();
        for (Expense exp : repository.getAllExpenses()) {
            if (exp.getTripID() == id) {
                filteredExpenses.add(exp);
            }
        }
        expenseAdapter.setExpenses(filteredExpenses);

        Button button = findViewById(R.id.savetrip);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //adding validation
                String startDate = editStartDate.getText().toString();
                String endDate = editEndDate.getText().toString();

                if (isDateValid(startDate) && isDateValid(endDate)) {
                    if (isEndDateAfterStartDate(startDate, endDate)) {
                        if (id == -1) {
                            trip = new Trip(0, editName.getText().toString(), Double.parseDouble(editBudget.getText().toString()),
                                    editLodging.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                            repository.insert(trip);
                            Toast.makeText(TripDetails.this, "Trip is saved.", Toast.LENGTH_SHORT).show();
                            setResult(Activity.RESULT_OK);
                            finish();
                        }
                        else {
                            trip = new Trip(id, editName.getText().toString(), Double.parseDouble(editBudget.getText().toString()),
                                    editLodging.getText().toString(), editStartDate.getText().toString(), editEndDate.getText().toString());
                            repository.update(trip);
                            Toast.makeText(TripDetails.this, "Trip is updated.", Toast.LENGTH_SHORT).show();
                            setResult(Activity.RESULT_OK);
                            finish();
                        }
                    } else {
                        // Show Toast message for invalid end date
                        Toast.makeText(TripDetails.this, "End date must be after the start date", Toast.LENGTH_SHORT).show();
                    } //end of isEndDateAfterStartDate validation
                } else {
                    // Show Toast messages for invalid date formats
                    if (!isDateValid(startDate)) {
                        Toast.makeText(TripDetails.this, "Invalid start date format. Enter mm-dd-yy", Toast.LENGTH_SHORT).show();
                    }
                    if (!isDateValid(endDate)) {
                        Toast.makeText(TripDetails.this, "Invalid end date format. Enter mm-dd-yy", Toast.LENGTH_SHORT).show();
                    }
                } //end of date format validation
            } //end of onClick
        });

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TripDetails.this, ExpenseDetails.class);
                intent.putExtra("tripID", id);
                startActivity(intent);
            }
        });

    }

    public boolean isDateValid(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy", Locale.US);
        sdf.setLenient(false);
        try {
            // Parse the date to check if it's valid
            Date parsedDate = sdf.parse(date);
            return true;
        } catch (ParseException e) {
            //e.printStackTrace();
            return false;
        }
    }

    public boolean isEndDateAfterStartDate(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy", Locale.US);
        sdf.setLenient(false);
        try {
            // Parse the start and end dates
            Date parsedStartDate = sdf.parse(startDate);
            Date parsedEndDate = sdf.parse(endDate);
            // Check if the end date is after the start date
            return parsedEndDate.after(parsedStartDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.expenserecyclerview);
        final ExpenseAdapter expenseAdapter = new ExpenseAdapter(this);
        recyclerView.setAdapter(expenseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Expense> filteredExpenses = new ArrayList<>();
        for (Expense exp : repository.getAllExpenses()) {
            if (exp.getTripID() == id) filteredExpenses.add(exp);
        }
        expenseAdapter.setExpenses(filteredExpenses);

        //Toast.makeText(ProductDetails.this,"refresh list",Toast.LENGTH_LONG).show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trip_details, menu);
        getMenuInflater().inflate(R.menu.delete_trip, menu);
        return true;
    }

    //delete business trip, notifications and sharing
    // cannot be deleted if expenses are associated with it.
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                // Create the text to be shared containing trip details

                StringBuilder tripDetailsBuilder = new StringBuilder();

                tripDetailsBuilder.append("Trip Name: ").append(name).append("\n");
                tripDetailsBuilder.append("Lodging: ").append(lodging).append("\n");
                tripDetailsBuilder.append("Budget: $").append(budget).append("\n");
                tripDetailsBuilder.append("Start Date: ").append(startDate).append("\n");
                tripDetailsBuilder.append("End Date: ").append(endDate).append("\n\n");

                // Prepare headers for expenses table
                String headerFormat = "%-20s %-12s %s\n";
                tripDetailsBuilder.append(String.format(headerFormat, "Expense Title ", "Amount", "Date"));

                // Check each expense's length to make sure everything aligns nicely in the "table"
                for (Expense exp : repository.getAllExpenses()) {
                    if (exp.getTripID() == id) {
                        String expenseFormat = "%-20s $%-9s %s\n";
                        tripDetailsBuilder.append(String.format(expenseFormat,
                                truncateString(exp.getExpenseName(), 17), // Limit to 14 characters
                                exp.getPrice(),
                                exp.getExpenseDate()));
                    }
                }

                // Create the sharing intent
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, tripDetailsBuilder.toString());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Business Trip Details");
                sendIntent.setType("text/plain");

                // Create a chooser to select the sharing app
                Intent shareIntent = Intent.createChooser(sendIntent, "Share Business Trip Details");

                // Start the sharing activity
                startActivity(shareIntent);

                return true;

            case R.id.notifystart:
                String dateFromScreen = editStartDate.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy", Locale.US);

                for (Trip tr : repository.getAllTrips()) {
                    if (tr.getTripID() == id) currentTrip = tr;
                }

                try {
                    // Parse the start date
                    Date startDate = sdf.parse(dateFromScreen);
                    // Get the time difference between the current date and the start date
                    long timeDifference = startDate.getTime() - System.currentTimeMillis();

                    if (timeDifference <= 0) {
                        // If the start date is already passed or today, show a Toast message
                        Toast.makeText(TripDetails.this, currentTrip.getTripName() + " Trip is today.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Schedule a notification to be shown at the start date
                        Intent notificationIntent = new Intent(TripDetails.this, MyReceiver.class);
                        notificationIntent.putExtra("key", dateFromScreen + " should trigger notification for start date");
                        PendingIntent sender = PendingIntent.getBroadcast(TripDetails.this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

                        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + timeDifference, sender);

                        Toast.makeText(TripDetails.this, currentTrip.getTripName() + " Notification scheduled for business trip start date.", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return true;

            case R.id.notifyend:
                String endDateFromScreen = editEndDate.getText().toString();
                SimpleDateFormat sdfEnd = new SimpleDateFormat("MM-dd-yy", Locale.US);

                for (Trip tr : repository.getAllTrips()) {
                    if (tr.getTripID() == id) currentTrip = tr;
                }

                try {
                    // Parse the end date
                    Date endDate = sdfEnd.parse(endDateFromScreen);
                    // Get the time difference between the current date and the end date
                    long timeDifferenceEnd = endDate.getTime() - System.currentTimeMillis();

                    if (timeDifferenceEnd <= 0) {
                        // If the end date is already passed or today, show a Toast message
                        Toast.makeText(TripDetails.this, currentTrip.getTripName() + " business trip end date notification.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Schedule a notification to be shown at the start date
                        Intent notificationIntentEnd = new Intent(TripDetails.this, MyReceiver.class);
                        notificationIntentEnd.putExtra("key", endDateFromScreen + " should trigger notification for end date");
                        PendingIntent senderEnd = PendingIntent.getBroadcast(TripDetails.this, 0, notificationIntentEnd, PendingIntent.FLAG_IMMUTABLE);

                        AlarmManager alarmManagerEnd = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        alarmManagerEnd.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + timeDifferenceEnd, senderEnd);

                        Toast.makeText(TripDetails.this, currentTrip.getTripName() + " Notification scheduled for business trip end date.", Toast.LENGTH_SHORT).show();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return true;

            case R.id.deletetrip:
                for (Trip tr : repository.getAllTrips()) {
                    if (tr.getTripID() == id) currentTrip = tr;
                }

                numExpenses = 0;
                for (Expense expense : repository.getAllExpenses()) {
                    if (expense.getTripID() == id) ++numExpenses;
                }

                if (numExpenses == 0) {
                    repository.delete(currentTrip);
                    Toast.makeText(TripDetails.this, currentTrip.getTripName() + " was deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(TripDetails.this, "Can't delete a business trip with expenses", Toast.LENGTH_LONG).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String truncateString(String str, int maxLength) {
        if (str.length() > maxLength) {
            return str.substring(0, maxLength - 3) + "..."; // Subtract 3 to account for the dots
        } else {
            StringBuilder sb = new StringBuilder(str);
            while (sb.length() < maxLength) {
                sb.append(" ");
            }
            return sb.toString();
        }
    }

}