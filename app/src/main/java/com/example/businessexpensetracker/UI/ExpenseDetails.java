package com.example.businessexpensetracker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    int tripID;
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
        tripID = getIntent().getIntExtra("tripID", -1);

        // Log the received tripID
        //Log.d("ExpenseDetails", "Received tripID: " + tripID);

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

                if (id == -1) {
                    expense = new Expense(0, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()),
                            tripID, expenseDate);
                    repository.insert(expense);
                    Toast.makeText(ExpenseDetails.this, "Expense is saved.", Toast.LENGTH_SHORT).show();
//                    setResult(Activity.RESULT_OK);
//                    finish();
                } else {
                    expense = new Expense(id, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()),
                            tripID, expenseDate);
                    repository.update(expense);
                    Toast.makeText(ExpenseDetails.this, "Expense is updated.", Toast.LENGTH_SHORT).show();
//                    setResult(Activity.RESULT_OK);
//                    finish();
                }
            }
        });
    } //end of onCreate


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