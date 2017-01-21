package com.ui.home;

import com.base.BasePresenter;
import com.base.BaseView;
import com.model._User;

/**
 * Created by baixiaokang on 16/4/22.
 */
public interface HomeContract {
    interface View extends BaseView {
        void showTabList(String[] mTabs);

        void initUserInfo(_User user);

        void onOpenRelease();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getTabList();

        public abstract void getUserInfo();
    }
}
