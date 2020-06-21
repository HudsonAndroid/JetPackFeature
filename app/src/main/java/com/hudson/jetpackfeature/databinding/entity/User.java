package com.hudson.jetpackfeature.databinding.entity;

import android.util.Log;
import android.view.View;

import com.hudson.jetpackfeature.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

/**
 * Created by Hudson on 2020/6/21.
 */
public class User extends BaseObservable {
    private static final String TAG = "user";
    private String mName;
    private int mAge;

    @Bindable
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
        notifyPropertyChanged(BR.age);
    }

    public void onClickThisData(View view){
        Log.e(TAG, "you click the item  " + mName + ", "+ mAge);
    }
}
