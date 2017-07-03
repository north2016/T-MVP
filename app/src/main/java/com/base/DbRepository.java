package com.base;

import java.util.HashMap;

import io.reactivex.Flowable;
import io.realm.RealmResults;

/**
 * Created by baixiaokang on 17/1/25.
 */

public interface DbRepository {
    Flowable<RealmResults> getData(HashMap<String, Object> param);
}
