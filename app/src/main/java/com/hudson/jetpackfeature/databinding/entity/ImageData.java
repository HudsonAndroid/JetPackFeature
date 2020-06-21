package com.hudson.jetpackfeature.databinding.entity;

import android.graphics.drawable.Drawable;

import com.hudson.jetpackfeature.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * Created by Hudson on 2020/6/21.
 */
public class ImageData extends BaseObservable {
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
}
