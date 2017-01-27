package com.base;

import com.base.entity.DataArr;

import java.util.HashMap;

import rx.Observable;

/**
 * Created by baixiaokang on 16/7/19.
 */
public interface NetRepository {
    Observable<DataArr> getData(HashMap<String, Object> param);
}
