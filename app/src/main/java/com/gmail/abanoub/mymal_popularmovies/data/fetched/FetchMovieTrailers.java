package com.gmail.abanoub.mymal_popularmovies.data.fetched;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class FetchMovieTrailers implements Parcelable {

    private int id;

    private List<MovieTrailer> results;

    public FetchMovieTrailers() {
    }

    protected FetchMovieTrailers(Parcel in) {
        id = in.readInt();
        results = in.createTypedArrayList(MovieTrailer.CREATOR);
    }

    public static final Creator<FetchMovieTrailers> CREATOR = new Creator<FetchMovieTrailers>() {
        @Override
        public FetchMovieTrailers createFromParcel(Parcel in) {
            return new FetchMovieTrailers(in);
        }

        @Override
        public FetchMovieTrailers[] newArray(int size) {
            return new FetchMovieTrailers[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<MovieTrailer> getResults() {
        return results;
    }

    public void setResults(List<MovieTrailer> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeTypedList(results);
    }

    public static class MovieTrailer implements Parcelable {
        private String id;
        private String iso_639_1;
        private String iso_3166_1;
        private String key;
        private String name;
        private String site;
        private int size;
        private String type;

        public MovieTrailer() {
        }

        protected MovieTrailer(Parcel in) {
            id = in.readString();
            iso_639_1 = in.readString();
            iso_3166_1 = in.readString();
            key = in.readString();
            name = in.readString();
            site = in.readString();
            size = in.readInt();
            type = in.readString();
        }

        public static final Creator<MovieTrailer> CREATOR = new Creator<MovieTrailer>() {
            @Override
            public MovieTrailer createFromParcel(Parcel in) {
                return new MovieTrailer(in);
            }

            @Override
            public MovieTrailer[] newArray(int size) {
                return new MovieTrailer[size];
            }
        };

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIso_639_1() {
            return iso_639_1;
        }

        public void setIso_639_1(String iso_639_1) {
            this.iso_639_1 = iso_639_1;
        }

        public String getIso_3166_1() {
            return iso_3166_1;
        }

        public void setIso_3166_1(String iso_3166_1) {
            this.iso_3166_1 = iso_3166_1;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSite() {
            return site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(id);
            parcel.writeString(iso_639_1);
            parcel.writeString(iso_3166_1);
            parcel.writeString(key);
            parcel.writeString(name);
            parcel.writeString(site);
            parcel.writeInt(size);
            parcel.writeString(type);
        }
    }
}
