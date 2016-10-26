package com.gmail.abanoub.mymal_popularmovies;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.abanoub.mymal_popularmovies.data.fetched.FetchedMoviesList;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MoviesArrayAdapter extends ArrayAdapter<FetchedMoviesList.Movie> {

    private Context context;
    private List<FetchedMoviesList.Movie> movieList;

    public MoviesArrayAdapter(Context context,int resource, List<FetchedMoviesList.Movie> objects) {
        super(context, resource, objects);
        this.context = context;
        this.movieList = objects;
    }

    @Override
    public long getItemId(int position) {
        if (movieList == null) {
            return super.getItemId(position);

        } else {

            return movieList.get(position).getId();
        }
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FetchedMoviesList.Movie movie = movieList.get(position);
        ButterKnife.bind(this,convertView);
        ViewHolder holder;

        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {

            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_movie, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }

        holder.movieTitle.setText(movie.getTitle());

        String imagePath = getContext().getString(R.string.BASE_URL_FETCH_POSTER) + movie.getPoster_path();
        Picasso.with(context).load(imagePath).into(holder.moviePoster);

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.movie_textView)
        TextView movieTitle;
        @BindView(R.id.movie_imageView)
        ImageView moviePoster;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
