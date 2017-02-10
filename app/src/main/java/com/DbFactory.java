package com;

import com.model.CommentInfo;
import com.model.ImageInfo;
import com.model.MessageInfo;
import com.model._User;

import java.util.HashMap;

import io.realm.Realm;
import rx.Observable;

/**
 * Created by baixiaokang on 17/1/25.
 */

public class DbFactory {
    public static Observable getAllImages(HashMap<String, Object> param) {
        return Observable.defer(() -> Realm.getDefaultInstance()
                .where(ImageInfo.class)
                .equalTo(C.TYPE, (String) param.get(C.TYPE))
                .findAll().asObservable());
    }

    public static Observable getCommentList(HashMap<String, Object> param) {
        return Observable.defer(() -> Realm.getDefaultInstance()
                .where(CommentInfo.class)
                .equalTo("article.objectId", (String) param.get(C.OBJECT_ID))
                .findAll().asObservable());
    }

    public static Observable getAllUser(HashMap<String, Object> param) {
        return Observable.defer(() ->
                Realm.getDefaultInstance().where(_User.class).findAll().asObservable());
    }

    public static Observable getUserCommentList(HashMap<String, Object> param) {
        return Observable.defer(() -> Realm.getDefaultInstance()
                .where(CommentInfo.class)
                .equalTo("creater.objectId", (String) param.get(C.OBJECT_ID))
                .findAll().asObservable());
    }

    public static Observable getMessageList(HashMap<String, Object> param) {
        return Observable.defer(() -> Realm.getDefaultInstance()
                .where(MessageInfo.class)
                .equalTo(C.UID, (String) param.get(C.UID))
                .findAll().asObservable());
    }
}
