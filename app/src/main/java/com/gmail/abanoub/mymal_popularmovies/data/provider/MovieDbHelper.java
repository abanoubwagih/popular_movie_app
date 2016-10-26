package com.gmail.abanoub.mymal_popularmovies.data.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Abanoub on 25/10/2016.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    public static final int MOVIE_DATABASE_VERSION = 1;
    public static final String MOVIE_DATABASE_NAME = "movie_dabase.db";

    public MovieDbHelper(Context context) {
        super(context, MOVIE_DATABASE_NAME, null, MOVIE_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(MoviesContract.MovieEntry.CREATE_TABLE);
        sqLiteDatabase.execSQL(MoviesContract.ReviewEntry.CREATE_TABLE);
        sqLiteDatabase.execSQL(MoviesContract.TrailerEntry.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(MoviesContract.MovieEntry.DELETE_TABLE);
        sqLiteDatabase.execSQL(MoviesContract.ReviewEntry.DELETE_TABLE);
        sqLiteDatabase.execSQL(MoviesContract.TrailerEntry.DELETE_TABLE);

        onCreate(sqLiteDatabase);
    }
}
