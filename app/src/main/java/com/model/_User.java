package com.model;

import com.base.BaseBean;

import io.realm.RealmObject;

/**
 * Created by baixiaokang on 16/4/29.
 */
public class _User extends RealmObject implements BaseBean {
    public String objectId;
    public String username;
    public String password;
    public String face;
    public String sessionToken;

    public _User() {
    }

    public _User(String objectId) {
        this.objectId = objectId;
    }

    public _User(String name, String pass) {
        this.username = name;
        this.password = pass;
    }

    @Override
    public String getObjectId() {
        return objectId;
    }
}
