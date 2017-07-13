package com.base.util.helper;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.disposables.Disposables;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by baixiaokang on 17/7/13.
 */

public class RealmHelper {
    public static <T extends RealmModel> Flowable<RealmResults<T>> getRealmItems(Class clazz, HashMap<String, String> map) {
        return Flowable.create(new FlowableOnSubscribe<RealmResults<T>>() {
            @Override
            public void subscribe(FlowableEmitter<RealmResults<T>> emitter)
                    throws Exception {
                Realm realm = Realm.getDefaultInstance();
                RealmQuery<T> query = realm.where(clazz);
                if (map != null) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        query.equalTo(entry.getKey(), entry.getValue());
                    }
                }
                RealmResults<T> results = query.findAll();

                final RealmChangeListener<RealmResults<T>> listener = _realm -> {
                    if (!emitter.isCancelled()) {
                        emitter.onNext(results);
                    }
                };
                emitter.setDisposable(Disposables.fromRunnable(() -> {
                    results.removeChangeListener(listener);
                    realm.close();
                }));
                results.addChangeListener(listener);
                emitter.onNext(results);
            }
        }, BackpressureStrategy.LATEST);
    }
}
