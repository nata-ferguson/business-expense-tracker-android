package com.example.businessexpensetracker.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "expenses")
public class Expense {
    @PrimaryKey(autoGenerate = true)
    private int expenseID;
    private String expenseName;
    private double price;
    private int tripID;
    private String expenseDate;

    public Expense(int expenseID, String expenseName, double price, int tripID, String expenseDate) {
        this.expenseID = expenseID;
        this.expenseName = expenseName;
        this.price = price;
        this.tripID = tripID;
        this.expenseDate = expenseDate;
    }

    public Expense() {
    }

    public int getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(int expenseID) {
        this.expenseID = expenseID;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }
}
