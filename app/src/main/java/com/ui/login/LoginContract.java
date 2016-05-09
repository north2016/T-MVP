package com.ui.login;

import com.base.BaseModel;
import com.base.BasePresenter;
import com.base.BaseView;
import com.data.CreatedResult;
import com.data.entity._User;

import rx.Observable;

/**
 * Created by baixiaokang on 16/4/29.
 */
public interface LoginContract {
    interface Model extends BaseModel {
        Observable<_User> login(String name, String pass);
        Observable<CreatedResult> sign(String name, String pass);
    }

    interface View extends BaseView {
        void loginSuccess();
        void signSuccess();
        void showMsg(String  msg);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void login(String name, String pass);
        public abstract void sign(String name, String pass);
        @Override
        public void onStart() {}
    }
}
