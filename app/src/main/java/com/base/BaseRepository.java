package com.base;

import com.data.Repository;

/**
 * Created by baixiaokang on 16/7/19.
 * 仓库基类，定义可以被复制的集装箱
 */
public class BaseRepository implements Cloneable {
    @Override
    public Object clone() {
        Repository ins = null;
        try {
            ins = (Repository) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return ins;
    }
}