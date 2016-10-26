package com.gmail.abanoub.mymal_popularmovies.setting;


import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.gmail.abanoub.mymal_popularmovies.R;


public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {


    public SettingFragment() {
        super();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prf_general);
        bindPreferenceChangeToSummery(findPreference(getString(R.string.pref_sort_movies_list)));
    }

    private void bindPreferenceChangeToSummery(Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), "")
        );
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String preferenceStringValue = newValue.toString();
        if (preference instanceof ListPreference) {

            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(preferenceStringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }
        return true;

    }

}
