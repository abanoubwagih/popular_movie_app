package com.gmail.abanoub.mymal_popularmovies.data.fetched;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Abanoub on 24/10/2016.
 */

public class FetchMovieReview implements Parcelable{

    private int id;
    private int page;
    private int total_pages;
    private int total_results;

    private List<MovieReview> results;

    protected FetchMovieReview(Parcel in) {
        id = in.readInt();
        page = in.readInt();
        total_pages = in.readInt();
        total_results = in.readInt();
        results = in.createTypedArrayList(MovieReview.CREATOR);
    }

    public static final Creator<FetchMovieReview> CREATOR = new Creator<FetchMovieReview>() {
        @Override
        public FetchMovieReview createFromParcel(Parcel in) {
            return new FetchMovieReview(in);
        }

        @Override
        public FetchMovieReview[] newArray(int size) {
            return new FetchMovieReview[size];
        }
    };

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

    public List<MovieReview> getResults() {
        return results;
    }

    public void setResults(List<MovieReview> results) {
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

    public static class MovieReview implements Parcelable{
        private String id;
        private String author;
        private String content;
        private String url;

        protected MovieReview(Parcel in) {
            id = in.readString();
            author = in.readString();
            content = in.readString();
            url = in.readString();
        }

        public static final Creator<MovieReview> CREATOR = new Creator<MovieReview>() {
            @Override
            public MovieReview createFromParcel(Parcel in) {
                return new MovieReview(in);
            }

            @Override
            public MovieReview[] newArray(int size) {
                return new MovieReview[size];
            }
        };

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
