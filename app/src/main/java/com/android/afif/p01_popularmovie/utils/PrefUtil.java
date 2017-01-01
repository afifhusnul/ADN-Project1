package com.android.afif.p01_popularmovie.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by NUSNAFIF on 12/28/2016.
 */

public class PrefUtil {
    private final SharedPreferences preferences;

    public PrefUtil(Context context, String namePreferences) {
        preferences = context.getSharedPreferences(namePreferences, MODE_PRIVATE);
    }


    public void putString(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    public String getString(String key) {
        return ((key == null) || (key.isEmpty())) ? Constant.MOVIE_PREF_KEY_DEFAULT : preferences.getString(key, "");
    }
}
