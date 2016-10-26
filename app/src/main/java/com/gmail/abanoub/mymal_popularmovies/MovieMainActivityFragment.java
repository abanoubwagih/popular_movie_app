package com.gmail.abanoub.mymal_popularmovies;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.gmail.abanoub.mymal_popularmovies.data.fetched.FetchedMoviesList;
import com.gmail.abanoub.mymal_popularmovies.data.fetched.IMoviesServices;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        moviesArrayAdapter = new MoviesArrayAdapter(getActivity(), R.layout.list_item_movie, new ArrayList<FetchedMoviesList.Movie>());
        gridView.setAdapter(moviesArrayAdapter);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMoviesList();
    }

    private void updateMoviesList() {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String SORT_TYPE = preferences.getString(getString(R.string.pref_sort_movies_list), getString(R.string.pref_sort_default));


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL_FETCH_List_MOVIES))
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Call<FetchedMoviesList> moviesListCall = retrofit.create(IMoviesServices.class)
                .MOVIES_LIST_CALL(SORT_TYPE, BuildConfig.API_KEY);

        moviesListCall.enqueue(new Callback<FetchedMoviesList>() {
            @Override
            public void onResponse(Call<FetchedMoviesList> call, Response<FetchedMoviesList> response) {
                List<FetchedMoviesList.Movie> movies = response.body().getResults();

                if (movies != null) {

                    moviesArrayAdapter.clear();
                    moviesArrayAdapter.addAll(movies);
                }
            }

            @Override
            public void onFailure(Call<FetchedMoviesList> call, Throwable t) {
                Log.e(LOG_TAG, "retrofit error", t);
            }
        });

    }


    public interface IActivityFragmentCallBack {
        void onSelectedItemFromGrid(AdapterView<?> parent, View view, int position, long id);
    }

}
