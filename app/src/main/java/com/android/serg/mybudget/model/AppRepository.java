package com.android.serg.mybudget.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AppRepository {

    private DateDao dateDao;
    private BudgetDao budgetDao;

    private LiveData<List<Date>> dates;
    private LiveData<List<Budget>> budgets;

    public AppRepository(Application application) {

        MoviesDatabase database = MoviesDatabase.getInstance(application);
        dateDao = database.getGenreDao();
        budgetDao = database.getMovieDao();

    }

    public LiveData<List<Date>> getGenres() {

        return dateDao.getAllGenres();

    }

    public LiveData<List<Budget>> getGenreMovies(int dateId) {

        return budgetDao.getGenreMovies(dateId);

    }

    public void insertGenre(Date date) {

        new InsertGenreAsyncTask(dateDao).execute(date);

    }

    public void insertMovie(Budget budget) {

        new InsertMovieAsyncTask(budgetDao).execute(budget);

    }

    private static class InsertGenreAsyncTask extends AsyncTask<Date, Void, Void> {

        private DateDao dateDao;

        public InsertGenreAsyncTask(DateDao dateDao) {
            this.dateDao = dateDao;
        }

        @Override
        protected Void doInBackground(Date... dates) {

            dateDao.insert(dates[0]);

            return null;
        }
    }

    private static class InsertMovieAsyncTask extends AsyncTask<Budget, Void, Void> {

        private BudgetDao budgetDao;

        public InsertMovieAsyncTask(BudgetDao budgetDao) {
            this.budgetDao = budgetDao;
        }

        @Override
        protected Void doInBackground(Budget... budgets) {

            budgetDao.insert(budgets[0]);

            return null;
        }
    }

    public void updateGenre(Date date) {

        new UpdateGenreAsyncTask(dateDao).execute(date);

    }

    public void updateMovie(Budget budget) {

        new UpdateMovieAsyncTask(budgetDao).execute(budget);

    }

    private static class UpdateGenreAsyncTask extends AsyncTask<Date, Void, Void> {

        private DateDao dateDao;

        public UpdateGenreAsyncTask(DateDao dateDao) {
            this.dateDao = dateDao;
        }

        @Override
        protected Void doInBackground(Date... dates) {

            dateDao.update(dates[0]);

            return null;
        }
    }

    private static class UpdateMovieAsyncTask extends AsyncTask<Budget, Void, Void> {

        private BudgetDao budgetDao;

        public UpdateMovieAsyncTask(BudgetDao budgetDao) {
            this.budgetDao = budgetDao;
        }

        @Override
        protected Void doInBackground(Budget... budgets) {

            budgetDao.update(budgets[0]);

            return null;
        }
    }

    public void deleteGenre(Date date) {

        new DeleteGenreAsyncTask(dateDao).execute(date);

    }

    public void deleteMovie(Budget budget) {

        new DeleteMovieAsyncTask(budgetDao).execute(budget);

    }

    private static class DeleteGenreAsyncTask extends AsyncTask<Date, Void, Void> {

        private DateDao dateDao;

        public DeleteGenreAsyncTask(DateDao dateDao) {
            this.dateDao = dateDao;
        }

        @Override
        protected Void doInBackground(Date... dates) {

            dateDao.delete(dates[0]);

            return null;
        }
    }

    private static class DeleteMovieAsyncTask extends AsyncTask<Budget, Void, Void> {

        private BudgetDao budgetDao;

        public DeleteMovieAsyncTask(BudgetDao budgetDao) {
            this.budgetDao = budgetDao;
        }

        @Override
        protected Void doInBackground(Budget... budgets) {

            budgetDao.delete(budgets[0]);

            return null;
        }
    }




}
