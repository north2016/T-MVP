package com.ui.login;

import com.api.Api;
import com.base.util.helper.RxSchedulers;
import com.data.CreatedResult;
import com.data.entity._User;

import rx.Observable;

/**
 * Created by baixiaokang on 16/5/2.
 */
public class LoginModel implements LoginContract.Model {

    @Override
    public Observable<_User> login(String name, String pass) {
        return Api.getInstance().service
                .login(name, pass)
                .compose(RxSchedulers.io_main());
    }

    @Override
    public Observable<CreatedResult> sign(String name, String pass) {
        return Api.getInstance().service
                .createUser(new _User(name, pass))
                .compose(RxSchedulers.io_main());
    }
}
