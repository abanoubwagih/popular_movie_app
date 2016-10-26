package com.gmail.abanoub.mymal_popularmovies.data.provider;

import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

/**
 * Created by Abanoub on 25/10/2016.
 */

public class MovieDbHelperTest extends AndroidTestCase {
    public void testCreateDB()throws Exception{
        MovieDbHelper movieDbHelper = new MovieDbHelper(getContext());
        SQLiteDatabase sqLiteDatabase = movieDbHelper.getReadableDatabase();
        assertEquals("wromng",1,sqLiteDatabase.getVersion());
    }
}
