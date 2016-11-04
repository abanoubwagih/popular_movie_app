package com.gmail.abanoub.mymal_popularmovies.movie.details;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.abanoub.mymal_popularmovies.BuildConfig;
import com.gmail.abanoub.mymal_popularmovies.R;
import com.gmail.abanoub.mymal_popularmovies.data.fetched.FetchMovieReviews;
import com.gmail.abanoub.mymal_popularmovies.data.fetched.FetchMovieTrailers;
import com.gmail.abanoub.mymal_popularmovies.data.fetched.FetchedMoviesList;
import com.gmail.abanoub.mymal_popularmovies.data.fetched.IMoviesServices;
import com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailsFragment extends Fragment {

    public static final String ARG_MOVIE_PARAM = "movie_param";
    private static final String LOG_TAG = MovieDetailsFragment.class.getSimpleName();

    @BindView(R.id.movie_poster)
    ImageView movie_poster;
    @BindView(R.id.movie_title)
    TextView movie_title;
    @BindView(R.id.movie_release_date)
    TextView movie_release_date;
    @BindView(R.id.movie_vote_average)
    TextView movie_vote_average;
    @BindView(R.id.movie_overview)
    TextView movie_overview;
    @BindView(R.id.review_recyclerView)
    RecyclerView review_recyclerView;
    @BindView(R.id.trailer_recyclerView)
    RecyclerView trailer_recyclerView;
    @BindView(R.id.movie_favourite)
    ImageView movie_favourite;

    private FetchedMoviesList.Movie movieParam;
    private ReviewRecyclerAdapter reviewRecyclerAdapter;
    private TrailerRecyclerAdapter trailerRecyclerAdapter;
    private Context context;
    private ArrayList<FetchMovieReviews.MovieReviews> movieReviews;
    private ArrayList<FetchMovieTrailers.MovieTrailer> movieTrailers;


    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    public static MovieDetailsFragment newInstance(FetchedMoviesList.Movie param1) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE_PARAM, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public static MovieDetailsFragment newInstance(Intent intent) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = intent.getExtras();
        fragment.setArguments(args);
        return fragment;
    }

    public static MovieDetailsFragment newInstance(Cursor cursor) {

        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE_PARAM, new FetchedMoviesList.Movie(cursor));
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate");

        if (savedInstanceState == null || !savedInstanceState.containsKey(getString(R.string.save_instance_movieParam))) {
            if (getArguments() != null) {
                movieParam = getArguments().getParcelable(ARG_MOVIE_PARAM);
                movieReviews = new ArrayList<>();
                movieTrailers = new ArrayList<>();
                updateMovieTrailer();
                updateMovieReview();
            }
        } else {
            movieParam = savedInstanceState.getParcelable(getString(R.string.save_instance_movieParam));
            movieTrailers = savedInstanceState.getParcelableArrayList(getString(R.string.save_instance_movieTrailers));
            movieReviews = savedInstanceState.getParcelableArrayList(getString(R.string.save_instance_movieReviews));
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(LOG_TAG, "onCreateView");
        getActivity().setTitle(movieParam.getTitle());

        if (getActivity().findViewById(R.id.movie_backdrop_image) != null) {

            ImageView backdrop = (ImageView) getActivity().findViewById(R.id.movie_backdrop_image);
            String imagePathdrob = context.getString(R.string.BASE_URL_FETCH_BACKDROP) + movieParam.getPoster_path();
            Picasso.with(getActivity()).load(imagePathdrob).into(backdrop);
        }

        View root = inflater.inflate(R.layout.fragment_movie_details, container, false);
        ButterKnife.bind(this, root);
        String imagePath = context.getString(R.string.BASE_URL_FETCH_POSTER) + movieParam.getPoster_path();
        Picasso.with(context).load(imagePath).into(movie_poster);
        movie_title.setText(movieParam.getTitle());
        movie_overview.setText(movieParam.getOverview());
        movie_release_date.setText(movieParam.getRelease_date());
        movie_vote_average.setText(String.valueOf(movieParam.getVote_average()));
        /**
         *  favourite
         */

        if (movieParam.isFavourite()) {
            movie_favourite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite));
        } else {
            movie_favourite.setImageDrawable(getResources().getDrawable(R.drawable.ic_not_favorite));
        }
        /**
         * review recycler
         */
        reviewRecyclerAdapter = new ReviewRecyclerAdapter(getActivity(), movieReviews);
        review_recyclerView.setAdapter(reviewRecyclerAdapter);
        review_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        review_recyclerView.setItemAnimator(new DefaultItemAnimator());
        /**
         * trailer recycler
         */
        trailerRecyclerAdapter = new TrailerRecyclerAdapter(getActivity(), movieTrailers);
        trailer_recyclerView.setAdapter(trailerRecyclerAdapter);
        trailer_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        trailer_recyclerView.setItemAnimator(new DefaultItemAnimator());

        return root;
    }


    private void updateMovieReview() {

        try {
            Uri uri = MoviesContract.appendUriWithId(MoviesContract.ReviewEntry.CONTENT_URI_TABLE, movieParam.getId());
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);

            if (cursor != null && cursor.getCount() != 0) {
                movieReviews = getMovieReviewsFromCursor(cursor);

                cursor.close();
                return;
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "error ", e);
            Toast.makeText(context, "error in retrive review data " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        try {


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.BASE_URL_FETCH_MOVIE_REVIEWS))
                    .client(new OkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Call<FetchMovieReviews> movieReviewCall = retrofit.create(IMoviesServices.class)
                    .MOVIE_REVIEWS_LIST_CALL(String.valueOf(movieParam.getId()), BuildConfig.API_KEY);

            movieReviewCall.enqueue(new Callback<FetchMovieReviews>() {
                @Override
                public void onResponse(Call<FetchMovieReviews> call, Response<FetchMovieReviews> response) {
                    ArrayList<ContentValues> contents = MoviesContract.getReviewsContentValues(response.body());
                    ContentResolver resolver = getActivity().getContentResolver();
                    int effectedRows = resolver.bulkInsert(MoviesContract.ReviewEntry.CONTENT_URI_TABLE,
                            contents.toArray(new ContentValues[contents.size()]));
                    Log.d(LOG_TAG, String.valueOf(effectedRows));

                    movieReviews = (ArrayList<FetchMovieReviews.MovieReviews>) response.body().getResults();
                    if (movieReviews != null) {
                        reviewRecyclerAdapter.addAndClear(movieReviews);
                    }
                }

                @Override
                public void onFailure(Call<FetchMovieReviews> call, Throwable t) {
                    Log.e(LOG_TAG, "error in fetch review  get MovieReviews ", t);
                }
            });
        } catch (Exception e) {
            Log.e(LOG_TAG, "you have no network");
            Toast.makeText(getActivity(), "on internet access " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }

    private ArrayList<FetchMovieReviews.MovieReviews> getMovieReviewsFromCursor(Cursor cursor) {
        ArrayList<FetchMovieReviews.MovieReviews> movieReviewses = new ArrayList<>();

        while (cursor.moveToNext()) {

            movieReviewses.add(new FetchMovieReviews.MovieReviews(cursor));
        }

        return movieReviewses;
    }

    private void updateMovieTrailer() {
        try {
            Uri uri = MoviesContract.appendUriWithId(MoviesContract.TrailerEntry.CONTENT_URI_TABLE, movieParam.getId());
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);

            if (cursor != null && cursor.getCount() != 0) {
                movieTrailers = getMovieTrailersFromCursor(cursor);

                cursor.close();
                return;
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "error ", e);
            Toast.makeText(context, "error in retrive trailer data " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(getString(R.string.BASE_URL_FETCH_MOVIE_TRAILERS))
                    .client(new OkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Call<FetchMovieTrailers> movieTrailersCall = retrofit.create(IMoviesServices.class)
                    .MOVIE_TRAILERS_LIST_CALL(String.valueOf(movieParam.getId()), BuildConfig.API_KEY);
            movieTrailersCall.enqueue(new Callback<FetchMovieTrailers>() {
                @Override
                public void onResponse(Call<FetchMovieTrailers> call, Response<FetchMovieTrailers> response) {

                    ArrayList<ContentValues> contents = MoviesContract.getTrailersContentValues(response.body());
                    ContentResolver resolver = getActivity().getContentResolver();

                    int effectedRows = resolver.bulkInsert(MoviesContract.TrailerEntry.CONTENT_URI_TABLE,
                            contents.toArray(new ContentValues[contents.size()]));
                    Log.d(LOG_TAG, String.valueOf(effectedRows));
                    movieTrailers = (ArrayList<FetchMovieTrailers.MovieTrailer>) response.body().getResults();

                    if (movieTrailers != null) {
                        trailerRecyclerAdapter.addAndClear(movieTrailers);
                    }
                }

                @Override
                public void onFailure(Call<FetchMovieTrailers> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Log.e(LOG_TAG, "you have no network");
            Toast.makeText(getActivity(), "on internet access " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    private ArrayList<FetchMovieTrailers.MovieTrailer> getMovieTrailersFromCursor(Cursor cursor) {
        ArrayList<FetchMovieTrailers.MovieTrailer> movieTrailers = new ArrayList<>();
        while (cursor.moveToNext()) {
            movieTrailers.add(new FetchMovieTrailers.MovieTrailer(cursor));
        }
        return movieTrailers;
    }


    @OnClick(R.id.movie_favourite)
    public void handleMovieFavourite(View view) {

        try {

            if (movieParam.isFavourite()) {
                movie_favourite.setImageDrawable(getResources().getDrawable(R.drawable.ic_not_favorite));
            } else {
                movie_favourite.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite));
            }

            new AsyncTask<Void, Void, Integer>() {

                @Override
                protected Integer doInBackground(Void... voids) {
                    Uri uri = MoviesContract.appendUriWithId(MoviesContract.MovieEntry.CONTENT_URI_TABLE, movieParam.getId());

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MoviesContract.MovieEntry.COLUMN_MOVIE_FAVOURITE, !movieParam.isFavourite());

                    int effective = context.getContentResolver().update(uri, contentValues, null, null);
                    return effective;
                }

                @Override
                protected void onPostExecute(Integer integer) {
                    Log.d(LOG_TAG, "update " + integer);
                }
            }.execute();

            movieParam.setFavourite(!movieParam.isFavourite());


        } catch (Exception e) {
            Toast.makeText(context, "error in update favourite data " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(LOG_TAG, "onSaveInstanceState");
        outState.putParcelable(getString(R.string.save_instance_movieParam), movieParam);
        outState.putParcelableArrayList(getString(R.string.save_instance_movieTrailers), movieTrailers);
        outState.putParcelableArrayList(getString(R.string.save_instance_movieReviews), movieReviews);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_movie_details_fragment, menu);
        try {
            MenuItem menuItem = menu.findItem(R.id.action_share);
            ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

            if (shareActionProvider != null) {

                shareActionProvider.setShareIntent(createShareTrailerIntent());
            } else {
                throw new NullPointerException("shareActionProvider is null");
            }

        } catch (Exception e) {
            Log.e(LOG_TAG, "wwwwwwwwww" + e.getMessage());
        }

    }

    private Intent createShareTrailerIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        intent.setType("text/plain");
        if (movieTrailers == null || movieTrailers.isEmpty()) {
            updateMovieTrailer();
        }
        String trailerPath = context.getString(R.string.BASE_URL_WATCH_TRAILER) + movieTrailers.get(0).getKey();
        intent.putExtra(Intent.EXTRA_TEXT, trailerPath);
        return intent;
    }
}
