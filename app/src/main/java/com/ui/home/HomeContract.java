package com.ui.home;

import com.base.BaseModel;
import com.base.BasePresenter;
import com.base.BaseView;
import com.data.entity._User;

/**
 * Created by baixiaokang on 16/4/22.
 */
public interface HomeContract {
    interface Model extends BaseModel {
        String[] getTabs();
    }


    interface View extends BaseView {
        void showTabList(String[] mTabs);

        void initUserInfo(_User user);
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void getTabList();

        public abstract void getUserInfo();
    }
}
