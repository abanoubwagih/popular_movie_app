package com.gmail.abanoub.mymal_popularmovies.data.provider;

import android.content.ContentValues;
import android.provider.BaseColumns;

import com.gmail.abanoub.mymal_popularmovies.data.fetched.FetchMovieReviews;
import com.gmail.abanoub.mymal_popularmovies.data.fetched.FetchMovieTrailers;
import com.gmail.abanoub.mymal_popularmovies.data.fetched.FetchedMoviesList;

import java.util.ArrayList;

import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.MovieEntry.COLUMN_MOVIE_ADULT;
import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.MovieEntry.COLUMN_MOVIE_BACKDROP_PATH;
import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.MovieEntry.COLUMN_MOVIE_ORIGINAL_LANGUAGE;
import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.MovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE;
import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.MovieEntry.COLUMN_MOVIE_OVERVIEW;
import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.MovieEntry.COLUMN_MOVIE_POPULARITY;
import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH;
import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE;
import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.MovieEntry.COLUMN_MOVIE_SORT_BY;
import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.MovieEntry.COLUMN_MOVIE_TITLE;
import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.MovieEntry.COLUMN_MOVIE_VIDEO;
import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE;
import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.MovieEntry.COLUMN_MOVIE_VOTE_COUNT;
import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.ReviewEntry.COLUMN_REVIEW_AUTHOR;
import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.ReviewEntry.COLUMN_REVIEW_CONTENT;
import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.ReviewEntry.COLUMN_REVIEW_ID;
import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.ReviewEntry.COLUMN_REVIEW_MOVIE_ID;
import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.ReviewEntry.COLUMN_REVIEW_URL;
import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.TrailerEntry.COLUMN_TRAILER_ID;
import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.TrailerEntry.COLUMN_TRAILER_ISO_3166_1;
import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.TrailerEntry.COLUMN_TRAILER_ISO_639_1;
import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.TrailerEntry.COLUMN_TRAILER_KEY;
import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.TrailerEntry.COLUMN_TRAILER_NAME;
import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.TrailerEntry.COLUMN_TRAILER_SITE;
import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.TrailerEntry.COLUMN_TRAILER_SIZE;
import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.TrailerEntry.COLUMN_TRAILER_TYPE;


public final class MoviesContract {

    public static final String DATA_TYPE_TEXT = " TEXT ";
    public static final String DATA_TYPE_INTEGER = " INTEGER ";
    public static final String DATA_TYPE_DOUBLE = " REAL ";
    public static final String DATA_TYPE_BOOLEAN = " NUMERIC ";
    public static final String NOT_NULL = " NOT NULL ";
    public static final String COMA_SP = " , ";
    public static final String AUTHORITY = "content://com.gmail.abanoub.mymal_popularmovies";

    public static ArrayList<ContentValues> getReviewsContentValues(FetchMovieReviews fetchMovieReviews) {
        ArrayList<ContentValues> contentValuesArrayList = new ArrayList<>();
        int movie_id = fetchMovieReviews.getId();
        for (FetchMovieReviews.MovieReviews movieReviews : fetchMovieReviews.getResults()) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(COLUMN_REVIEW_ID, movieReviews.getId());
            contentValues.put(COLUMN_REVIEW_AUTHOR, movieReviews.getAuthor());
            contentValues.put(COLUMN_REVIEW_CONTENT, movieReviews.getContent());
            contentValues.put(COLUMN_REVIEW_URL, movieReviews.getUrl());
            contentValues.put(COLUMN_REVIEW_MOVIE_ID, movie_id);
            contentValuesArrayList.add(contentValues);
        }

