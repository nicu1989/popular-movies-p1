package com.nicu1989.popularmoviespart1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private boolean isPopularSort = true;

    private MovieAdapter mAdapter;
    private RecyclerView mMoviesList;

    final static String TMDB_BASE =
            "http://api.themoviedb.org/3/movie/";

    final static String SORT_RATING =
            "top_rated";

    final static String SORT_POPULAR =
            "popular";

    //TODO remove API key
    final static String YOUR_API_KEY = "123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpLayout();

        doPopularSort();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.sort_by) {
            if(isPopularSort)
            {
                item.setTitle(R.string.sort_rating);
                isPopularSort = false;
                doRatingSort();
            }
            else{
                item.setTitle(R.string.sort_popular);
                isPopularSort = true;
                doPopularSort();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doPopularSort(){
        URL requestURL = buildQuery(SORT_POPULAR);
    }

    private void doRatingSort(){
        URL requestURL = buildQuery(SORT_RATING);
    }

    private URL buildQuery(String sortType){
        Uri builtUri = Uri.parse(TMDB_BASE).buildUpon()
                .appendPath(sortType)
                .appendQueryParameter("api_key", YOUR_API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    private void setUpLayout(){
        mMoviesList = findViewById(R.id.rv_posters);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mMoviesList.setLayoutManager(layoutManager);
        mMoviesList.setHasFixedSize(true);

        mAdapter = new MovieAdapter(30);

        mMoviesList.setAdapter(mAdapter);
    }
}
