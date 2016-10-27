package com.gmail.abanoub.mymal_popularmovies.movie.details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.gmail.abanoub.mymal_popularmovies.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MovieDetailsFragment movieDetailsFragment = MovieDetailsFragment.newInstance(getIntent());
        getFragmentManager().beginTransaction().replace(R.id.fragment_details, movieDetailsFragment).commit();

    }

}
