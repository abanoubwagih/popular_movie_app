package com.gmail.abanoub.mymal_popularmovies.data.fetched;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



/**
 * Created by Abanoub on 24/10/2016.
 */

public class FetchJsonStrMovies extends AsyncTask<String, Void, String> {


    private static final String LOG_TAG = FetchJsonStrMovies.class.getSimpleName();
    public FetchJsonStrMoviesCallBack moviesCallBack;

    @Override
    protected String doInBackground(String... url) {

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url[0]).build();
        try {

            Response response = okHttpClient.newCall(request).execute();

            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG,e.getMessage(),e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        moviesCallBack.onPostExecute(s);
    }

    public interface FetchJsonStrMoviesCallBack {
        void onPostExecute(String s);
    }
}
