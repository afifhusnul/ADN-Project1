package com.android.afif.p01_popularmovie.model;

/**
 * Created by NUSNAFIF on 12/28/2016.
 */

public class Movie {

    private String id;
    private String posterPath;
    private String backdropPath;
    private String overview;
    private String title;
    private String rating;
    private String date;

    public Movie(String id,String overview, String posterPath, String backdropPath, String title, String rating, String date) {
        this.id = id;
        this.overview = overview;
        this.posterPath = posterPath;
        this.title = title;
        this.rating = rating;
        this.date = date;
    }

    public String getId() {return id;}

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public String getRating() {
        return rating;
    }

    public String getDate() {
        return date;
    }
}
