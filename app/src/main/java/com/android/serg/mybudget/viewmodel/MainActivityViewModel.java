package com.android.serg.mybudget.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.serg.mybudget.model.AppRepository;
import com.android.serg.mybudget.model.Date;
import com.android.serg.mybudget.model.Budget;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    AppRepository appRepository;
    private LiveData<List<Date>> genres;
    private LiveData<List<Budget>> genreMovies;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);

        appRepository = new AppRepository(application);
    }

    public LiveData<List<Date>> getGenres() {

        genres = appRepository.getGenres();
        return genres;
    }

    public LiveData<List<Budget>> getGenreMovies(int genreId) {

        genreMovies = appRepository.getGenreMovies(genreId);
        return genreMovies;
    }

    public void addNewMovie(Budget budget) {

        appRepository.insertMovie(budget);

    }

    public void updateMovie(Budget budget) {

        appRepository.updateMovie(budget);

    }

    public void deleteMovie(Budget budget) {

        appRepository.deleteMovie(budget);

    }
}
