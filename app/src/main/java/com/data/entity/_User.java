package com.data.entity;

import com.C;
import com.api.Api;
import com.base.BaseEntity;
import com.base.util.helper.RxSchedulers;

import java.util.Map;

import rx.Observable;

/**
 * Created by baixiaokang on 16/4/29.
 */
public class _User extends BaseEntity.ListBean {
    public String username;
    public String password;
    public String face;
    public String sessionToken;

    public _User() {
    }

    public _User(String name, String pass) {
        this.username = name;
        this.password = pass;
    }

    @Override
    public Observable getPageAt(int page) {
        return Api.getInstance().movieService.getAllUser(C.PAGE_COUNT * (page - 1), C.PAGE_COUNT).compose(RxSchedulers.io_main());
    }
}
