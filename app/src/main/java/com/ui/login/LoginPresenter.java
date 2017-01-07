package com.ui.login;

import com.EventTags;
import com.app.annotation.apt.InstanceFactory;
import com.apt.ApiFactory;
import com.base.event.OkBus;
import com.base.util.SpUtil;
import com.model._User;

/**
 * Created by baixiaokang on 16/4/29.
 */
@InstanceFactory
public class LoginPresenter extends LoginContract.Presenter {

    @Override
    public void login(String name, String pass) {
        mCompositeSubscription.add(
                ApiFactory.login(name, pass).subscribe(user -> {
                            SpUtil.setUser(user);
                            OkBus.getInstance().onEvent(EventTags.ON_USER_LOGIN, user);
                            mView.loginSuccess();
                            mView.showMsg("登录成功!");
                        }, e -> mView.showMsg("登录失败!")
                ));
    }

    @Override
    public void sign(String name, String pass) {
        mCompositeSubscription.add(
                ApiFactory.createUser(new _User(name, pass))
                        .subscribe(res -> mView.signSuccess(),
                                e -> mView.showMsg("注册失败!")));
    }
}
