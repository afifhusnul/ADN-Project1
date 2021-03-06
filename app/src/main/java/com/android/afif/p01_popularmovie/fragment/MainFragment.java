package com.android.afif.p01_popularmovie.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.afif.p01_popularmovie.DetailActivity;
import com.android.afif.p01_popularmovie.R;
import com.android.afif.p01_popularmovie.adapter.MovieAdapter;
import com.android.afif.p01_popularmovie.model.Movie;
import com.android.afif.p01_popularmovie.utils.Constant;
import com.android.afif.p01_popularmovie.utils.PrefUtil;
import com.android.afif.p01_popularmovie.utils.QueryUtils;

import java.util.ArrayList;

/**
 * Created by NUSNAFIF on 12/28/2016.
 */

public class MainFragment extends Fragment {

    private static final String LOG_TAG = MainFragment.class.getSimpleName();


    @Nullable
    private SharedPreferences sharedPref;
    private String menuOrder;
    private MovieAdapter movieAdapter;
    private GridView gridView;
    private ArrayList<Movie> movies;
    private GridLayoutManager mgridLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // Initialize Order
        menuOrder = new PrefUtil(getActivity(), getString(R.string.pref_key_sort)).getString(getString(R.string.pref_key_sort));

        // Default order
        if ((menuOrder != null) && menuOrder.isEmpty()) {
            menuOrder = Constant.SORTBY_DEFAULT_PARAM;
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        movies = new ArrayList<>();
        movieAdapter = new MovieAdapter(getContext(), movies);

        gridView = (GridView) rootView.findViewById(R.id.movie_list);
        gridView.setAdapter(movieAdapter);


        // Handle ImageClick to get movie details
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Movie selectedMovie = (Movie) movieAdapter.getItem(position);

                Intent I = new Intent(getActivity(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", selectedMovie.getId());
                bundle.putString("date", selectedMovie.getDate());
                bundle.putString("title", selectedMovie.getTitle());
                bundle.putString("backdropPath", selectedMovie.getPosterPath());
                bundle.putString("overview", selectedMovie.getOverview());
                bundle.putString("rating", selectedMovie.getRating());
                bundle.putString("posterPath", selectedMovie.getPosterPath());
                I.putExtras(bundle);
                startActivity(I);
            }
        });
        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        new MovieAsyncTask().execute(Constant.BASE_URL + menuOrder + Constant.BASE_URL_API_KEY + Constant.API_KEY);
    }

/*
    @Override
    public void onResume() {
        super.onResume();
        new PrefUtil(getActivity(), getString(R.string.pref_key_sort)).putString(getString(R.string.pref_key_sort), menuOrder);
        Log.i(LOG_TAG, "On Resume: Isi Menu : " + menuOrder);
    }
*/

    /*@Override
    public void onStop() {
        super.onStop();
        // save SharedPreferences
        new PrefUtil(getActivity(), getString(R.string.pref_key_sort)).putString(getString(R.string.pref_key_sort), menuOrder);
    }*/

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        new PrefUtil(getActivity(), getString(R.string.pref_key_sort)).putString(getString(R.string.pref_key_sort), menuOrder);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);

        // Initialize menu value
        if ((menuOrder != null) && menuOrder.equalsIgnoreCase(String.valueOf(Constant.TOP_RATED))) {
            menu.findItem(R.id.menu_sort_top_rated).setChecked(true);
        } else {
            menu.findItem(R.id.menu_sort_popular).setChecked(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        /* Handle action bar item clicks here. The action bar will
         automatically handle clicks on the Home/Up button, so long
         as you specify a parent activity in AndroidManifest.xml
         */

        switch (item.getItemId()) {
            case R.id.menu_sort_popular:
                menuOrder = String.valueOf(Constant.POPULAR);
                // Load Data -- POPULAR
                new MovieAsyncTask().execute(Constant.BASE_URL + menuOrder + Constant.BASE_URL_API_KEY + Constant.API_KEY);
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;


            case R.id.menu_sort_top_rated:
                menuOrder = String.valueOf(Constant.TOP_RATED);
                // Load Data -- TOP RATED
                new MovieAsyncTask().execute(Constant.BASE_URL + menuOrder + Constant.BASE_URL_API_KEY + Constant.API_KEY);
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private class MovieAsyncTask extends AsyncTask<String, String, ArrayList<Movie>> {
        @Override
        protected ArrayList<Movie> doInBackground(String... String) {
            ArrayList list = QueryUtils.fetchMovieData(String[0]);
            return list;
        }


        @Override
        protected void onPostExecute(ArrayList<Movie> list) {
            super.onPostExecute(list);
            if (list != null) {
                movieAdapter = new MovieAdapter(getActivity(), list);
                gridView.setAdapter(movieAdapter);
            } else {
                Toast.makeText(getContext(), "Failed To Fetch Data, please check API KEY & your internet connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
