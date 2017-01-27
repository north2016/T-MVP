package com.model;

import com.base.BaseBean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by baixiaokang on 16/5/4.
 */
public class CommentInfo extends RealmObject implements BaseBean {
    @PrimaryKey
    public String objectId;
    public ImageInfo article;
    public String content;
    public _User creater;

    @Override
    public String getObjectId() {
        return objectId;
    }
}
