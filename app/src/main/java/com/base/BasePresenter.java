package com.base;

import android.content.Context;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by baixiaokang on 16/4/22.
 */
public abstract class BasePresenter<M, V> {
    public Context context;
    public M mModel;
    public V mView;
    public CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    public void setVM(V v, M m) {
        this.mView = v;
        this.mModel = m;
        this.onAttached();
    }

    public abstract void onAttached();

    public void onDetached() {
        mCompositeSubscription.unsubscribe();
    }
}
