package com.gmail.abanoub.mymal_popularmovies.movie.details;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gmail.abanoub.mymal_popularmovies.R;
import com.gmail.abanoub.mymal_popularmovies.data.fetched.FetchMovieTrailers;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TrailerRecyclerAdapter extends RecyclerView.Adapter<TrailerRecyclerAdapter.ViewHolder> {

    private List<FetchMovieTrailers.MovieTrailer> movieTrailers;
    private Context context;

    public TrailerRecyclerAdapter(Context context, List<FetchMovieTrailers.MovieTrailer> movieTrailers) {
        this.movieTrailers = movieTrailers;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_trailer, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        String thumbnailPaths = String.format(context.getString(R.string.BASE_URL_thumbnail_TRAILER), movieTrailers.get(position).getKey());
        Picasso.with(context).load(thumbnailPaths).into(holder.trailer_thumbnail_image);

    }

    @Override
    public int getItemCount() {
        return movieTrailers.size();
    }

    public String getItemIdString(int position) {

        return movieTrailers.get(position).getId();

    }

    public void addAndClear(List<FetchMovieTrailers.MovieTrailer> objects) {

        if (movieTrailers != null) {

            movieTrailers.clear();
        } else {
            movieTrailers = new ArrayList<>();
        }
        movieTrailers.addAll(objects);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.trailer_thumbnail_image)
        ImageView trailer_thumbnail_image;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                    String trailerPath = context.getString(R.string.BASE_URL_WATCH_TRAILER) + movieTrailers.get(getAdapterPosition()).getKey();
                    intent.setData(Uri.parse(trailerPath));
                    context.startActivity(intent);

                }
            });
        }
    }
}
