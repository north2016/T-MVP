package com.ui.home;

import com.EventTags;
import com.app.annotation.apt.Instance;
import com.app.annotation.javassist.Bus;
import com.app.annotation.javassist.BusRegister;
import com.app.annotation.javassist.BusUnRegister;
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

    @Bus(tag = EventTags.ON_USER_LOGIN)
    public void OnLogin(_User user) {
        mView.initUserInfo(user);
    }

    @Override
    @BusUnRegister
    public void onDetached() {
        super.onDetached();
    }
}
