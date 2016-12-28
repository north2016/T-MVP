package com.ui.login;

import com.base.BasePresenter;
import com.base.BaseView;

/**
 * Created by baixiaokang on 16/4/29.
 */
public interface LoginContract {
    interface View extends BaseView {
        void loginSuccess();

        void signSuccess();

        void showMsg(String msg);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void login(String name, String pass);

        public abstract void sign(String name, String pass);

        @Override
        public void onAttached() {
        }
    }
}
