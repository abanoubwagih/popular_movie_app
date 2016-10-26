package com.gmail.abanoub.mymal_popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.gmail.abanoub.mymal_popularmovies.setting.SettingActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieMainActivity extends AppCompatActivity implements MovieMainActivityFragment.IActivityFragmentCallBack {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private boolean largeScreen ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        largeScreen = findViewById(R.id.fragment_details) != null;
        MovieMainActivityFragment mainActivityFragment = new MovieMainActivityFragment();
        getFragmentManager().beginTransaction().replace(R.id.fragment_main,mainActivityFragment).commit();

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
    public void onSelectedItemFromGrid(AdapterView<?> parent, View view, int position, long id) {

    }
}
