package com.android.afif.p01_popularmovie.utils;

/**
 * Created by NUSNAFIF on 12/28/2016.
 */

public class Constant {
    /* use private Constructor to prevents instantiation of class */
    private Constant() {
    }

    /*
    * Base Url & Api Key
    */

    public static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    public static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w780";
    public static final String BASE_URL_API_KEY = "?api_key=";
//    public static final String API_KEY = "";  //Put your API key here
    public static final String API_KEY = "d769708ddf16f0f58f37bcdfb113a730";  //Put your API key here
    public static final String MOVIE_PREF_KEY_DEFAULT = "KEY";

    /**
     * Menu
     */
    public static final String POPULAR = "popular";
    public static final String TOP_RATED = "top_rated";
    public static final String SORTBY_DEFAULT_PARAM = "popular";
}
