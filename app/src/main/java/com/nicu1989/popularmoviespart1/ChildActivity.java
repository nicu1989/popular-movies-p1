package com.nicu1989.popularmoviespart1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ChildActivity extends AppCompatActivity {
    private ImageView mIV_poster;
    private TextView mTV_title;
    private TextView mTV_plot;
    private TextView mTV_rating;
    private TextView mTV_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        mIV_poster = findViewById(R.id.iv_poster);
        mTV_title = findViewById(R.id.tv_title);
        mTV_plot = findViewById(R.id.tv_plot);
        mTV_rating = findViewById(R.id.tv_rating);
        mTV_date = findViewById(R.id.tv_date);

        Intent intent = getIntent();

        // COMPLETED (2) Display the weather forecast that was passed from MainActivity
        if (intent != null) {
            if (intent.hasExtra("movie")) {
                String intentText = intent.getStringExtra("movie");
                mTV_title.setText(intentText);
            }

            if (intent.hasExtra("poster")) {
                String intentText = intent.getStringExtra("poster");
                String posterPath = "https://image.tmdb.org/t/p/w185/" + intentText;
                Picasso.get().load(posterPath).into(mIV_poster);
            }

            if (intent.hasExtra("plot")) {
                String intentText = intent.getStringExtra("plot");
                mTV_plot.setText(intentText);
            }

            if (intent.hasExtra("rating")) {
                String intentText = intent.getStringExtra("rating");
                mTV_rating.setText(intentText);
            }

            if (intent.hasExtra("date")) {
                String intentText = intent.getStringExtra("date");
                mTV_date.setText(intentText);
            }
        }
    }
}
