package com.ui.login;

import com.EventTags;
import com.app.annotation.apt.Instance;
import com.base.OkBus;
import com.base.util.SpUtil;

/**
 * Created by baixiaokang on 16/4/29.
 */
@Instance
public class LoginPresenter extends LoginContract.Presenter {

    @Override
    public void login(String name, String pass) {
        mCompositeSubscription.add(mModel.login(name, pass).subscribe(user -> {
                    SpUtil.setUser(user);
                    OkBus.getInstance().onEvent(EventTags.ON_USER_LOGIN, user);
                    mView.loginSuccess();
                    mView.showMsg("登录成功!");
                }, e -> mView.showMsg("登录失败!")
        ));
    }

    @Override
    public void sign(String name, String pass) {
        mCompositeSubscription.add(mModel.sign(name, pass)
                .subscribe(res -> mView.signSuccess(),
                        e -> mView.showMsg("注册失败!")));
    }
}
