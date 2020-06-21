package com.hudson.jetpackfeature.databinding.adapter;

import com.hudson.jetpackfeature.BR;
import com.hudson.jetpackfeature.databinding.viewholder.BindingHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Hudson on 2020/6/21.
 */
public abstract class UnknownTypeAdapter<DataType> extends RecyclerView.Adapter<BindingHolder>{
    private final List<DataType> mDatas = new ArrayList<>();

    public void refresh(List<DataType> datas){
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull BindingHolder holder, int position) {
        //约定绑定的数据类变量名为item。
        holder.getBinding().setVariable(BR.item, mDatas.get(position));
        //官方文档中有加入该行代码，意思是使得数据立刻绑定，目前没看出加与不加的区别
        //见https://developer.android.google.cn/topic/libraries/data-binding/generated-binding#dynamic_variables
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
