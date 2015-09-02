package com.alexlowe.MoviesBoxoffice;

/**
 * Created by tsharma3 on 9/1/2015.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.alexlowe.MoviesBoxoffice.DbHelper.SQLiteHandler;
import com.alexlowe.MoviesBoxoffice.adaptor.ResultAdaptor;

import java.util.ArrayList;
import java.util.HashMap;

public class FavouriteMovies extends Fragment {
    public static final String MOVIE_DETAIL_KEY = "movie";
    ArrayList<BoxOfficeMovie> aMovies;
    private ListView favMovies;
    private ResultAdaptor resultAdaptor;
    private ProgressBar progress;
    private SQLiteHandler db;

    public FavouriteMovies() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_favouritemovies, container, false);
        db = new SQLiteHandler(getActivity());
        aMovies = new ArrayList<BoxOfficeMovie>();
        progress = (ProgressBar) rootView.findViewById(R.id.progress);

        resultAdaptor = new ResultAdaptor(getActivity(), aMovies);
        favMovies = (ListView) rootView.findViewById(R.id.favMovies);
        progress.setVisibility(ProgressBar.VISIBLE);
        LoadFavMovies();
        // setupMovieSelectedListener();
        return rootView;


    }

    private void LoadFavMovies() {
        // favMovies.setAdapter(adapterMovies);
        ArrayList<HashMap<String, String>> savedMovieData = db.getFavMovieDetails();

        if (savedMovieData != null && savedMovieData.size() > 0) {

            for (HashMap<String, String> map : savedMovieData) {
                String tagTitle = map.get("name");
                String tagScore = map.get("score");
                String tagImage = map.get("image");


                BoxOfficeMovie movies = new BoxOfficeMovie();
                movies.setTitle(tagTitle);
                if (tagScore != null) {
                    movies.setCriticsScore(tagScore);
                }
                movies.setLargePosterUrl(tagImage);
                aMovies.add(movies);
                //  favMovies.setAdapter(resultAdaptor);


            }
        }
        progress.setVisibility(ProgressBar.GONE);
        favMovies.setAdapter(resultAdaptor);

    }


}
