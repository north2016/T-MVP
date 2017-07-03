package com.base;

import com.base.entity.DataArr;

import java.util.HashMap;

import io.reactivex.Flowable;

/**
 * Created by baixiaokang on 16/7/19.
 */
public interface NetRepository {
    Flowable<DataArr> getData(HashMap<String, Object> param);
}
