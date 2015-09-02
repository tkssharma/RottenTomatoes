package com.alexlowe.MoviesBoxoffice.DbHelper;

/**
 * Created by tsharma3 on 8/13/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 6;

    // Database Name
    private static final String DATABASE_NAME = "Movies.db";

    // Login table name
    private static final String TABLE_SAVED_MOVIES = "saved_movies_1";
    private static final String TABLE_FAV_MOVIES = "fav_movies_1";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_MOVIE_SAVED = "name";
    private static final String SCORE = "score";
    private static final String IMAGE = "image";


    private static final String KEY_MOVIE_FAV = "name";
    private static final String KEY_CREATED_AT = "created_at";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SAVED_MOVIES_TABLE = "CREATE TABLE  IF NOT EXISTS " + TABLE_SAVED_MOVIES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MOVIE_SAVED + " TEXT," + SCORE + " TEXT ," + IMAGE + " TEXT " + ")";
        db.execSQL(CREATE_SAVED_MOVIES_TABLE);

        String CREATE_FAV_MOVIES_TABLE = "CREATE  TABLE IF NOT EXISTS " + TABLE_FAV_MOVIES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MOVIE_FAV + " TEXT," + SCORE + " TEXT ," + IMAGE + " TEXT " + ")";
        db.execSQL(CREATE_FAV_MOVIES_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVED_MOVIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAV_MOVIES);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     */
    public void addMovie_saved(String name, String score, String url) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MOVIE_SAVED, name); // Name
        values.put(SCORE, score);
        values.put(IMAGE, url);
        // Inserting Row
        long id = db.insert(TABLE_SAVED_MOVIES, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    public void addMovie_fav(String name, String score, String url) throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MOVIE_FAV, name); // Name
        values.put(SCORE, score);
        values.put(IMAGE, url);
        // Inserting Row
        long id = db.insert(TABLE_FAV_MOVIES, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     */
    public ArrayList<HashMap<String, String>> getSavedMovieDetails() {

        String selectQuery = "SELECT  * FROM " + TABLE_SAVED_MOVIES;

        ArrayList<HashMap<String, String>> savedMovie = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", cursor.getString(0));
                map.put("name", cursor.getString(1));
                map.put("score", cursor.getString(2));
                map.put("image", cursor.getString(3));
                savedMovie.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user
        Log.i(TAG, "Fetching user from Sqlite: " + savedMovie.toString());

        return savedMovie;
    }

    public ArrayList<HashMap<String, String>> getFavMovieDetails() {


        String selectQuery = "SELECT  * FROM " + TABLE_FAV_MOVIES;

        ArrayList<HashMap<String, String>> favMovie = new ArrayList<HashMap<String, String>>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", cursor.getString(0));
                map.put("name", cursor.getString(1));
                map.put("score", cursor.getString(2));
                map.put("image", cursor.getString(3));
                favMovie.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + favMovie.toString());

        return favMovie;
    }

    /**
     * Getting user login status return true if rows are there in table
     */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SAVED_MOVIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    /**
     * Re crate database Delete all tables and create them again
     */
    public void deleteSavedMovies() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_SAVED_MOVIES, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

    public void deleteFavMovies() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_FAV_MOVIES, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}
