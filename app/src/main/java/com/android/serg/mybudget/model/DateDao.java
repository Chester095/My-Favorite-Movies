package com.android.serg.mybudget.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DateDao {

    @Insert
    void insert(Date date);

    @Update
    void update(Date date);

    @Delete
    void delete(Date date);

    @Query("select * from date_table")
    LiveData<List<Date>> getAllGenres();

}
