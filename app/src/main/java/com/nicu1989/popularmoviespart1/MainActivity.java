package com.nicu1989.popularmoviespart1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener{

    private boolean isPopularSort = true;
    private boolean isLayoutInitialized = false;

    private MovieAdapter mAdapter;
    private RecyclerView mMoviesList;
    private JSONObject mResponseJSON = null;
    List<String> mPostersList = new ArrayList<>();
    List<String> mTitleList = new ArrayList<>();
    List<String> mPlotList = new ArrayList<>();
    List<String> mRatingList = new ArrayList<>();
    List<String> mDateList = new ArrayList<>();

    final static String TMDB_BASE =
            "http://api.themoviedb.org/3/movie/";

    final static String SORT_RATING =
            "top_rated";

    final static String SORT_POPULAR =
            "popular";

    //TODO remove API key
    final static String YOUR_API_KEY = "xxx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        doPopularSortAsyncRequest();
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
                doRatingSortAsyncRequest();
            }
            else{
                item.setTitle(R.string.sort_popular);
                isPopularSort = true;
                doPopularSortAsyncRequest();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doPopularSortAsyncRequest(){
        isLayoutInitialized = false;
        mAdapter = null;
        URL requestURL = buildQuery(SORT_POPULAR);
        new RequestAsyncDataTask().execute(requestURL);
    }

    private void doRatingSortAsyncRequest(){
        isLayoutInitialized = false;
        mAdapter = null;
        URL requestURL = buildQuery(SORT_RATING);
        new RequestAsyncDataTask().execute(requestURL);
    }

    private static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
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


    private void setUpLayout(int layoutSize){
        mMoviesList = findViewById(R.id.rv_posters);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mMoviesList.setLayoutManager(layoutManager);
        mMoviesList.setHasFixedSize(true);

        mAdapter = new MovieAdapter(layoutSize, this);

        mMoviesList.setAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        String movieTitle = mTitleList.get(clickedItemIndex);
        String moviePoster = mPostersList.get(clickedItemIndex);
        String moviePlot = mPlotList.get(clickedItemIndex);
        String movieRating = mRatingList.get(clickedItemIndex);
        String movieDate = mDateList.get(clickedItemIndex);

        Intent intent = new Intent(this, ChildActivity.class);
        intent.putExtra("movie", movieTitle);
        intent.putExtra("poster", moviePoster);
        intent.putExtra("plot", moviePlot);
        intent.putExtra("rating", movieRating);
        intent.putExtra("date", movieDate);
        startActivity(intent);
    }

    public class RequestAsyncDataTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... params) {
            URL requestUrl = params[0];
            String responseResults = null;
            try {
                responseResults = getResponseFromHttpUrl(requestUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return responseResults;
        }

        @Override
        protected void onPostExecute(String responseResult) {

            if (responseResult != null && !responseResult.equals("")) {

                try {
                    mResponseJSON = new JSONObject(responseResult);
                    processJSONToLayout();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // mSearchResultsTextView.setText(responseResult);
            }
        }
    }

    private void processJSONToLayout(){
        int numberOfLayoutItems = 0;

        try {
            JSONArray results = mResponseJSON.getJSONArray("results");

            numberOfLayoutItems = 20;//(int)numberOfMovies/numberOfPages;
            for (int i = 0; i < (numberOfLayoutItems); i++){
                JSONObject movieObject = results.getJSONObject(i);
                String moviePoster = movieObject.getString("poster_path");
                String originalTitle = movieObject.getString("original_title");
                String overview = movieObject.getString("overview");
                String rating = movieObject.getString("vote_average");
                String releaseDate = movieObject.getString("release_date");
                mPostersList.add(moviePoster);
                mTitleList.add(originalTitle);
                mPlotList.add(overview);
                mRatingList.add(rating);
                mDateList.add(releaseDate);
            }

            if (!isLayoutInitialized)
            {
                setUpLayout(numberOfLayoutItems);
                isLayoutInitialized = true;
            }
            mAdapter.setMovieLists(mPostersList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
