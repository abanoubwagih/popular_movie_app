package com.gmail.abanoub.mymal_popularmovies;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.gmail.abanoub.mymal_popularmovies.data.fetched.FetchedMoviesList;
import com.gmail.abanoub.mymal_popularmovies.data.fetched.IMoviesServices;
import com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract;

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

public class MovieMainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = MovieMainActivityFragment.class.getSimpleName();
    private static final int MOVIE_LOADER_CALLBACK = 5151;
    @BindView(R.id.grid_movies_list)
    GridView gridView;
    int sortType;
    private IActivityFragmentCallBack iActivityFragmentCallBack;
    private Context context;
    private MovieCursorAdapter movieCursorAdapter;
    private int itemPositionIndex;

    public MovieMainActivityFragment() {
    }

    @OnItemClick(R.id.grid_movies_list)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        Cursor cursor = (Cursor) parent.getAdapter().getItem(position);

        iActivityFragmentCallBack.onSelectedItemFromGrid(cursor, position, id);
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
        this.context = context;
        if (context instanceof MovieMainActivityFragment.IActivityFragmentCallBack) {

            iActivityFragmentCallBack = (IActivityFragmentCallBack) context;
        } else {
            Log.e(LOG_TAG, "not instance of interface ");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMoviesList();

    }

    @Override
    public void onDetach() {
        super.onDetach();
        iActivityFragmentCallBack = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_movie_main, container, false);
        ButterKnife.bind(this, root);

        movieCursorAdapter = new MovieCursorAdapter(context, null);
        gridView.setAdapter(movieCursorAdapter);
        if (itemPositionIndex != 0) gridView.setSelection(itemPositionIndex);

        movieCursorAdapter.notifyDataSetChanged();
        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIE_LOADER_CALLBACK, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    private void updateMoviesList() {


        if (!isConnected()) {
            Toast.makeText(context, "no internet connection", Toast.LENGTH_LONG).show();
            getLoaderManager().restartLoader(MOVIE_LOADER_CALLBACK, null, MovieMainActivityFragment.this);
        }
        String SORT_TYPE = getSortType();
        sortType = (SORT_TYPE.equals(getString(R.string.sort_popular))) ?
                MoviesContract.MovieEntry.MOVIE_SORT_TYPE_POPULAR : MoviesContract.MovieEntry.MOVIE_SORT_TYPE_Rate;
        if (SORT_TYPE.equals(getString(R.string.sort_popular)))
            sortType = MoviesContract.MovieEntry.MOVIE_SORT_TYPE_POPULAR;
        else if (SORT_TYPE.equals(getString(R.string.sort_rate)))
            sortType = MoviesContract.MovieEntry.MOVIE_SORT_TYPE_Rate;
        else {
            getLoaderManager().restartLoader(MOVIE_LOADER_CALLBACK, null, MovieMainActivityFragment.this);
            return;
        }


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

                ContentResolver resolver = context.getContentResolver();
                ArrayList<ContentValues> contents = MoviesContract.getMoviesContentValues(response.body(), sortType);

                int effectedRows = resolver.bulkInsert(MoviesContract.MovieEntry.CONTENT_URI_TABLE,
                        contents.toArray(new ContentValues[contents.size()]));
                Log.d(LOG_TAG, String.valueOf(effectedRows));

            }

            @Override
            public void onFailure(Call<FetchedMoviesList> call, Throwable t) {
                Log.e(LOG_TAG, "retrofit error", t);
            }
        });

    }

    private String getSortType() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(getString(R.string.pref_sort_movies_list), getString(R.string.pref_sort_default));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null || !savedInstanceState.containsKey(getString(R.string.save_instance_itemPositionIndex))) {
//            moviesArrayList = new ArrayList<>();
            itemPositionIndex = 0;
            updateMoviesList();
        } else {
//            moviesArrayList = savedInstanceState.getParcelableArrayList(getString(R.string.save_instance_moviesArrayList));
            itemPositionIndex = savedInstanceState.getInt(getString(R.string.save_instance_itemPositionIndex));
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        itemPositionIndex = gridView.getFirstVisiblePosition();
//        outState.putParcelableArrayList(getString(R.string.save_instance_moviesArrayList), moviesArrayList);
        outState.putInt(getString(R.string.save_instance_itemPositionIndex), itemPositionIndex);

        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String SORT_TYPE = getSortType();
        Uri currentUri;

        if (SORT_TYPE.equals(getString(R.string.sort_popular)))
            currentUri = MoviesContract.MovieEntry.CONTENT_URI_POPULAR_MOVIES;
        else if (SORT_TYPE.equals(getString(R.string.sort_rate)))
            currentUri = MoviesContract.MovieEntry.CONTENT_URI_RATE_MOVIE;
        else currentUri = MoviesContract.MovieEntry.CONTENT_URI_FAVOURITE_MOVIE;


        return new CursorLoader(context, currentUri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        movieCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        movieCursorAdapter.swapCursor(null);
    }

    public boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());

    }

    public interface IActivityFragmentCallBack {
        void onSelectedItemFromGrid(Cursor cursor, int position, long id);
    }
}
