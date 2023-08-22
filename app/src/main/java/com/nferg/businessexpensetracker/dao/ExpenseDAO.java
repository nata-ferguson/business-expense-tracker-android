package com.nferg.businessexpensetracker.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.nferg.businessexpensetracker.entities.Expense;

import java.util.List;

@Dao
public interface ExpenseDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Expense expense);

    @Update
    void update(Expense expense);

    @Delete
    void delete(Expense expense);

    @Query("SELECT * FROM expenses ORDER BY expenseID ASC")
    List<Expense> getAllExpenses();

    @Query("SELECT * FROM expenses WHERE tripID= :tripID ORDER BY expenseID ASC")
    List<Expense> getAllAssociatedExpenses(int tripID);

}
