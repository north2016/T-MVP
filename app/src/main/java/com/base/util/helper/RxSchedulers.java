package com.base.util.helper;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by baixiaokang on 16/5/6.
 */
public class RxSchedulers {
    public static final Observable.Transformer<?, ?> mTransformer
            = observable -> observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());

    @SuppressWarnings("unchecked")
    public static <T> Observable.Transformer<T, T> io_main() {
        return (Observable.Transformer<T, T>) mTransformer;
    }
}
