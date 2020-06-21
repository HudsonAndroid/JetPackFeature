package com.hudson.jetpackfeature.databinding.entity;

import android.util.Log;

import com.hudson.jetpackfeature.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * Created by Hudson on 2020/6/21.
 */
public class TwoWayData extends BaseObservable {
    private static final String TAG = "TwoWayData";
    private String mContent;

    @Bindable
    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
        Log.e(TAG,"content change: " +content);
        notifyPropertyChanged(BR.content);
    }
}
