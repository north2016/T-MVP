package com.base;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by baixiaokang on 16/12/26.
 * 支持多类型的ViewHolder
 */

public abstract class BaseMultiVH<T> extends BaseViewHolder<T> {
    public BaseMultiVH(View v) {
        super(v);
        if (((ViewGroup) v).getChildCount() > 0)
            initView(v);
    }

    /**
     * 根据数据获得相对应的布局类型
     *
     * @param item
     * @return
     */
    public abstract int getMultiType(T item);

    /**
     * 不同布局交给子类去初始化view
     *
     * @param v
     */
    public abstract void initView(View v);
}
