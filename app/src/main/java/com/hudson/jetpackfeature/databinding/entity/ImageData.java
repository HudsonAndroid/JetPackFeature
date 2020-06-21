package com.hudson.jetpackfeature.databinding.entity;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import com.hudson.jetpackfeature.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * Created by Hudson on 2020/6/21.
 */
public class ImageData extends BaseObservable {
    private static final String TAG = "ImageData";
    private String mImageUrl;
    private Drawable mErrorDrawable;

    @Bindable
    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
        notifyPropertyChanged(BR.imageUrl);
    }

    @Bindable
    public Drawable getErrorDrawable() {
        return mErrorDrawable;
    }

    public void setErrorDrawable(Drawable errorDrawable) {
        mErrorDrawable = errorDrawable;
        notifyPropertyChanged(BR.errorDrawable);
    }

    public void onLayoutChange(View v, int left, int top, int right, int bottom,
                               int oldLeft, int oldTop, int oldRight, int oldBottom){
        Log.e(TAG,"layout changed : "+ left + "," + top+","+right+","+bottom);
    }
}
