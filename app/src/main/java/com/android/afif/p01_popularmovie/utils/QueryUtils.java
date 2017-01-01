package com.android.afif.p01_popularmovie.utils;

import android.text.TextUtils;
import android.util.Log;

import com.android.afif.p01_popularmovie.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by NUSNAFIF on 12/29/2016.
 * Helper methods related to requesting and receiving earthquake data from TheMovieDB
 */

public class QueryUtils {
    /* Setup Log*/
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static ArrayList<Movie> fetchMovieData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            Log.v(LOG_TAG, "makeHttpRequest");
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.v(LOG_TAG, "Problem making the HTTP request.", e);
        }
        ArrayList<Movie> movieArrayList = extractMovieFeaturesFromJson(jsonResponse);
        return movieArrayList;
    }


    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.v(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }


    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static ArrayList<Movie> extractMovieFeaturesFromJson(String weatherJSON) {
        if (TextUtils.isEmpty(weatherJSON)) {
            return null;
        }
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(weatherJSON);
            JSONArray movieResultsArray = baseJsonResponse.getJSONArray("results");
            for (int i = 0; i < movieResultsArray.length(); i++) {
                JSONObject currentMovie = movieResultsArray.getJSONObject(i);
                String id = currentMovie.getString("id");
                String posterPath = currentMovie.getString("poster_path");
                String backdropPath = currentMovie.getString("backdrop_path");
                String overview = currentMovie.getString("overview");
                String date = currentMovie.getString("release_date");
                String rating = currentMovie.getString("vote_average");
                String title = currentMovie.getString("title");
                movieArrayList.add(new Movie(id, overview, posterPath, backdropPath, title, rating, date));
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the JSON results", e);
        }
        return movieArrayList;
    }
    
}
