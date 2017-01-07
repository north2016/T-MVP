package com.model;

import com.base.BaseBean;

/**
 * Created by baixiaokang on 16/4/29.
 */
public class _User extends BaseBean {
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
}
