package com.hudson.jetpackfeature.databinding.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hudson.jetpackfeature.R;
import com.hudson.jetpackfeature.databinding.ItemUnknownTypeOneBinding;
import com.hudson.jetpackfeature.databinding.entity.UnknownTypeOneData;
import com.hudson.jetpackfeature.databinding.viewholder.BindingHolder;

import androidx.annotation.NonNull;

/**
 * Created by Hudson on 2020/6/21.
 */
public class TypeOneAdapter extends UnknownTypeAdapter<UnknownTypeOneData> {
    @NonNull
    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewRoot = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_unknown_type_one, parent, false);
        ItemUnknownTypeOneBinding binding = ItemUnknownTypeOneBinding.bind(viewRoot);
        return new BindingHolder<ItemUnknownTypeOneBinding>(binding);
    }
}
