package com.ui.user;

import com.base.BasePresenter;
import com.base.BaseView;
import com.base.adapter.AdapterPresenter;
import com.model._User;

import java.io.File;

/**
 * Created by baixiaokang on 16/5/5.
 */
public interface UserContract {

    interface View extends BaseView {
        void showMsg(String msg);

        void initUser(_User user);

        void takePhoto();
    }

    abstract class Presenter extends BasePresenter<View> {

        public abstract void upLoadFace(File f);

        public abstract void upUserInfo(String face);

        public abstract void initAdapterPresenter(AdapterPresenter mAdapterPresenter, _User user);
    }
}
