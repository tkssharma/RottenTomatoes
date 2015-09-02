package com.alexlowe.MoviesBoxoffice;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class RtClient {

    private static final String TAG = RtClient.class.getSimpleName();

    private final String API_KEY = "APIKEY";
    private final String API_BASE_URL = "http://api.rottentomatoes.com/api/public/v1.0/";
    private AsyncHttpClient client;

    public RtClient() {
        this.client = new AsyncHttpClient();
    }

    // http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?apikey=<key>
    public void getBoxOfficeMovies(String fetchurl ,String query ,JsonHttpResponseHandler handler) {
        String url = getApiUrl(fetchurl);
        Log.d(TAG, url);
        RequestParams params = new RequestParams("apikey", API_KEY);

        if(query != null && query.length() > 0 ){
            params.add("page_limit","1");
            params.add("q",query);
        }

        client.get(url, params, handler);
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }
}
