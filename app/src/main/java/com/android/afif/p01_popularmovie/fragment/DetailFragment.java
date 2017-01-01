package com.android.afif.p01_popularmovie.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.afif.p01_popularmovie.R;
import com.android.afif.p01_popularmovie.utils.Constant;
import com.bumptech.glide.Glide;

/**
 * Created by NUSNAFIF on 12/28/2016.
 */

public class DetailFragment extends Fragment {

    private final String LOG_TAG = DetailFragment.class.getSimpleName();
    private Context context;
    private String data;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        Bundle bundle = getActivity().getIntent().getExtras();
        String posterPath = bundle.getString("posterPath");
        String backdropPath = bundle.getString("backdropPath");
        String date = bundle.getString("date");
        String title = bundle.getString("title");
        String overview = bundle.getString("overview");
        String rating = bundle.getString("rating");
        String id = bundle.getString("id");

        ImageView imgPoster = (ImageView) rootView.findViewById(R.id.detail_poster);
        TextView movieTitle = (TextView) rootView.findViewById(R.id.detail_title);
        TextView movieSummary = (TextView) rootView.findViewById(R.id.detail_overview);
        TextView movieRating = (TextView) rootView.findViewById(R.id.detail_rating);
        TextView movieReldate = (TextView) rootView.findViewById(R.id.detail_release_date);
        Glide.with(this).load(Constant.BASE_IMAGE_URL + posterPath).fitCenter().into(imgPoster);


        movieTitle.setText(title);
        movieRating.setText("Movie Rating " + rating);
        movieSummary.setText(overview);
        movieReldate.setText("Release : " + date);
        // Change ActionBar with Movie title
        getActivity().setTitle(title);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
