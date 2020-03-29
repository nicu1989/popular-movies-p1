package com.nicu1989.popularmoviespart1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private int mNumberItems;
    private boolean isPostersListInit = false;
    private List<String> mMoviePostersList;
    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }


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

    public MovieAdapter(int numberOfItems, ListItemClickListener listener) {
        mOnClickListener = listener;
        mNumberItems = numberOfItems;
    }

    public void setMovieLists(List<String> postersList){
        mMoviePostersList = postersList;
        isPostersListInit = true;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView listItemImageView;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            listItemImageView = itemView.findViewById(R.id.iv_poster_item);
            listItemImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }

        void bind(int listIndex) {
            //Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
            if (isPostersListInit) {
                String posterPath = "https://image.tmdb.org/t/p/w185/" + mMoviePostersList.get(listIndex);
                Picasso.get().load(posterPath).into(listItemImageView);
            }
            else{
                Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(listItemImageView);
            }
        }
    }
}
