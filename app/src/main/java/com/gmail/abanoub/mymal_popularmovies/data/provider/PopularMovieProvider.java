package com.gmail.abanoub.mymal_popularmovies.data.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.MovieEntry;
import com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.ReviewEntry;
import com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.TrailerEntry;

import static com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract.AUTHORITY;


public class PopularMovieProvider extends ContentProvider {
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String LOG_TAG = PopularMovieProvider.class.getSimpleName();

    static {
        uriMatcher.addURI(AUTHORITY, ReviewEntry.CONTENT_PROVIDER_URI_TABLE_PATTERN, ReviewEntry.CODE_TABLE);
        uriMatcher.addURI(AUTHORITY, ReviewEntry.CONTENT_PROVIDER_URI_TABLE_RECORD_PATTERN, ReviewEntry.CODE_TABLE_ID);

        uriMatcher.addURI(AUTHORITY, TrailerEntry.CONTENT_PROVIDER_URI_TABLE_PATTERN, TrailerEntry.CODE_TABLE);
        uriMatcher.addURI(AUTHORITY, TrailerEntry.CONTENT_PROVIDER_URI_TABLE_RECORD_PATTERN, TrailerEntry.CODE_TABLE_ID);

        uriMatcher.addURI(AUTHORITY, MovieEntry.CONTENT_PROVIDER_URI_TABLE_PATTERN, MovieEntry.CODE_TABLE);
        uriMatcher.addURI(AUTHORITY, MovieEntry.CONTENT_PROVIDER_URI_TABLE_RECORD_PATTERN, MovieEntry.CODE_TABLE_ID);
        uriMatcher.addURI(AUTHORITY, MovieEntry.CONTENT_PROVIDER_URI_TABLE_POPULAR_PATTERN, MovieEntry.CODE_TABLE_POPULAR);
        uriMatcher.addURI(AUTHORITY, MovieEntry.CONTENT_PROVIDER_URI_TABLE_RATE_PATTERN, MovieEntry.CODE_TABLE_RATE);
        uriMatcher.addURI(AUTHORITY, MovieEntry.CONTENT_PROVIDER_URI_TABLE_FAVOURITE_PATTERN, MovieEntry.CODE_TABLE_FAVOURITE);
    }

    private MovieDbHelper movieDbHelper;

