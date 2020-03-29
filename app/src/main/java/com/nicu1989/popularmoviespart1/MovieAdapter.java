package com.nicu1989.popularmoviespart1;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private int mNumberItems;

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        MovieViewHolder viewHolder = new MovieViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Log.d("BIND", "#" + position);
        holder.bind(position);
    }


    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    public MovieAdapter(int numberOfItems) {
        mNumberItems = numberOfItems;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView listItemImageView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            listItemImageView = itemView.findViewById(R.id.iv_poster_item);
        }

        void bind(int listIndex) {
            //Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
            Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(listItemImageView);

            //listItemImageView.setImageResource(R.drawable.x_test);
        }
    }
}
