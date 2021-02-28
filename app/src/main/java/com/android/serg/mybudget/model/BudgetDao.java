package com.android.serg.mybudget.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BudgetDao {

    @Insert
    void insert(Budget budget);

    @Update
    void update(Budget budget);

    @Delete
    void delete(Budget budget);

    @Query("select * from budget_table")
    LiveData<List<Budget>> getAllMovies();

    @Query("select * from budget_table where date_id==:dateId")
    LiveData<List<Budget>> getGenreMovies(int dateId);

}
