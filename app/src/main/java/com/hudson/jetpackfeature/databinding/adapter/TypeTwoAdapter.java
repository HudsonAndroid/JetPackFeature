package com.hudson.jetpackfeature.databinding.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hudson.jetpackfeature.R;
import com.hudson.jetpackfeature.databinding.ItemUnknownTypeOneBinding;
import com.hudson.jetpackfeature.databinding.ItemUnknownTypeTwoBinding;
import com.hudson.jetpackfeature.databinding.entity.UnknownTypeTwoData;
import com.hudson.jetpackfeature.databinding.viewholder.BindingHolder;

import androidx.annotation.NonNull;

/**
 * Created by Hudson on 2020/6/21.
 */
public class TypeTwoAdapter extends UnknownTypeAdapter<UnknownTypeTwoData> {
    @NonNull
    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewRoot = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_unknown_type_two, parent, false);
        ItemUnknownTypeTwoBinding binding = ItemUnknownTypeTwoBinding.bind(viewRoot);
        return new BindingHolder<ItemUnknownTypeTwoBinding>(binding);
    }
}
