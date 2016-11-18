package com.gmail.abanoub.mymal_popularmovies.data.fetched;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract;

import java.util.ArrayList;
import java.util.List;

public class FetchedMoviesList implements Parcelable{

    public static final Creator<FetchedMoviesList> CREATOR = new Creator<FetchedMoviesList>() {
        @Override
        public FetchedMoviesList createFromParcel(Parcel in) {
            return new FetchedMoviesList(in);
        }

        @Override
        public FetchedMoviesList[] newArray(int size) {
            return new FetchedMoviesList[size];
        }
    };
    private int page;
    private int total_results;
    private int total_pages;
    private List<Movie> results;

    protected FetchedMoviesList(Parcel in) {
        page = in.readInt();
        total_results = in.readInt();
        total_pages = in.readInt();
        results = in.createTypedArrayList(Movie.CREATOR);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(page);
        parcel.writeInt(total_results);
        parcel.writeInt(total_pages);
        parcel.writeTypedList(results);
    }

    public static class Movie implements Parcelable {
        public static final Creator<Movie> CREATOR = new Creator<Movie>() {
            @Override
            public Movie createFromParcel(Parcel in) {
                return new Movie(in);
            }

            @Override
            public Movie[] newArray(int size) {
                return new Movie[size];
            }
        };
        private String poster_path;
        private boolean adult;
        private String overview;
        private String release_date;
        private int id;
        private String original_title;
        private String original_language;
        private String title;
        private String backdrop_path;
        private double popularity;
        private int vote_count;
        private boolean video;
        private double vote_average;
        private List<Integer> genre_ids;
        private boolean favourite;

        public Movie() {
        }

        public Movie(Cursor cursor) {

            this.poster_path = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH));
            this.overview = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_OVERVIEW));
            this.release_date = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE));
            this.original_title = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE));
            this.original_language = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_ORIGINAL_LANGUAGE));
            this.title = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_TITLE));
            this.backdrop_path = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_BACKDROP_PATH));
            this.adult = cursor.getInt(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_ADULT)) == 1;
            this.id = cursor.getInt(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_ID));
            this.popularity = cursor.getDouble(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_POPULARITY));
            this.vote_count = cursor.getInt(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_VOTE_COUNT));
            this.video = cursor.getInt(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_VIDEO)) == 1;
            this.vote_average = cursor.getDouble(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE));
            this.favourite = cursor.getInt(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_FAVOURITE)) == 1;
            this.genre_ids = new ArrayList<>();

        }

        protected Movie(Parcel in) {
            poster_path = in.readString();
            adult = in.readByte() != 0;
            overview = in.readString();
            release_date = in.readString();
            id = in.readInt();
            original_title = in.readString();
            original_language = in.readString();
            title = in.readString();
            backdrop_path = in.readString();
            popularity = in.readDouble();
            vote_count = in.readInt();
            video = in.readByte() != 0;
            favourite = in.readByte() != 0;
            vote_average = in.readDouble();
            int count = in.readInt();
            genre_ids = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                genre_ids.add(in.readInt());
            }

        }

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public boolean isAdult() {
            return adult;
        }

        public void setAdult(boolean adult) {
            this.adult = adult;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getOriginal_language() {
            return original_language;
        }

        public void setOriginal_language(String original_language) {
            this.original_language = original_language;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public void setBackdrop_path(String backdrop_path) {
            this.backdrop_path = backdrop_path;
        }

        public double getPopularity() {
            return popularity;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        public int getVote_count() {
            return vote_count;
        }

        public void setVote_count(int vote_count) {
            this.vote_count = vote_count;
        }

        public boolean isVideo() {
            return video;
        }

        public void setVideo(boolean video) {
            this.video = video;
        }

        public double getVote_average() {
            return vote_average;
        }

        public void setVote_average(double vote_average) {
            this.vote_average = vote_average;
        }

        public List<Integer> getGenre_ids() {
            return genre_ids;
        }

        public void setGenre_ids(List<Integer> genre_ids) {
            this.genre_ids = genre_ids;
        }

        @Override
        public String toString() {
            return "Movie{" +
                    "id=" + id +
                    ", original_title='" + original_title + '\'' +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(poster_path);
            parcel.writeByte((byte) (adult ? 1 : 0));
            parcel.writeString(overview);
            parcel.writeString(release_date);
            parcel.writeInt(id);
            parcel.writeString(original_title);
            parcel.writeString(original_language);
            parcel.writeString(title);
            parcel.writeString(backdrop_path);
            parcel.writeDouble(popularity);
            parcel.writeInt(vote_count);
            parcel.writeByte((byte) (video ? 1 : 0));
            parcel.writeByte((byte) (favourite ? 1 : 0));
            parcel.writeDouble(vote_average);
            int count = genre_ids.size();
            parcel.writeInt(count);
            for (int j = 0; j < count; j++) {
                parcel.writeInt(genre_ids.get(i));
            }
        }

        public boolean isFavourite() {
            return favourite;
        }

        public void setFavourite(boolean favourite) {
            this.favourite = favourite;
        }
    }
}
