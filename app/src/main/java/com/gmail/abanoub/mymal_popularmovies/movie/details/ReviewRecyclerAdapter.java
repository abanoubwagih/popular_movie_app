package com.gmail.abanoub.mymal_popularmovies.movie.details;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmail.abanoub.mymal_popularmovies.R;
import com.gmail.abanoub.mymal_popularmovies.data.fetched.FetchMovieReviews;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewRecyclerAdapter.ViewHolder> {

    private List<FetchMovieReviews.MovieReviews> movieReviewses;
    private Context context;

    public ReviewRecyclerAdapter(Context context, List<FetchMovieReviews.MovieReviews> objects) {

        this.context = context;
        this.movieReviewses = objects;

    }

    public String getItemIdString(int position) {

        return movieReviewses.get(position).getId();

    }

    public void addAndClear(List<FetchMovieReviews.MovieReviews> objects) {

        movieReviewses.clear();
        movieReviewses.addAll(objects);
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_review, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.review_author.setText(movieReviewses.get(position).getAuthor());
        holder.review_content.setText(movieReviewses.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return movieReviewses.size();
    }


    public void onItemClick() {


    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.review_author)
        TextView review_author;
        @BindView(R.id.review_content)
        TextView review_content;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                    intent.setData(Uri.parse(movieReviewses.get(getAdapterPosition()).getUrl()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
