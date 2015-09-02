package com.alexlowe.MoviesBoxoffice;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.alexlowe.MoviesBoxoffice.DbHelper.SQLiteHandler;
import com.alexlowe.MoviesBoxoffice.adaptor.ResultAdaptor;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListsavedMovies extends Fragment {


    public static final String MOVIE_DETAIL_KEY = "movie";
    ArrayList<BoxOfficeMovie> aMovies;
    private ListView savedMovies;
    private ResultAdaptor resultAdaptor;
    private ProgressBar progress;
    private SQLiteHandler db;

    public ListsavedMovies() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_listsaved_movies, container, false);
        progress = (ProgressBar) rootView.findViewById(R.id.progress);

        db = new SQLiteHandler(getActivity());
        aMovies = new ArrayList<BoxOfficeMovie>();
        progress = (ProgressBar) rootView.findViewById(R.id.progress);

        resultAdaptor = new ResultAdaptor(getActivity(), aMovies);
        savedMovies = (ListView) rootView.findViewById(R.id.savedMovies);
        progress.setVisibility(ProgressBar.VISIBLE);
        LoadsavedMovies();
        return rootView;
    }

    private void LoadsavedMovies() {
        // favMovies.setAdapter(adapterMovies);
        ArrayList<HashMap<String, String>> savedMovieData = db.getSavedMovieDetails();

        if (savedMovieData != null && savedMovieData.size() > 0) {

            for (HashMap<String, String> map : savedMovieData) {
                String tagTitle = map.get("name");
                String tagScore = map.get("score");
                String tagImage = map.get("image");
                Log.i("LOLLLLLLLLLLLLLLLLL", "" + tagTitle + tagImage + tagScore);
                BoxOfficeMovie movies = new BoxOfficeMovie();
                movies.setTitle(tagTitle);
                if (tagScore != null) {
                    movies.setCriticsScore((tagScore));
                }

                movies.setLargePosterUrl(tagImage);
                aMovies.add(movies);
                //  favMovies.setAdapter(resultAdaptor);


            }
        }
        progress.setVisibility(ProgressBar.GONE);
        savedMovies.setAdapter(resultAdaptor);

    }


}
