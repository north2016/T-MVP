package com.model;

import com.base.BaseBean;

import io.realm.RealmObject;

/**
 * Created by baixiaokang on 16/4/29.
 */
public class User extends RealmObject implements BaseBean {
    public String objectId;
    public String username;
    public String password;
    public String face;
    public String sessionToken;

    public User() {
    }

    public User(String objectId) {
        this.objectId = objectId;
    }

    public User(String name, String pass) {
        this.username = name;
        this.password = pass;
    }

    @Override
    public String getObjectId() {
        return objectId;
    }
}
