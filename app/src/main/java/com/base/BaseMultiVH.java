package com.base;

import android.view.View;

/**
 * Created by baixiaokang on 16/12/26.
 * 支持多类型的ViewHolder
 */

public abstract class BaseMultiVH<T> extends BaseViewHolder<T> {
    public BaseMultiVH(View v) {
        super(v);
    }

    /**
     * 根据数据获得相对应的布局类型
     *
     * @param item
     * @return
     */
    public abstract int getMultiType(T item);
}
