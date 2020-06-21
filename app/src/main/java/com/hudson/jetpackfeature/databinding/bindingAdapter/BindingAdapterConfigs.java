package com.hudson.jetpackfeature.databinding.bindingAdapter;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import androidx.databinding.BindingAdapter;

/**
 * Created by Hudson on 2020/6/21.
 */
public class BindingAdapterConfigs {

    @BindingAdapter(value = {"imageUrl","errorDrawable"},requireAll = false)
    public static void loadImage(ImageView imageView, String url, Drawable error){
        Glide.with(imageView.getContext()).load(url).error(error).into(imageView);
    }

    @BindingAdapter("android:onLayoutChange")
    public static void setOnLayoutChangeListener(View view, View.OnLayoutChangeListener oldValue,
                                                 View.OnLayoutChangeListener newValue) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (oldValue != null) {
                view.removeOnLayoutChangeListener(oldValue);
            }
            if (newValue != null) {
                view.addOnLayoutChangeListener(newValue);
            }
        }
    }
}
