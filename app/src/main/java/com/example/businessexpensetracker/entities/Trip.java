package com.example.businessexpensetracker.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="trips")
public class Trip {
    @PrimaryKey(autoGenerate = true)
    private int tripID;
    private String tripName;
    private double budget;
    private String lodging;
    private String startDate;
    private String endDate;

    public Trip(int tripID, String tripName, double budget, String lodging, String startDate, String endDate) {
        this.tripID = tripID;
        this.tripName = tripName;
        this.budget = budget;
        this.lodging = lodging;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Trip() {
    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getLodging() {
        return lodging;
    }

    public void setLodging(String lodging) {
        this.lodging = lodging;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "tripID=" + tripID +
                ", tripName='" + tripName + '\'' +
                ", budget=" + budget +
                ", lodging='" + lodging +  '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
