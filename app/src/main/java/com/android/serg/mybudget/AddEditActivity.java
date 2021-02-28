package com.android.serg.mybudget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.serg.mybudget.databinding.ActivityAddEditBinding;
import com.android.serg.mybudget.model.Budget;

public class AddEditActivity extends AppCompatActivity {

    private Budget budget;

    public static final String MOVIE_ID = "movieId";
    public static final String MOVIE_NAME = "movieName";
    public static final String MOVIE_DESCRIPTION = "movieDescription";
    private ActivityAddEditBinding activityAddEditBinding;
    private AddEditActivityClickHandlers addEditActivityClickHandlers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        budget = new Budget();
        activityAddEditBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_add_edit);
        activityAddEditBinding.setBudget(budget);
        addEditActivityClickHandlers = new AddEditActivityClickHandlers(this);
        activityAddEditBinding.setClickHandlers(addEditActivityClickHandlers);

        Intent intent = getIntent();

        if (intent.hasExtra(MOVIE_ID)) {
            setTitle("Edit budget");

            budget.setMovieName(intent.getStringExtra(MOVIE_NAME));
            budget.setMovieDescription(intent.getStringExtra(MOVIE_DESCRIPTION));
        } else {
            setTitle("Add budget");
        }

    }

    public class AddEditActivityClickHandlers {

        Context context;

        public AddEditActivityClickHandlers(Context context) {
            this.context = context;
        }

        public void onOkButtonClicked(View view) {

            if (budget.getMovieName() == null) {

                Toast.makeText(context, "Please input the mame", Toast.LENGTH_SHORT).show();

            } else {

                Intent intent = new Intent();
                intent.putExtra(MOVIE_NAME, budget.getMovieName());
                intent.putExtra(MOVIE_DESCRIPTION, budget.getMovieDescription());
                setResult(RESULT_OK, intent);
                finish();

            }

        }
    }
}
