package com.example.businessexpensetracker.database;

import android.app.Application;

import com.example.businessexpensetracker.dao.ExpenseDAO;
import com.example.businessexpensetracker.dao.TripDAO;
import com.example.businessexpensetracker.entities.Expense;
import com.example.businessexpensetracker.entities.Trip;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class Repository {
    private final ExpenseDAO mExpenseDAO;
    private final TripDAO mTripDAO;
    private List<Trip> mAllTrips;
    private List<Expense> mAllExpenses;

    private static final int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        BizTripExpensesDatabaseBuilder db=BizTripExpensesDatabaseBuilder.getDatabase(application);
        mExpenseDAO = db.expenseDAO();
        mTripDAO = db.tripDAO();
    }

    public List<Trip> getAllTrips() {
        databaseExecutor.execute(()->{
            mAllTrips=mTripDAO.getAllTrips();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTrips;
    }

    //for validation
    public Trip getTripByID(final int tripID) {
        final AtomicReference<Trip> result = new AtomicReference<>();

        databaseExecutor.execute(() -> {
            result.set(mTripDAO.getTripByID(tripID));
        });

        try {
            Thread.sleep(1000);  // Waiting for the executor to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result.get();
    }

    public void insert(Trip trip) {
        databaseExecutor.execute(()-> {
            mTripDAO.insert(trip);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Trip trip) {
        databaseExecutor.execute(()->{
            mTripDAO.update(trip);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Trip trip) {
        databaseExecutor.execute(()->{
            mTripDAO.delete(trip);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Expense> getAllExpenses() {
        databaseExecutor.execute(()->{
            mAllExpenses=mExpenseDAO.getAllExpenses();
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllExpenses;
    }

    public void insert(Expense expense) {
        databaseExecutor.execute(()-> {
            mExpenseDAO.insert(expense);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Expense expense) {
        databaseExecutor.execute(()->{
            mExpenseDAO.update(expense);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void delete(Expense expense) {
        databaseExecutor.execute(()->{
            mExpenseDAO.delete(expense);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
