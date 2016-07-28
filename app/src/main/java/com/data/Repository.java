package com.data;

import com.base.BaseRepository;

import java.util.Map;

import rx.Observable;

/**
 * Created by baixiaokang on 16/7/19.
 */
public abstract class Repository<T> extends BaseRepository {
    public T data;

    public Map<String, String> param;

    public abstract Observable<Data<T>> getPageAt(int page);
}
