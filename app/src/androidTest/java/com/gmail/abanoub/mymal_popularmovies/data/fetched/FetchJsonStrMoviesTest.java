package com.gmail.abanoub.mymal_popularmovies.data.fetched;

import android.test.AndroidTestCase;

import com.gmail.abanoub.mymal_popularmovies.R;
import com.gmail.abanoub.mymal_popularmovies.data.fetched.FetchJsonStrMovies;
import com.gmail.abanoub.mymal_popularmovies.data.fetched.FetchMovieReview;
import com.gmail.abanoub.mymal_popularmovies.data.fetched.FetchedMoviesList;
import com.solidfire.gson.Gson;

/**
 * Created by Abanoub on 24/10/2016.
 */

public class FetchJsonStrMoviesTest extends AndroidTestCase {

    public void testFetchData() throws Exception {

        // fetch movies
        final String expected = "Star Trek Beyond";
        FetchJsonStrMovies fetchJsonStrMovies = new FetchJsonStrMovies();
        fetchJsonStrMovies.moviesCallBack = new FetchJsonStrMovies.FetchJsonStrMoviesCallBack() {
            @Override
            public void onPostExecute(String s) {
                Gson gson = new Gson();
                FetchedMoviesList fetchedMoviesList = gson.fromJson(s,FetchedMoviesList.class);
                assertEquals("data wrong",expected, fetchedMoviesList.getResults().get(0).getOriginal_title());
            }
        };

        fetchJsonStrMovies.execute("http://api.themoviedb.org/3/movie/popular?api_key=2900c1804e7ec7ef483309a5021aa377");
        // end fetch movies


        // fetch review
        FetchJsonStrMovies fetchJsonStrMovies1 = new FetchJsonStrMovies();
        fetchJsonStrMovies1.moviesCallBack = new FetchJsonStrMovies.FetchJsonStrMoviesCallBack() {
            @Override
            public void onPostExecute(String s) {
                Gson gson = new Gson();
                FetchMovieReview fetchMovieReview = gson.fromJson(s,FetchMovieReview.class);
                assertEquals("data wrong","Frank Ochieng", fetchMovieReview.getResults().get(0).getAuthor());
            }
        };

        String bac = String.format(getContext().getString(R.string.BASE_URL_FETCH_MOVIE_REVIEW),188927)+"?api_key=2900c1804e7ec7ef483309a5021aa377";
        fetchJsonStrMovies1.execute(bac);

        // end fetch review
    }
}
