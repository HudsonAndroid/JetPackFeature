package com.hudson.jetpackfeature.databinding.viewholder;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Hudson on 2020/6/21.
 */
public class BindingHolder<Binding extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private Binding mBinding;

    public BindingHolder(Binding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public Binding getBinding() {
        return mBinding;
    }
}
