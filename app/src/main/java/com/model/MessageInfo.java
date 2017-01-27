package com.model;

import com.base.BaseBean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by baixiaokang on 16/12/24.
 */
public class MessageInfo extends RealmObject implements BaseBean {
    @PrimaryKey
    public String objectId;
    public _User receiver;
    public String message;
    public String uId;
    public _User creater;

    @Override
    public String getObjectId() {
        return objectId;
    }
}
