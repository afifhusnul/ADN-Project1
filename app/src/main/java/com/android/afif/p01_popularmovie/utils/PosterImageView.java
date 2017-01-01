package com.android.afif.p01_popularmovie.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by NUSNAFIF on 12/29/2016.
 */

/*
Code for this view class was taken from the following StackOverflow QnA:
http://stackoverflow.com/questions/16506275/imageview-be-a-square-with-dynamic-width
Answer provided by user a.bertucci http://stackoverflow.com/users/302645/a-bertucciq
*/


public class PosterImageView extends ImageView {
    public PosterImageView(Context context) {
        super(context);
    }

    public PosterImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PosterImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int newHeight = (int) Math.round(getMeasuredWidth() * 1.5);
        setMeasuredDimension(getMeasuredWidth(),newHeight);
    }

}