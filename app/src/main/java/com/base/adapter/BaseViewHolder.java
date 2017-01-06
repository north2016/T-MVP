package com.base.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public class BaseViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    public final T mViewDataBinding;

    public BaseViewHolder(T t) {
        super(t.getRoot());
        mViewDataBinding = t;
    }
}
