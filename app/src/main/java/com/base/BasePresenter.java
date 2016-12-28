package com.base;

import android.content.Context;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by baixiaokang on 16/4/22.
 */
public abstract class BasePresenter<V> {
    public Context context;
    public V mView;
    public CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    public void setView(V v) {
        this.mView = v;
        this.onAttached();
    }

    public abstract void onAttached();

    public void onDetached() {
        mCompositeSubscription.unsubscribe();
    }
}
