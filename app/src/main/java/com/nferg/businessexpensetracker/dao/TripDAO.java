package com.nferg.businessexpensetracker.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.nferg.businessexpensetracker.entities.Trip;

import java.util.List;

@Dao
public interface TripDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Trip trip);

    @Update
    void update(Trip trip);

    @Delete
    void delete(Trip trip);

    @Query("SELECT * FROM trips ORDER BY tripID ASC")
    List<Trip> getAllTrips();

    //for validation
    @Query("SELECT * FROM trips WHERE tripID = :tripID")
    Trip getTripByID(int tripID);
}