        return contentValuesArrayList;
    }

    public static ArrayList<ContentValues> getTrailersContentValues(FetchMovieTrailers fetchMovieTrailers) {
        ArrayList<ContentValues> contentValuesArrayList = new ArrayList<>();
        int movie_id = fetchMovieTrailers.getId();
        for (FetchMovieTrailers.MovieTrailer movieTrailer : fetchMovieTrailers.getResults()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_TRAILER_ID, movieTrailer.getId());
            contentValues.put(COLUMN_TRAILER_KEY, movieTrailer.getKey());
            contentValues.put(COLUMN_TRAILER_NAME, movieTrailer.getName());
            contentValues.put(COLUMN_TRAILER_SITE, movieTrailer.getSite());
            contentValues.put(COLUMN_TRAILER_SIZE, movieTrailer.getSize());
            contentValues.put(COLUMN_TRAILER_TYPE, movieTrailer.getType());
            contentValues.put(COLUMN_TRAILER_ISO_639_1, movieTrailer.getIso_639_1());
            contentValues.put(COLUMN_TRAILER_ISO_3166_1, movieTrailer.getIso_3166_1());
            contentValues.put(COLUMN_REVIEW_MOVIE_ID, movie_id);
            contentValuesArrayList.add(contentValues);
        }
        return contentValuesArrayList;
    }

    public static ArrayList<ContentValues> getMoviesContentValues(FetchedMoviesList fetchedMoviesList, int sortBy) {
        ArrayList<ContentValues> contentValuesArrayList = new ArrayList<>();
        for (FetchedMoviesList.Movie movie : fetchedMoviesList.getResults()) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(MovieEntry.COLUMN_MOVIE_ID, movie.getId());
            contentValues.put(COLUMN_MOVIE_TITLE, movie.getTitle());
            contentValues.put(COLUMN_MOVIE_POSTER_PATH, movie.getPoster_path());
            contentValues.put(COLUMN_MOVIE_BACKDROP_PATH, movie.getBackdrop_path());
            contentValues.put(COLUMN_MOVIE_OVERVIEW, movie.getOverview());
            contentValues.put(COLUMN_MOVIE_RELEASE_DATE, movie.getRelease_date());
            contentValues.put(COLUMN_MOVIE_ORIGINAL_TITLE, movie.getOriginal_title());
            contentValues.put(COLUMN_MOVIE_ORIGINAL_LANGUAGE, movie.getOriginal_language());
            contentValues.put(COLUMN_MOVIE_VOTE_COUNT, movie.getVote_count());
            contentValues.put(COLUMN_MOVIE_VOTE_AVERAGE, movie.getVote_average());
            contentValues.put(COLUMN_MOVIE_POPULARITY, movie.getPopularity());
            contentValues.put(COLUMN_MOVIE_VIDEO, movie.isVideo());
            contentValues.put(COLUMN_MOVIE_SORT_BY, sortBy);
            contentValues.put(COLUMN_MOVIE_ADULT, movie.isAdult());

            contentValuesArrayList.add(contentValues);
        }
        return contentValuesArrayList;
    }


    public final class ReviewEntry implements BaseColumns {


        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_REVIEW_ID = "review_id";
        public static final String COLUMN_REVIEW_AUTHOR = "review_author";
        public static final String COLUMN_REVIEW_CONTENT = "review_content";
        public static final String COLUMN_REVIEW_URL = "review_url";
        public static final String COLUMN_REVIEW_MOVIE_ID = "review_movie_id";

        public static final String TABLE_NAME = "ReviewEntry";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (  "
                + _ID + DATA_TYPE_INTEGER + " PRIMARY KEY  " + COMA_SP
                + COLUMN_REVIEW_ID + DATA_TYPE_INTEGER + NOT_NULL + " UNIQUE " + COMA_SP
                + COLUMN_REVIEW_AUTHOR + DATA_TYPE_TEXT + NOT_NULL + COMA_SP
                + COLUMN_REVIEW_CONTENT + DATA_TYPE_TEXT + NOT_NULL + COMA_SP
                + COLUMN_REVIEW_URL + DATA_TYPE_TEXT + NOT_NULL + COMA_SP
                + COLUMN_REVIEW_MOVIE_ID + DATA_TYPE_INTEGER + NOT_NULL + COMA_SP
                + " FOREIGN KEY (" + COLUMN_REVIEW_MOVIE_ID + ") REFERENCES " + MovieEntry.TABLE_NAME + "(" + MovieEntry.COLUMN_MOVIE_ID + "));";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS  " + TABLE_NAME;
        public static final String CONTENT_PROVIDER_URI_TABLE_PATTERN = TABLE_NAME;
        public static final String CONTENT_PROVIDER_URI_TABLE_RECORD_PATTERN = CONTENT_PROVIDER_URI_TABLE_PATTERN + "/#";
        public static final int CODE_TABLE = 100;
        public static final int CODE_TABLE_ID = 101;

        public static final String CONTENT_URI_TABLE = AUTHORITY + CONTENT_PROVIDER_URI_TABLE_PATTERN;
        public static final String CONTENT_URI_SPECIFIC_MOVIE = AUTHORITY + CONTENT_PROVIDER_URI_TABLE_RECORD_PATTERN;

    }

    public final class TrailerEntry implements BaseColumns {

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TRAILER_ID = "trailer_id";
        public static final String COLUMN_TRAILER_KEY = "trailer_key";
        public static final String COLUMN_TRAILER_NAME = "trailer_name";
        public static final String COLUMN_TRAILER_SITE = "trailer_site";
        public static final String COLUMN_TRAILER_SIZE = "trailer_size";
        public static final String COLUMN_TRAILER_TYPE = "trailer_type";
        public static final String COLUMN_TRAILER_ISO_639_1 = "trailer_iso_639_1";
        public static final String COLUMN_TRAILER_ISO_3166_1 = "trailer_iso_3166_1";
        public static final String COLUMN_TRAILER_MOVIE_ID = "trailer_movie_id";

        public static final String TABLE_NAME = "TrailerEntry";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
                + _ID + DATA_TYPE_INTEGER + " PRIMARY KEY  " + COMA_SP
                + COLUMN_TRAILER_ID + DATA_TYPE_TEXT + NOT_NULL + " UNIQUE " + COMA_SP
                + COLUMN_TRAILER_KEY + DATA_TYPE_TEXT + NOT_NULL + COMA_SP
                + COLUMN_TRAILER_NAME + DATA_TYPE_TEXT + NOT_NULL + COMA_SP
                + COLUMN_TRAILER_SITE + DATA_TYPE_TEXT + NOT_NULL + COMA_SP
                + COLUMN_TRAILER_SIZE + DATA_TYPE_INTEGER + NOT_NULL + COMA_SP
                + COLUMN_TRAILER_TYPE + DATA_TYPE_TEXT + NOT_NULL + COMA_SP
                + COLUMN_TRAILER_ISO_639_1 + DATA_TYPE_TEXT + NOT_NULL + COMA_SP
                + COLUMN_TRAILER_ISO_3166_1 + DATA_TYPE_TEXT + NOT_NULL + COMA_SP
                + COLUMN_TRAILER_MOVIE_ID + DATA_TYPE_INTEGER + NOT_NULL + COMA_SP
                + " FOREIGN KEY (" + COLUMN_TRAILER_MOVIE_ID + ") REFERENCES " + MovieEntry.TABLE_NAME + "(" + MovieEntry.COLUMN_MOVIE_ID + "));";

        public static final String DELETE_TABLE = " DROP TABLE IF EXISTS  " + TABLE_NAME;
        public static final String CONTENT_PROVIDER_URI_TABLE_PATTERN = TABLE_NAME;
        public static final String CONTENT_PROVIDER_URI_TABLE_RECORD_PATTERN = CONTENT_PROVIDER_URI_TABLE_PATTERN + "/#";
        public static final int CODE_TABLE = 200;
        public static final int CODE_TABLE_ID = 201;

        public static final String CONTENT_URI_TABLE = AUTHORITY + CONTENT_PROVIDER_URI_TABLE_PATTERN;
        public static final String CONTENT_URI_SPECIFIC_MOVIE = AUTHORITY + CONTENT_PROVIDER_URI_TABLE_RECORD_PATTERN;
    }

    public final class MovieEntry implements BaseColumns {

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_MOVIE_TITLE = "movie_title";
        public static final String COLUMN_MOVIE_POSTER_PATH = "movie_poster_path";
        public static final String COLUMN_MOVIE_BACKDROP_PATH = "movie_backdrop_path";
        public static final String COLUMN_MOVIE_OVERVIEW = "movie_overview";
        public static final String COLUMN_MOVIE_ADULT = "movie_adult";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "movie_release_date";
        public static final String COLUMN_MOVIE_ORIGINAL_TITLE = "movie_original_title";
        public static final String COLUMN_MOVIE_ORIGINAL_LANGUAGE = "movie_original_language";
        public static final String COLUMN_MOVIE_VOTE_COUNT = "movie_vote_count";
        public static final String COLUMN_MOVIE_VOTE_AVERAGE = "movie_vote_average";
        public static final String COLUMN_MOVIE_POPULARITY = "movie_popularity";
        public static final String COLUMN_MOVIE_VIDEO = "movie_video";
        public static final String COLUMN_MOVIE_FAVOURITE = "movie_favourite";
        public static final String COLUMN_MOVIE_SORT_BY = "movie_sort_by";


        public static final String TABLE_NAME = "MovieEntry";

        // sort type
        public static final int MOVIE_SORT_TYPE_POPULAR = 0;
        public static final int MOVIE_SORT_TYPE_Rate = 1;
        // is favourite
        public static final int MOVIE_NOT_FAVOURITE = 0;
        public static final int MOVIE_FAVOURITE = 1;


        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
                + _ID + DATA_TYPE_INTEGER + " PRIMARY KEY " + COMA_SP
                + COLUMN_MOVIE_ID + DATA_TYPE_INTEGER + NOT_NULL + " UNIQUE " + COMA_SP
                + COLUMN_MOVIE_TITLE + DATA_TYPE_TEXT + NOT_NULL + COMA_SP
                + COLUMN_MOVIE_POSTER_PATH + DATA_TYPE_TEXT + NOT_NULL + COMA_SP
                + COLUMN_MOVIE_BACKDROP_PATH + DATA_TYPE_TEXT + NOT_NULL + COMA_SP
                + COLUMN_MOVIE_OVERVIEW + DATA_TYPE_TEXT + NOT_NULL + COMA_SP
                + COLUMN_MOVIE_RELEASE_DATE + DATA_TYPE_TEXT + NOT_NULL + COMA_SP
                + COLUMN_MOVIE_ORIGINAL_TITLE + DATA_TYPE_TEXT + NOT_NULL + COMA_SP
                + COLUMN_MOVIE_ORIGINAL_LANGUAGE + DATA_TYPE_TEXT + NOT_NULL + COMA_SP
                + COLUMN_MOVIE_VOTE_COUNT + DATA_TYPE_INTEGER + NOT_NULL + COMA_SP
                + COLUMN_MOVIE_VOTE_AVERAGE + DATA_TYPE_DOUBLE + NOT_NULL + COMA_SP
                + COLUMN_MOVIE_POPULARITY + DATA_TYPE_DOUBLE + NOT_NULL + COMA_SP
                + COLUMN_MOVIE_VIDEO + DATA_TYPE_BOOLEAN + NOT_NULL + COMA_SP
                + COLUMN_MOVIE_SORT_BY + DATA_TYPE_BOOLEAN + NOT_NULL + " DEFAULT " + MOVIE_SORT_TYPE_POPULAR + COMA_SP
                + COLUMN_MOVIE_FAVOURITE + DATA_TYPE_BOOLEAN + NOT_NULL + " DEFAULT " + MOVIE_NOT_FAVOURITE + COMA_SP
                + COLUMN_MOVIE_ADULT + DATA_TYPE_BOOLEAN + NOT_NULL + " );";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS  " + TABLE_NAME;

        public static final String CONTENT_PROVIDER_URI_TABLE_PATTERN = TABLE_NAME;
        public static final String CONTENT_PROVIDER_URI_TABLE_POPULAR_PATTERN = "/popular";
        public static final String CONTENT_PROVIDER_URI_TABLE_RATE_PATTERN = "/rate";
        public static final String CONTENT_PROVIDER_URI_TABLE_FAVOURITE_PATTERN = "/favourite";
        public static final String CONTENT_PROVIDER_URI_TABLE_RECORD_PATTERN = CONTENT_PROVIDER_URI_TABLE_PATTERN + "/#";

        public static final int CODE_TABLE = 300;
        public static final int CODE_TABLE_ID = 301;
        public static final int CODE_TABLE_POPULAR = 302;
        public static final int CODE_TABLE_RATE = 303;
        public static final int CODE_TABLE_FAVOURITE = 304;

        public static final String CONTENT_URI_TABLE = AUTHORITY + CONTENT_PROVIDER_URI_TABLE_PATTERN;
        public static final String CONTENT_URI_SPECIFIC_MOVIE = AUTHORITY + CONTENT_PROVIDER_URI_TABLE_RECORD_PATTERN;
    }
}
