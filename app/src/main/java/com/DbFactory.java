package com;

import com.model.CommentInfo;
import com.model.ImageInfo;
import com.model.MessageInfo;
import com.model._User;

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
 * Created by baixiaokang on 17/1/25.
 */

public class DbFactory {

    public static Flowable getAllImages(HashMap<String, Object> param) {
        HashMap<String ,String > map=new HashMap<>();
        map.put(C.TYPE, (String) param.get(C.TYPE));
        return Flowable.defer(() -> getRealmItems(ImageInfo.class,map));
    }

    public static Flowable getCommentList(HashMap<String, Object> param) {
        HashMap<String ,String > map=new HashMap<>();
        map.put("article.objectId", (String) param.get(C.OBJECT_ID));
        return Flowable.defer(() -> getRealmItems(CommentInfo.class,map));
    }

    public static Flowable getAllUser(HashMap<String, Object> param) {
        return Flowable.defer(() -> getRealmItems(_User.class,null));
    }

    public static Flowable getUserCommentList(HashMap<String, Object> param) {
        HashMap<String ,String > map=new HashMap<>();
        map.put("creater.objectId", (String) param.get(C.OBJECT_ID));
        return Flowable.defer(() -> getRealmItems(CommentInfo.class,map));
    }

    public static Flowable getMessageList(HashMap<String, Object> param) {
        HashMap<String ,String > map=new HashMap<>();
        map.put(C.UID, (String) param.get(C.UID));
        return Flowable.defer(() -> getRealmItems(MessageInfo.class,map));
    }

    public static <T extends RealmModel> Flowable<RealmResults<T>> getRealmItems(Class clazz, HashMap<String,String> map) {
        return Flowable.create(new FlowableOnSubscribe<RealmResults<T>>() {
            @Override
            public void subscribe(FlowableEmitter<RealmResults<T>> emitter)
                    throws Exception {
                Realm realm = Realm.getDefaultInstance();
                RealmQuery<T> query=realm.where(clazz);
                if(map!=null){
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        query.equalTo(entry.getKey(),entry.getValue());
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
