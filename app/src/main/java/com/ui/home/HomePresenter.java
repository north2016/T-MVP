package com.ui.home;

import com.C;
import com.EventTags;
import com.app.annotation.apt.Instance;
import com.app.annotation.javassist.Bus;
import com.app.annotation.javassist.BusRegister;
import com.base.OkBus;
import com.base.util.SpUtil;
import com.data.entity._User;

/**
 * Created by baixiaokang on 16/4/22.
 */
@Instance
public class HomePresenter extends HomeContract.Presenter {


    @Override
    public void getUserInfo() {
        _User user = SpUtil.getUser();
        if (user != null)
            mView.initUserInfo(user);
    }

    @Override
    public void onAttached() {
        initEvent();
        getTabList();
        getUserInfo();
        mRxManager.on(C.EVENT_LOGIN, arg -> mView.initUserInfo((_User) arg));
    }

    @Override
    public void getTabList() {
        OkBus.getInstance().onEvent(EventTags.SHOW_TAB_LIST, mModel.getTabs());
    }

    @Bus(tag = EventTags.SHOW_TAB_LIST)
    public void showTabList(String[] tabs) {
        mView.showTabList(tabs);
    }

    @BusRegister
    private void initEvent() {
    }

    @Override
    //@BusUnRegister
    public void onDetached() {
        super.onDetached();
    }
}
