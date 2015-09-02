package com.alexlowe.MoviesBoxoffice;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alexlowe.MoviesBoxoffice.DbHelper.SQLiteHandler;
import com.alexlowe.MoviesBoxoffice.R;
import com.squareup.picasso.Picasso;

import java.sql.SQLDataException;
import java.sql.SQLException;

public class DetailActivity extends AppCompatActivity {
    private ImageView ivPosterImage;
    private TextView tvTitle;
    private TextView tvSynopsis;
    private TextView tvCast;
    private TextView tvAudienceScore;
    private TextView tvCriticsScore;
    private TextView tvCriticsConsensus;
    private Toolbar toolbar;
    private Context mContext;
    BoxOfficeMovie movie;
    private ImageView likeView;
    private SQLiteHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // Fetch views
        mContext = this;
        db = new SQLiteHandler(mContext);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);


        ivPosterImage = (ImageView) findViewById(R.id.ivPosterImage);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvSynopsis = (TextView) findViewById(R.id.tvSynopsis);
        tvCast = (TextView) findViewById(R.id.tvCast);
        tvCriticsConsensus = (TextView) findViewById(R.id.tvCriticsConsensus);
        tvAudienceScore =  (TextView) findViewById(R.id.tvAudienceScore);
        tvCriticsScore = (TextView) findViewById(R.id.tvCriticsScore);
        likeView = (ImageView)findViewById(R.id.likebutton);
        // Load movie data

        movie = (BoxOfficeMovie) getIntent().getSerializableExtra(BoxOfficeActivity.MOVIE_DETAIL_KEY);
        loadMovie(movie);
        likeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeView.setImageResource(R.drawable.ic_heart_red);
                try {
                    db.addMovie_fav(movie.getTitle(),movie.getCriticsScore()+"",movie.getLargePosterUrl());
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });
    }

    // Populate the data for the movie
    @SuppressLint("NewApi")
    public void loadMovie(BoxOfficeMovie movie) {
       
        // Populate data
        tvTitle.setText(movie.getTitle());
        tvCriticsScore.setText(Html.fromHtml("<b>Critics Score:</b> " + movie.getCriticsScore() + "%"));
        tvAudienceScore.setText(Html.fromHtml("<b>Audience Score:</b> " + movie.getAudienceScore() + "%"));
        tvCast.setText(movie.getCastList());
        tvSynopsis.setText(Html.fromHtml("<b>Synopsis:</b> " + movie.getSynopsis()));
        tvCriticsConsensus.setText(Html.fromHtml("<b>Consensus:</b> " + movie.getCriticsConsensus()));
        // R.drawable.large_movie_poster from
        // http://content8.flixster.com/movie/11/15/86/11158674_pro.jpg -->
        Picasso.with(this).load(movie.getLargePosterUrl()).
                placeholder(R.drawable.large_poster).
                into(ivPosterImage);

        saveSearchResults(movie.getTitle(),movie.getCriticsScore()+"",movie.getLargePosterUrl());
        
    }

    private void saveSearchResults(String title , String score , String url) {
        try {
            db.addMovie_saved(title.toString(),score.toString(),url.toString());
        }
        catch (SQLException EX)
        {
            EX.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_addtoFav) {
             addtoFavorite(movie);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void addtoFavorite(BoxOfficeMovie movie) {
        likeView.setImageResource(R.drawable.ic_heart_red);
        try {
            db.addMovie_fav(movie.getTitle(),movie.getCriticsScore()+"",movie.getLargePosterUrl());
            Toast.makeText(this,"Added to favorite",Toast.LENGTH_SHORT).show();
        }
        catch (SQLException exx)
        {
            exx.printStackTrace();
        }
        finally {

        }

    }

}
