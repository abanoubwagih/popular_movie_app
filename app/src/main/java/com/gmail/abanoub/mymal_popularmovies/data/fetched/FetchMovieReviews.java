package com.gmail.abanoub.mymal_popularmovies.data.fetched;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract;

import java.util.List;

public class FetchMovieReviews implements Parcelable {

    public static final Creator<FetchMovieReviews> CREATOR = new Creator<FetchMovieReviews>() {
        @Override
        public FetchMovieReviews createFromParcel(Parcel in) {
            return new FetchMovieReviews(in);
        }

        @Override
        public FetchMovieReviews[] newArray(int size) {
            return new FetchMovieReviews[size];
        }
    };
    private int id;
    private int page;
    private int total_pages;
    private int total_results;
    private List<MovieReviews> results;

    protected FetchMovieReviews(Parcel in) {
        id = in.readInt();
        page = in.readInt();
        total_pages = in.readInt();
        total_results = in.readInt();
        results = in.createTypedArrayList(MovieReviews.CREATOR);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public List<MovieReviews> getResults() {
        return results;
    }

    public void setResults(List<MovieReviews> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(page);
        parcel.writeInt(total_pages);
        parcel.writeInt(total_results);
        parcel.writeTypedList(results);
    }

    public static class MovieReviews implements Parcelable {
        public static final Creator<MovieReviews> CREATOR = new Creator<MovieReviews>() {
            @Override
            public MovieReviews createFromParcel(Parcel in) {
                return new MovieReviews(in);
            }

            @Override
            public MovieReviews[] newArray(int size) {
                return new MovieReviews[size];
            }
        };
        private String id;
        private String author;
        private String content;
        private String url;

        public MovieReviews(Cursor cursor) {
            this.id = cursor.getString(cursor.getColumnIndex(MoviesContract.ReviewEntry.COLUMN_REVIEW_ID));
            this.author = cursor.getString(cursor.getColumnIndex(MoviesContract.ReviewEntry.COLUMN_REVIEW_AUTHOR));
            this.content = cursor.getString(cursor.getColumnIndex(MoviesContract.ReviewEntry.COLUMN_REVIEW_CONTENT));
            this.url = cursor.getString(cursor.getColumnIndex(MoviesContract.ReviewEntry.COLUMN_REVIEW_URL));
        }

        protected MovieReviews(Parcel in) {
            id = in.readString();
            author = in.readString();
            content = in.readString();
            url = in.readString();
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(id);
            parcel.writeString(author);
            parcel.writeString(content);
            parcel.writeString(url);
        }
    }
}
