package com.gmail.abanoub.mymal_popularmovies;

import android.app.Activity;
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

    private static final String LOG_TAG = MovieMainActivityFragment.class.getSimpleName();
    @BindView(R.id.grid_movies_list)
    GridView gridView;
    private IActivityFragmentCallBack iActivityFragmentCallBack;
    private Context context;
    private MoviesArrayAdapter moviesArrayAdapter;
    private ArrayList<FetchedMoviesList.Movie> moviesArrayList;
    private int itemPositionIndex;

    public MovieMainActivityFragment() {
        Log.d(LOG_TAG, "MovieMainActivityFragment");
    }

    @OnItemClick(R.id.grid_movies_list)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        iActivityFragmentCallBack.onSelectedItemFromGrid(moviesArrayAdapter.getItem(position), position, id);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachStarted(context);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onAttachStarted(activity);
    }

    private void onAttachStarted(Context context) {
        Log.d(LOG_TAG, "onAttach");

        this.context = context;
        if (context instanceof MovieMainActivityFragment.IActivityFragmentCallBack) {

            iActivityFragmentCallBack = (IActivityFragmentCallBack) context;
        } else {
            Log.e(LOG_TAG, "not instance of interface ");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        iActivityFragmentCallBack = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d(LOG_TAG, "onCreateView");
        View root = inflater.inflate(R.layout.fragment_movie_main, container, false);
        ButterKnife.bind(this, root);
        moviesArrayAdapter = new MoviesArrayAdapter(getActivity(), R.layout.list_item_movie, moviesArrayList);
        gridView.setAdapter(moviesArrayAdapter);
        if (itemPositionIndex != 0) gridView.setSelection(itemPositionIndex);
        moviesArrayAdapter.notifyDataSetChanged();
        return root;
    }

    private void updateMoviesList() {

        Log.d(LOG_TAG, "updateMoviesList");
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
                moviesArrayList = (ArrayList<FetchedMoviesList.Movie>) response.body().getResults();

                if (moviesArrayList != null) {

                    Log.d(LOG_TAG, "update adapter");

                    moviesArrayAdapter.clear();
                    moviesArrayAdapter.addAll(moviesArrayList);
                }
            }

            @Override
            public void onFailure(Call<FetchedMoviesList> call, Throwable t) {
                Log.e(LOG_TAG, "retrofit error", t);
            }
        });

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate");

        if (savedInstanceState == null || !savedInstanceState.containsKey(getString(R.string.save_instance_moviesArrayList))) {
            moviesArrayList = new ArrayList<>();
            Log.d(LOG_TAG, "onCreateView no instansce ");
            itemPositionIndex = 0;
            updateMoviesList();


        } else {
            Log.d(LOG_TAG, "onCreateView with instance ");

            moviesArrayList = savedInstanceState.getParcelableArrayList(getString(R.string.save_instance_moviesArrayList));
            itemPositionIndex = savedInstanceState.getInt(getString(R.string.save_instance_itemPositionIndex));
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        itemPositionIndex = gridView.getFirstVisiblePosition();
        outState.putParcelableArrayList(getString(R.string.save_instance_moviesArrayList), moviesArrayList);
        outState.putInt(getString(R.string.save_instance_itemPositionIndex), itemPositionIndex);
        Log.d(LOG_TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    public interface IActivityFragmentCallBack {
        void onSelectedItemFromGrid(FetchedMoviesList.Movie movie, int position, long id);
    }

}
