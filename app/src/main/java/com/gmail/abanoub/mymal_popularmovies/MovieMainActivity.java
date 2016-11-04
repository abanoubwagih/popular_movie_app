package com.gmail.abanoub.mymal_popularmovies;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.gmail.abanoub.mymal_popularmovies.data.fetched.FetchedMoviesList;
import com.gmail.abanoub.mymal_popularmovies.movie.details.MovieDetailsActivity;
import com.gmail.abanoub.mymal_popularmovies.movie.details.MovieDetailsFragment;
import com.gmail.abanoub.mymal_popularmovies.setting.SettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieMainActivity extends AppCompatActivity implements MovieMainActivityFragment.IActivityFragmentCallBack {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private boolean largeScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
        largeScreen = findViewById(R.id.fragment_details) != null;
        if (savedInstanceState == null) {
            MovieMainActivityFragment mainActivityFragment = new MovieMainActivityFragment();
            getFragmentManager().beginTransaction().add(R.id.fragment_main, mainActivityFragment).commit();
        } else {

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSelectedItemFromGrid(Cursor cursor, int position, long id) {

        if (largeScreen) {
            MovieDetailsFragment movieDetailsFragment =
                    MovieDetailsFragment.newInstance(cursor);
            getFragmentManager().beginTransaction().replace(R.id.fragment_details, movieDetailsFragment).commit();

        } else {

            Bundle bundle = new Bundle();
            bundle.putParcelable(MovieDetailsFragment.ARG_MOVIE_PARAM, new FetchedMoviesList.Movie(cursor));
            Intent intent = new Intent(this, MovieDetailsActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }


}
