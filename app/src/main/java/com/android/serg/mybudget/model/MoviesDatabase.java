package com.android.serg.mybudget.model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Date.class, Budget.class}, version = 1)
public abstract class MoviesDatabase extends RoomDatabase {

    private static MoviesDatabase instance;

    public abstract DateDao getGenreDao();

    public abstract BudgetDao getMovieDao();

    public static synchronized MoviesDatabase getInstance(Context context) {

        if (instance == null) {

            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MoviesDatabase.class, "moviesDB")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();

        }

        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new InitialDataAsyncTask(instance).execute();
        }
    };

    private static class InitialDataAsyncTask extends AsyncTask<Void, Void, Void> {

        private DateDao dateDao;
        private BudgetDao budgetDao;

        public InitialDataAsyncTask(MoviesDatabase database) {

            dateDao = database.getGenreDao();
            budgetDao = database.getMovieDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {

            Date comedyDate = new Date();
            comedyDate.setGenreName("Comedy");

            Date romanceDate = new Date();
            romanceDate.setGenreName("Romance");

            Date dramaDate = new Date();
            dramaDate.setGenreName("Drama");

            dateDao.insert(comedyDate);
            dateDao.insert(romanceDate);
            dateDao.insert(dramaDate);


            Budget budget1 = new Budget();
            budget1.setMovieName("Bad Boys for Life");
            budget1.setMovieDescription("The Bad Boys Mike Lowrey and Marcus Burnett are back together for one last ride in the highly anticipated Bad Boys for Life.");
            budget1.setDateId(1);

            Budget budget2 = new Budget();
            budget2.setMovieName("Parasite");
            budget2.setMovieDescription("All unemployed, Ki-taek and his family take peculiar interest in the wealthy and glamorous Parks, as they ingratiate themselves into their lives and get entangled in an unexpected incident.");
            budget2.setDateId(1);

            Budget budget3 = new Budget();
            budget3.setMovieName(" Once Upon a Time... in Hollywood");
            budget3.setMovieDescription("A faded television actor and his stunt double strive to achieve fame and success in the film industry during the final years of Hollywood's Golden Age in 1969 Los Angeles.");
            budget3.setDateId(1);

            Budget budget4 = new Budget();
            budget4.setMovieName("You");
            budget4.setMovieDescription("A dangerously charming, intensely obsessive young man goes to extreme measures to insert himself into the lives of those he is transfixed by.");
            budget4.setDateId(2);

            Budget budget5 = new Budget();
            budget5.setMovieName("Little Women");
            budget5.setMovieDescription("Jo March reflects back and forth on her life, telling the beloved story of the March sisters - four young women each determined to live life on their own terms.");
            budget5.setDateId(2);

            Budget budget6 = new Budget();
            budget6.setMovieName("Vikings");
            budget6.setMovieDescription("Vikings transports us to the brutal and mysterious world of Ragnar Lothbrok, a Viking warrior and farmer who yearns to explore - and raid - the distant shores across the ocean.");
            budget6.setDateId(2);

            Budget budget7 = new Budget();
            budget7.setMovieName("1917");
            budget7.setMovieDescription("Two young British soldiers during the First World War are given an impossible mission: deliver a message deep in enemy territory that will stop 1,600 men, and one of the soldiers' brothers, from walking straight into a deadly trap.");
            budget7.setDateId(3);

            Budget budget8 = new Budget();
            budget8.setMovieName("The Witcher");
            budget8.setMovieDescription("Geralt of Rivia, a solitary monster hunter, struggles to find his place in a world where people often prove more wicked than beasts.");
            budget8.setDateId(3);

            Budget budget9 = new Budget();
            budget9.setMovieName("The Outsider");
            budget9.setMovieDescription("Investigators are confounded over an unspeakable crime that's been committed.");
            budget9.setDateId(3);

            budgetDao.insert(budget1);
            budgetDao.insert(budget2);
            budgetDao.insert(budget3);
            budgetDao.insert(budget4);
            budgetDao.insert(budget5);
            budgetDao.insert(budget6);
            budgetDao.insert(budget7);
            budgetDao.insert(budget8);
            budgetDao.insert(budget9);

            return null;
        }
    }

}
