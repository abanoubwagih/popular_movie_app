package com.gmail.abanoub.mymal_popularmovies;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.abanoub.mymal_popularmovies.data.provider.MoviesContract;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MovieCursorAdapter extends CursorAdapter {
    public MovieCursorAdapter(Context context) {
        super(context, null);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.list_item_movie, null);
        ViewHolder holder = new ViewHolder(root);
        root.setTag(holder);

        return root;
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        String title = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_TITLE));
        String imagePath = context.getString(R.string.BASE_URL_FETCH_POSTER)
                + cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH));
        holder.movieTitle.setText(title);
        Picasso.with(context).load(imagePath).placeholder(R.drawable.user_placeholder).error(R.drawable.user_placeholder_error).into(holder.moviePoster);
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
