package com.example.businessexpensetracker.UI;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.businessexpensetracker.R;
import com.example.businessexpensetracker.database.Repository;
import com.example.businessexpensetracker.entities.Expense;
import com.example.businessexpensetracker.entities.Trip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExpenseDetails extends AppCompatActivity {

    EditText editName;
    EditText editPrice;
    EditText editDate;

    String name;
    double price;
    int id;
    int tripId;
    String date;

    Expense expense;
    Expense currentExpense;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_details);

        // Initialize EditText fields
        editName = findViewById(R.id.expensename);
        editPrice = findViewById(R.id.price);
        editDate = findViewById(R.id.editdate);

        // Retrieve data from the intent extras
        id = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        price = getIntent().getDoubleExtra("price", -1.0);
        date = getIntent().getStringExtra("date");
        tripId = getIntent().getIntExtra("tripID", -1);

        editName.setText(name);
        editPrice.setText(Double.toString(price));
        editDate.setText(date);

        repository = new Repository(getApplication());

        Button button = findViewById(R.id.saveexpense);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Fetch the expense date
                String expenseDate = editDate.getText().toString();

                // Fetch the associated trip's start and end dates
                Trip associatedTrip = repository.getTripByID(tripId);
                String tripStartDate = associatedTrip.getStartDate();
                String tripEndDate = associatedTrip.getEndDate();

                // Validate that the expense date is within the business trip dates
                if (!isDateValid(expenseDate)) {
                    Toast.makeText(ExpenseDetails.this, "Invalid expense date format. Enter mm-dd-yy", Toast.LENGTH_SHORT).show();
                } else if (!isDateWithinRange(expenseDate, tripStartDate, tripEndDate)) {
                    Toast.makeText(ExpenseDetails.this, "Expense date must be during the associated business trip dates", Toast.LENGTH_SHORT).show();
                } else {
                    // All validation checks passed
                    if (id == -1) {
                        expense = new Expense(0, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()),
                                tripId, expenseDate);
                        repository.insert(expense);
                    } else {
                        expense = new Expense(id, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()),
                                tripId, expenseDate);
                        repository.update(expense);
                    }
                }
            }
        });
    } //end of onCreate

    private boolean isDateWithinRange(String dateToCheck, String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy", Locale.US);
        sdf.setLenient(false);
        try {
            // Parse the start, end, and check dates
            Date parsedStartDate = sdf.parse(startDate);
            Date parsedEndDate = sdf.parse(endDate);
            Date parsedDateToCheck = sdf.parse(dateToCheck);
            // Check if the date is within the business trip date range
            return (parsedDateToCheck.equals(parsedStartDate) || parsedDateToCheck.after(parsedStartDate)) &&
                    (parsedDateToCheck.equals(parsedEndDate) || parsedDateToCheck.before(parsedEndDate));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isDateValid(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy", Locale.US);
        sdf.setLenient(false);
        try {
            // Parse the date to check if it's valid
            Date parsedDate = sdf.parse(date);
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_expense_details, menu);
        getMenuInflater().inflate(R.menu.delete_expense, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.share:
                String expenseDetails = "Expense Name: " + name + "\n" +
                        "Price: $" + price + "\n" +
                        "Date: " + editDate.getText().toString() + "\n" ;

                // Create the sharing intent
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, expenseDetails);
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Expense Details");
                sendIntent.setType("text/plain");

                // Create a chooser to select the sharing app
                Intent shareIntent = Intent.createChooser(sendIntent, "Share Expense Details");

                // Start the sharing activity
                startActivity(shareIntent);

                return true;

            case R.id.deleteexpense:
                for (Expense exp : repository.getAllExpenses()) {
                    if (exp.getExpenseID() == id) currentExpense = exp;
                }

                repository.delete(currentExpense);
                Toast.makeText(ExpenseDetails.this, currentExpense.getExpenseName() + " was deleted", Toast.LENGTH_LONG).show();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}