package com.gmail.abanoub.mymal_popularmovies.setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gmail.abanoub.mymal_popularmovies.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getFragmentManager().beginTransaction().replace(R.id.fragment_setting,new SettingFragment()).commit();
    }


}
