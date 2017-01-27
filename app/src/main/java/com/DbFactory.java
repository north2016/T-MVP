package com;

import com.model.CommentInfo;
import com.model.ImageInfo;
import com.model.MessageInfo;
import com.model._User;

import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by baixiaokang on 17/1/25.
 */

public class DbFactory {
    public static RealmResults getAllImages(HashMap<String, Object> param) {
        return Realm.getDefaultInstance()
                .where(ImageInfo.class)
                .equalTo(C.TYPE, (String) param.get(C.TYPE))
                .findAll();
    }

    public static RealmResults getCommentList(HashMap<String, Object> param) {
        return Realm.getDefaultInstance()
                .where(CommentInfo.class)
                .equalTo("article.objectId", (String) param.get(C.OBJECT_ID))
                .findAll();
    }

    public static RealmResults getAllUser(HashMap<String, Object> param) {
        return Realm.getDefaultInstance().where(_User.class).findAll();
    }

    public static RealmResults getUserCommentList(HashMap<String, Object> param) {
        return Realm.getDefaultInstance()
                .where(CommentInfo.class)
                .equalTo("creater.objectId", (String) param.get(C.OBJECT_ID))
                .findAll();
    }

    public static RealmResults getMessageList(HashMap<String, Object> param) {
        return Realm.getDefaultInstance()
                .where(MessageInfo.class)
                .equalTo(C.UID, (String) param.get(C.UID))
                .findAll();
    }
}
