package com.data.entity;

import com.app.annotation.apt.Repository;
import com.base.BaseEntity;
import com.data.repository._UserRepository;

/**
 * Created by baixiaokang on 16/4/29.
 */
@Repository(clazz = _UserRepository.class)
public class _User extends BaseEntity.BaseBean {
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