    @Override
    public boolean onCreate() {
        movieDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase sqLiteDatabase = movieDbHelper.getReadableDatabase();

        int match = uriMatcher.match(uri);


        Cursor cursor;
        switch (match) {
            case ReviewEntry.CODE_TABLE_ID:
                selection = ReviewEntry.COLUMN_REVIEW_MOVIE_ID + " = ? ";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = sqLiteDatabase.query(ReviewEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case TrailerEntry.CODE_TABLE_ID:
                selection = TrailerEntry.COLUMN_TRAILER_MOVIE_ID + " = ? ";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = sqLiteDatabase.query(TrailerEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case MovieEntry.CODE_TABLE:
                cursor = sqLiteDatabase.query(MovieEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case MovieEntry.CODE_TABLE_ID:
                selection = MovieEntry.COLUMN_MOVIE_ID + " = ? ";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = sqLiteDatabase.query(MovieEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case MovieEntry.CODE_TABLE_POPULAR:
                selection = MovieEntry.COLUMN_MOVIE_SORT_BY + " = ? ";
                selectionArgs = new String[]{String.valueOf(MovieEntry.MOVIE_SORT_TYPE_POPULAR)};
                cursor = sqLiteDatabase.query(MovieEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case MovieEntry.CODE_TABLE_RATE:
                selection = MovieEntry.COLUMN_MOVIE_SORT_BY + " = ? ";
                selectionArgs = new String[]{String.valueOf(MovieEntry.MOVIE_SORT_TYPE_Rate)};
                cursor = sqLiteDatabase.query(MovieEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case MovieEntry.CODE_TABLE_FAVOURITE:
                selection = MovieEntry.COLUMN_MOVIE_FAVOURITE + " = ? ";
                selectionArgs = new String[]{String.valueOf(MovieEntry.MOVIE_FAVOURITE)};
                cursor = sqLiteDatabase.query(MovieEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("cannot query. unknown uri " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        int match = uriMatcher.match(uri);
        SQLiteDatabase sqLiteDatabase = movieDbHelper.getWritableDatabase();
        long id;

        switch (match) {
            case ReviewEntry.CODE_TABLE:
                id = sqLiteDatabase.insertWithOnConflict(ReviewEntry.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case TrailerEntry.CODE_TABLE:
                id = sqLiteDatabase.insertWithOnConflict(TrailerEntry.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            case MovieEntry.CODE_TABLE:
                id = sqLiteDatabase.insertWithOnConflict(MovieEntry.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                break;
            default:
                throw new IllegalArgumentException("cannot query unknown uri " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        int match = uriMatcher.match(uri);
        SQLiteDatabase sqLiteDatabase = movieDbHelper.getWritableDatabase();
        int id = -1;

        switch (match) {
            case ReviewEntry.CODE_TABLE_ID:
                selection = ReviewEntry.COLUMN_REVIEW_MOVIE_ID + " = ? ";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                id = sqLiteDatabase.delete(ReviewEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case TrailerEntry.CODE_TABLE_ID:
                selection = TrailerEntry.COLUMN_TRAILER_MOVIE_ID + " = ? ";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                id = sqLiteDatabase.delete(TrailerEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MovieEntry.CODE_TABLE_POPULAR:
                selection = MovieEntry.COLUMN_MOVIE_SORT_BY + " = ? ";
                selectionArgs = new String[]{String.valueOf(MovieEntry.MOVIE_SORT_TYPE_POPULAR)};
                id = sqLiteDatabase.delete(MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case MovieEntry.CODE_TABLE_RATE:
                selection = MovieEntry.COLUMN_MOVIE_SORT_BY + " = ? ";
                selectionArgs = new String[]{String.valueOf(MovieEntry.MOVIE_SORT_TYPE_Rate)};
                id = sqLiteDatabase.delete(MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("cannot delete . unknown uri " + uri);
        }

        if (id != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return id;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        int match = uriMatcher.match(uri);
        SQLiteDatabase sqLiteDatabase = movieDbHelper.getWritableDatabase();
        int id = -1;

        switch (match) {
            case MovieEntry.CODE_TABLE_ID:
                selection = MovieEntry.COLUMN_MOVIE_ID + " = ? ";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                id = sqLiteDatabase.update(MovieEntry.TABLE_NAME, contentValues, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("cannot insert . unknown uri " + uri);
        }
        if (id != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return id;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        int match = uriMatcher.match(uri);
        SQLiteDatabase sqLiteDatabase = movieDbHelper.getWritableDatabase();

        int count = 0;
        switch (match) {
            case ReviewEntry.CODE_TABLE:
                sqLiteDatabase.beginTransaction();

                try {
                    for (ContentValues value : values) {

                        long _id = sqLiteDatabase.insertWithOnConflict(ReviewEntry.TABLE_NAME, null, value, SQLiteDatabase.CONFLICT_IGNORE);
                        if (_id != -1) count++;
                    }
                    sqLiteDatabase.setTransactionSuccessful();
                } finally {
                    sqLiteDatabase.endTransaction();
                }
                break;
            case TrailerEntry.CODE_TABLE:
                sqLiteDatabase.beginTransaction();

                try {
                    for (ContentValues value : values) {

                        long _id = sqLiteDatabase.insertWithOnConflict(TrailerEntry.TABLE_NAME, null, value, SQLiteDatabase.CONFLICT_IGNORE);
                        if (_id != -1) count++;
                    }
                    sqLiteDatabase.setTransactionSuccessful();
                } finally {
                    sqLiteDatabase.endTransaction();
                }
                break;
            case MovieEntry.CODE_TABLE:
                sqLiteDatabase.beginTransaction();

                try {
                    for (ContentValues value : values) {

                        long _id = sqLiteDatabase.insertWithOnConflict(MovieEntry.TABLE_NAME, null, value, SQLiteDatabase.CONFLICT_IGNORE);
                        if (_id != -1) count++;
                    }
                    sqLiteDatabase.setTransactionSuccessful();
                } finally {
                    sqLiteDatabase.endTransaction();
                }
                break;
            default:
                return super.bulkInsert(uri, values);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
