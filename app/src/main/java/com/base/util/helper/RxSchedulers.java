package com.base.util.helper;

import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by baixiaokang on 16/5/6.
 */
public class RxSchedulers {
    public static final FlowableTransformer<?, ?> mTransformer
            = observable -> observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());

    @SuppressWarnings("unchecked")
    public static <T> FlowableTransformer<T, T> io_main() {
        return (FlowableTransformer<T, T>) mTransformer;
    }
}
