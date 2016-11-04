package com.data;

import com.base.BaseRepository;

import java.util.Map;

import rx.Observable;

/**
 * Created by baixiaokang on 16/7/19.
 * 仓库类，定义仓库货物既数据，钥匙，来源
 */
public abstract class Repository<T> extends BaseRepository {
    public T data;//货物

    public Map<String, String> param;//钥匙

    public abstract Observable<Data<T>> getPageAt(int page);//来源
}
