# App based on REST API with custom Adaptor and drawer layouts 

The navigation drawer is a panel that displays the app’s main navigation options on the left edge of the screen. It is hidden most of the time, but is revealed when the user swipes a finger from the left edge of the screen or, while at the top level of the app, the user touches the app icon in the action bar.

# Android Studio App 

> Material Design Specifications[Navigation Drawer](http://blog.teamtreehouse.com/add-navigation-drawer-android) 
> Creating Apps with Material Design[Material Design](http://developer.android.com/training/material/index.html) 

## Running Locally
Make sure you have [Android studio/Eclipse ADB](http://developer.android.com/tools/studio/index.html) 

```sh
    compile 'com.android.support:appcompat-v7:22.2.1'
    compile 'com.squareup.picasso:picasso:2.5.2'
    compile 'com.loopj.android:android-async-http:1.4.6'
    compile 'com.jakewharton:butterknife:7.0.1'
    compile 'com.android.support:design:22.2.0'
    compile 'com.android.support:recyclerview-v7:22.2.+'
```

## Create a Drawer Layout and add fragment calls 


```sh
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
```