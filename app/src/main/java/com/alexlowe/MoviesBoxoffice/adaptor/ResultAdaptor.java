package com.alexlowe.MoviesBoxoffice.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexlowe.MoviesBoxoffice.BoxOfficeMovie;
import com.alexlowe.MoviesBoxoffice.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by tsharma3 on 9/2/2015.
 */
public class ResultAdaptor extends ArrayAdapter<BoxOfficeMovie> {
    public ResultAdaptor(Context context, ArrayList<BoxOfficeMovie> aMovies) {
        super(context, 0, aMovies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        BoxOfficeMovie movie = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_saved_data_item, null);
        }
        // Lookup view for data population
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvCriticsScore = (TextView) convertView.findViewById(R.id.tvCriticScore);
        ImageView ivPosterImage = (ImageView) convertView.findViewById(R.id.ivPosterImage);
        // Populate the data into the template view using the data object
        tvTitle.setText(movie.getTitle());
        tvCriticsScore.setText("Score: " + movie.getCriticsScore() + "%");

        Picasso.with(getContext()).load(movie.getLargePosterUrl()).into(ivPosterImage);
        // Return the completed view to render on screen
        return convertView;
    }
}


