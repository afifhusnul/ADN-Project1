package com.android.afif.p01_popularmovie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.android.afif.p01_popularmovie.R;
import com.android.afif.p01_popularmovie.model.Movie;
import com.android.afif.p01_popularmovie.utils.Constant;
import com.android.afif.p01_popularmovie.utils.PosterImageView;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by NUSNAFIF on 12/28/2016.
 */

public class MovieAdapter extends ArrayAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList objects;

    public MovieAdapter(Context context, ArrayList objects) {
        super(context, R.layout.movie_list_item,objects);
        this.context = context;
        this.objects = objects;
        inflater = LayoutInflater.from(context);
//        Glide.with(context).setIndicatorsEnabled(false);
    }


    @Override
    public View getView(int position, View recycled, ViewGroup parent) {
        View listItemView = recycled;
        if (listItemView == null) {
            listItemView = inflater.inflate(R.layout.movie_list_item, parent, false);
        }
        Movie currentMovie = (Movie) getItem(position);
        PosterImageView imageView = (PosterImageView) listItemView.findViewById(R.id.image);
        String pathToImage = Constant.BASE_IMAGE_URL + currentMovie.getPosterPath();
        Glide.with(context).load(pathToImage).centerCrop().into(imageView);
        return listItemView;
    }


}
