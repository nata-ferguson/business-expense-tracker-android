package com.example.businessexpensetracker.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.businessexpensetracker.dao.ExpenseDAO;
import com.example.businessexpensetracker.dao.TripDAO;
import com.example.businessexpensetracker.entities.Expense;
import com.example.businessexpensetracker.entities.Trip;

@Database(entities = {Trip.class, Expense.class}, version = 2, exportSchema = false)
public abstract class BizTripExpensesDatabaseBuilder extends RoomDatabase {
    public abstract TripDAO tripDAO();
    public abstract ExpenseDAO expenseDAO();
    private static volatile BizTripExpensesDatabaseBuilder INSTANCE;

    static BizTripExpensesDatabaseBuilder getDatabase(final Context context) {
        if(INSTANCE==null){
            synchronized (BizTripExpensesDatabaseBuilder.class) {
                if(INSTANCE==null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), BizTripExpensesDatabaseBuilder.class, "BusinessExpenseTrackerDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }

        }
        return INSTANCE;
    }
}
