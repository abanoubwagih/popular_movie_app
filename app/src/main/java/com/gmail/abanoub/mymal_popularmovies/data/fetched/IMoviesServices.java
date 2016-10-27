package com.gmail.abanoub.mymal_popularmovies.data.fetched;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IMoviesServices {

    @GET("{sort_type}")
    Call<FetchedMoviesList> MOVIES_LIST_CALL(@Path("sort_type") String SORT_TYPE, @Query("api_key") String api_key);

    @GET("{movie_id}/reviews")
    Call<FetchMovieReviews> MOVIE_REVIEWS_LIST_CALL(@Path("movie_id") String MOVIE_ID, @Query("api_key") String api_key);

    @GET("{movie_id}/videos")
    Call<FetchMovieTrailers> MOVIE_TRAILERS_LIST_CALL(@Path("movie_id") String MOVIE_ID, @Query("api_key") String api_key);

}
