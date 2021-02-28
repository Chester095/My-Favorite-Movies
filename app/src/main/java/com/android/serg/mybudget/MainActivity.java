package com.android.serg.mybudget;

import android.content.Intent;
import android.os.Bundle;

import com.android.serg.mybudget.databinding.ActivityMainBinding;
import com.android.serg.mybudget.model.Budget;
import com.android.serg.mybudget.model.Date;
import com.android.serg.mybudget.viewmodel.MainActivityViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel mainActivityViewModel;
    private ActivityMainBinding activityMainBinding;
    private MainActivityClickHandlers clickHandlers;
    private Date selectedDate;
    private ArrayList<Date> dateArrayList;
    private ArrayList<Budget> budgetArrayList;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private int selectedMovieId;

    public static final int ADD_MOVIE_REQUEST_CODE = 111;
    public static final int EDIT_MOVIE_REQUEST_CODE = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        activityMainBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_main);

        mainActivityViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(MainActivityViewModel.class);

        clickHandlers = new MainActivityClickHandlers();
        activityMainBinding.setClickHandlers(clickHandlers);

        mainActivityViewModel.getGenres().observe(this, new Observer<List<Date>>() {
            @Override
            public void onChanged(List<Date> dates) {

                dateArrayList = (ArrayList<Date>) dates;

                for (Date date : dates) {

                    Log.d("MyTAG", date.getGenreName());

                }

                showInSpinner();

            }
        });



//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    private void showInSpinner() {

        ArrayAdapter<Date> genreArrayAdapter = new ArrayAdapter<Date>(this,
                R.layout.spinner_item, dateArrayList);
        genreArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        activityMainBinding.setSpinnerAdapter(genreArrayAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class MainActivityClickHandlers {

        public void onFabClicked(View view) {

//            Toast.makeText(MainActivity.this, "Button is clicked!",
//                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            startActivityForResult(intent, ADD_MOVIE_REQUEST_CODE);

        }

        public void onSelectedItem(AdapterView<?> parent, View view, int position, long id) {

            selectedDate = (Date) parent.getItemAtPosition(position);

            String message = "id is " + selectedDate.getId() +
                    "\n name is " + selectedDate.getGenreName();

//            Toast.makeText(parent.getContext(), message, Toast.LENGTH_SHORT).show();

            loadGenreMoviesInArrayList(selectedDate.getId());
        }

    }

    private void loadGenreMoviesInArrayList(int genreId) {

        mainActivityViewModel.getGenreMovies(genreId).observe(this, new Observer<List<Budget>>() {
            @Override
            public void onChanged(List<Budget> budgets) {

                budgetArrayList = (ArrayList<Budget>) budgets;
                loadRecyclerView();

            }
        });

    }

    private void loadRecyclerView() {

        recyclerView = activityMainBinding.secondaryLayout.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        movieAdapter = new MovieAdapter();
        movieAdapter.setBudgetArrayList(budgetArrayList);
        recyclerView.setAdapter(movieAdapter);

        movieAdapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Budget budget) {
                selectedMovieId = budget.getBudgetId();
                Intent intent = new Intent(MainActivity.this,
                        AddEditActivity.class);
                intent.putExtra(AddEditActivity.MOVIE_ID, selectedMovieId);
                intent.putExtra(AddEditActivity.MOVIE_NAME, budget.getMovieName());
                intent.putExtra(AddEditActivity.MOVIE_DESCRIPTION, budget.getMovieDescription());
                startActivityForResult(intent, EDIT_MOVIE_REQUEST_CODE);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
                                 int direction) {

                Budget budgetToDelete = budgetArrayList.get(viewHolder.getAdapterPosition());
                mainActivityViewModel.deleteMovie(budgetToDelete);

            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int selectedGenreId = selectedDate.getId();

        if (requestCode == ADD_MOVIE_REQUEST_CODE && resultCode == RESULT_OK) {

            Budget budget = new Budget();
            budget.setDateId(selectedGenreId);
            budget.setMovieName(data.getStringExtra(AddEditActivity.MOVIE_NAME));
            budget.setMovieDescription(data.getStringExtra(AddEditActivity.MOVIE_DESCRIPTION));

            mainActivityViewModel.addNewMovie(budget);

        } else if (requestCode == EDIT_MOVIE_REQUEST_CODE && resultCode == RESULT_OK) {

            Budget budget = new Budget();
            budget.setBudgetId(selectedMovieId);
            budget.setDateId(selectedGenreId);
            budget.setMovieName(data.getStringExtra(AddEditActivity.MOVIE_NAME));
            budget.setMovieDescription(data.getStringExtra(AddEditActivity.MOVIE_DESCRIPTION));

            mainActivityViewModel.updateMovie(budget);

        }
    }
}
