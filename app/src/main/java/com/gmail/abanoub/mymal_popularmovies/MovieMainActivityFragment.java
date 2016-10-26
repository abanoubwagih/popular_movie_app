package com.gmail.abanoub.mymal_popularmovies;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.gmail.abanoub.mymal_popularmovies.data.fetched.FetchJsonStrMovies;
import com.gmail.abanoub.mymal_popularmovies.data.fetched.FetchedMoviesList;
import com.solidfire.gson.Gson;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class MovieMainActivityFragment extends Fragment {

    public static final String LOG_TAG = MovieMainActivityFragment.class.getSimpleName();
    public IActivityFragmentCallBack iActivityFragmentCallBack;
    @BindView(R.id.grid_movies_list)
    GridView gridView;

    private MoviesArrayAdapter moviesArrayAdapter;

    public MovieMainActivityFragment() {
    }

    @OnItemClick(R.id.grid_movies_list)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        iActivityFragmentCallBack.onSelectedItemFromGrid(parent, view, position, id);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(context instanceof IActivityFragmentCallBack)) throw new AssertionError();
        iActivityFragmentCallBack = (IActivityFragmentCallBack) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_movie_main, container, false);
        ButterKnife.bind(this, root);
        moviesArrayAdapter = new MoviesArrayAdapter(getActivity(), R.layout.list_item_layout, new ArrayList<FetchedMoviesList.Movie>());
        gridView.setAdapter(moviesArrayAdapter);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMoviesList();
    }

    private void updateMoviesList() {

        String urlPath = null;
        try {
            urlPath = getMoviesListUrl();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "url of movies error" + e.getMessage(), e);
        }

        FetchJsonStrMovies fetchJsonStrMovies = new FetchJsonStrMovies();
        fetchJsonStrMovies.moviesCallBack = new FetchJsonStrMovies.FetchJsonStrMoviesCallBack() {
            @Override
            public void onPostExecute(String s) {
                // fetching list of movies
                Gson gson = new Gson();
                FetchedMoviesList fetchedMoviesList = gson.fromJson(s, FetchedMoviesList.class);
                // add the list to array and adapter
                List<FetchedMoviesList.Movie> movies = fetchedMoviesList.getResults();

                if (movies != null) {

                    moviesArrayAdapter.clear();
                    moviesArrayAdapter.addAll(movies);
                }
            }
        };
        fetchJsonStrMovies.execute(urlPath);
    }

    private String getMoviesListUrl() throws MalformedURLException {
        URL url;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String SORT_TYPE = preferences.getString(getString(R.string.pref_sort_movies_list), getString(R.string.pref_sort_default));

        final String MOVIES_BASE_SITE = getString(R.string.BASE_URL_FETCH_List_MOVIES) + SORT_TYPE;
        final String API_KEY_PARAM = "api_key";

        Uri uri = Uri.parse(MOVIES_BASE_SITE).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, BuildConfig.API_KEY).build();

        return uri.toString();
    }

    public interface IActivityFragmentCallBack {
        void onSelectedItemFromGrid(AdapterView<?> parent, View view, int position, long id);
    }

}
