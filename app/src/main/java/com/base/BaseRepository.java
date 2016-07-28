package com.base;

import com.data.Repository;

/**
 * Created by baixiaokang on 16/7/19.
 */
public class BaseRepository implements Cloneable {
    @Override
    public Object clone() {
        Repository stu = null;
        try {
            stu = (Repository) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return stu;
    }
}