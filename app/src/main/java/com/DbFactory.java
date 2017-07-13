package com;

import com.base.util.helper.RealmHelper;
import com.model.CommentInfo;
import com.model.ImageInfo;
import com.model.MessageInfo;
import com.model._User;

import java.util.HashMap;

import io.reactivex.Flowable;

/**
 * Created by baixiaokang on 17/1/25.
 */

public class DbFactory {

    public static Flowable getAllImages(HashMap<String, Object> param) {
        HashMap<String, String> map = new HashMap<>();
        map.put(C.TYPE, (String) param.get(C.TYPE));
        return Flowable.defer(() -> RealmHelper.getRealmItems(ImageInfo.class, map));
    }

    public static Flowable getCommentList(HashMap<String, Object> param) {
        HashMap<String, String> map = new HashMap<>();
        map.put("article.objectId", (String) param.get(C.OBJECT_ID));
        return Flowable.defer(() -> RealmHelper.getRealmItems(CommentInfo.class, map));
    }

    public static Flowable getAllUser(HashMap<String, Object> param) {
        return Flowable.defer(() -> RealmHelper.getRealmItems(_User.class, null));
    }

    public static Flowable getUserCommentList(HashMap<String, Object> param) {
        HashMap<String, String> map = new HashMap<>();
        map.put("creater.objectId", (String) param.get(C.OBJECT_ID));
        return Flowable.defer(() -> RealmHelper.getRealmItems(CommentInfo.class, map));
    }

    public static Flowable getMessageList(HashMap<String, Object> param) {
        HashMap<String, String> map = new HashMap<>();
        map.put(C.UID, (String) param.get(C.UID));
        return Flowable.defer(() -> RealmHelper.getRealmItems(MessageInfo.class, map));
    }
}
