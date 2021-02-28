package com.android.serg.mybudget.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "budget_table", foreignKeys = @ForeignKey(entity = Date.class,
parentColumns = "id", childColumns = "date_id", onDelete = ForeignKey.CASCADE))
public class Budget extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "movie_id")
    private int budgetId;
    @ColumnInfo(name = "movie_name")
    private String movieName;
    @ColumnInfo(name = "movie_description")
    private String movieDescription;
    @ColumnInfo(name = "date_id")
    private int dateId;

    @Ignore
    public Budget() {
    }

    public Budget(int budgetId, String movieName, String movieDescription, int dateId) {
        this.budgetId = budgetId;
        this.movieName = movieName;
        this.movieDescription = movieDescription;
        this.dateId = dateId;
    }

    @Bindable
    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
        notifyPropertyChanged(BR.budgetId);
    }

    @Bindable
    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
        notifyPropertyChanged(BR.movieName);
    }

    @Bindable
    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
        notifyPropertyChanged(BR.movieDescription);
    }

    @Bindable
    public int getDateId() {
        return dateId;
    }

    public void setDateId(int dateId) {
        this.dateId = dateId;
        notifyPropertyChanged(BR.dateId);
    }
}
