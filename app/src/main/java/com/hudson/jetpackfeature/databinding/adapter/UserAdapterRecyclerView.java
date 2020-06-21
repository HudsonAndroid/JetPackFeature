package com.hudson.jetpackfeature.databinding.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hudson.jetpackfeature.BR;
import com.hudson.jetpackfeature.R;
import com.hudson.jetpackfeature.databinding.ItemContentBinding;
import com.hudson.jetpackfeature.databinding.entity.User;
import com.hudson.jetpackfeature.databinding.viewholder.BindingHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

/**
 * Created by Hudson on 2020/6/21.
 */
public class UserAdapterRecyclerView extends RecyclerViewBindingAdapter {
    private final List<User> mUsers = new ArrayList<>();

    public void refresh(List<User> users){
        mUsers.clear();
        mUsers.addAll(users);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BindingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContentBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_content,
                parent,false);
        return new BindingHolder<ItemContentBinding>(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder holder, int position) {
        holder.getBinding().setVariable(BR.user,mUsers.get(position));
//        holder.getBinding().executePendingBindings();//数据立刻生效
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }
}
