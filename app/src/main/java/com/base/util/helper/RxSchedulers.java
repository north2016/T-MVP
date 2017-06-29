package com.base.util.helper;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by baixiaokang on 16/5/6.
 */
public class RxSchedulers {
    public static final Observable.Transformer<?, ?> mTransformer
            = observable -> observable
            .timeout(10, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());

    @SuppressWarnings("unchecked")
    public static <T> Observable.Transformer<T, T> io_main() {
        return (Observable.Transformer<T, T>) mTransformer;
    }
}
