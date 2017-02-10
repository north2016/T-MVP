package com.base;

import java.util.HashMap;

import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by baixiaokang on 17/1/25.
 */

public interface DbRepository {
    Observable<RealmResults> getData(HashMap<String, Object> param);
}
