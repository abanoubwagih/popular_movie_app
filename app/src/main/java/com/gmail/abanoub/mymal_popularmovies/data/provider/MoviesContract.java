package com.gmail.abanoub.mymal_popularmovies.data.provider;

import android.provider.BaseColumns;

public final class MoviesContract {

    public static final String DATA_TYPE_TEXT = " TEXT ";
    public static final String DATA_TYPE_INTEGER = " INTEGER ";
    public static final String DATA_TYPE_DOUBLE = " REAL ";
    public static final String DATA_TYPE_BOOLEAN = " NUMERIC ";
    public static final String NOT_NULL = " NOT NULL ";
    public static final String COMA_SP = " , ";

    public final class ReviewEntry implements BaseColumns {

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_REVIEW_ID = "review_id";
        public static final String COLUMN_REVIEW_AUTHOR = "review_author";
        public static final String COLUMN_REVIEW_CONTENT = "review_content";
        public static final String COLUMN_REVIEW_URL = "review_url";

        public static final String TABLE_NAME = "ReviewEntry";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (  "
                + _ID + DATA_TYPE_INTEGER + " PRIMARY KEY  " + COMA_SP
                + COLUMN_REVIEW_ID + DATA_TYPE_INTEGER + NOT_NULL + " UNIQUE " + COMA_SP
                + COLUMN_REVIEW_AUTHOR + DATA_TYPE_TEXT + NOT_NULL + COMA_SP
                + COLUMN_REVIEW_CONTENT + DATA_TYPE_TEXT + NOT_NULL + COMA_SP
                + COLUMN_REVIEW_URL + DATA_TYPE_TEXT + NOT_NULL + " );";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS  " + TABLE_NAME;

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
                + COLUMN_TRAILER_ISO_3166_1 + DATA_TYPE_TEXT + NOT_NULL + " );";

        public static final String DELETE_TABLE = " DROP TABLE IF EXISTS  " + TABLE_NAME;
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

        public static final String TABLE_NAME = "MovieEntry";

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
                + COLUMN_MOVIE_ADULT + DATA_TYPE_BOOLEAN + NOT_NULL + " );";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS  " + TABLE_NAME;
    }
}
