package com.alexlowe.MoviesBoxoffice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alexlowe.MoviesBoxoffice.Util.Const;
import com.alexlowe.MoviesBoxoffice.adaptor.MoviesAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Latestfeeds extends Fragment {

    public static final String MOVIE_DETAIL_KEY = "movie";
    private static final String TAG = BoxOfficeActivity.class.getSimpleName();
    ArrayList<BoxOfficeMovie> aMovies;
    private ListView lvMovies;
    private MoviesAdapter adapterMovies;
    private RtClient client;

    public Latestfeeds() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_latestfeeds, container, false);
        aMovies = new ArrayList<BoxOfficeMovie>();
        adapterMovies = new MoviesAdapter(getActivity(), aMovies);
        lvMovies = (ListView) rootView.findViewById(R.id.lvMovies);
        setupMovieSelectedListener();
        fetchBoxOfficeMovies(Const.FETCH_MOVIES, "");

        return rootView;
    }

    public void setupMovieSelectedListener() {
        lvMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position, long rowId) {
                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra(MOVIE_DETAIL_KEY, adapterMovies.getItem(position));
                startActivity(i);
            }
        });
    }

    private void fetchBoxOfficeMovies(String url, String query) {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Loading...");
        dialog.setMessage("Please wait.");
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        client = new RtClient();
        client.getBoxOfficeMovies(url, query, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int code, Header[] headers, JSONObject body) {
                JSONArray items = null;
                try {
                    Log.d("", body.toString());
                    // Get the movies json array
                    items = body.getJSONArray("movies");
                    // Parse json array into array of model objects
                    ArrayList<BoxOfficeMovie> movies = BoxOfficeMovie.fromJson(items);
                    // Load model objects into the adapter which displays them
                    if (adapterMovies != null) {
                        adapterMovies.clear();
                    }
                    adapterMovies.addAll(movies);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                lvMovies.setAdapter(adapterMovies);
                if (dialog != null) {
                    dialog.dismiss();
                }
            }

        });


    }


}
