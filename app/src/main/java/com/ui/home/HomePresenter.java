package com.ui.home;

import android.text.TextUtils;

import com.C;
import com.EventTags;
import com.app.annotation.apt.InstanceFactory;
import com.app.annotation.javassist.Bus;
import com.app.annotation.javassist.BusRegister;
import com.app.annotation.javassist.BusUnRegister;
import com.base.event.OkBus;
import com.base.util.SpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.model._User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by baixiaokang on 16/4/22.
 */
@InstanceFactory
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
      List<String > data=new ArrayList<>();
        if (!TextUtils.isEmpty(SpUtil.getData(C.TAB))) {
            List<String > old= new Gson().fromJson(SpUtil.getData(C.TAB), new TypeToken<List<String>>() {
            }.getType());
            data.addAll(old);
        }else{
            data.addAll(Arrays.asList(C.HOME_TABS));
        }
        OkBus.getInstance().onEvent(EventTags.SHOW_TAB_LIST, data);
    }

    @Bus(EventTags.SHOW_TAB_LIST)
    public void showTabList(List<String> tabs) {
        mView.showTabList(tabs);
    }

    @BusRegister
    private void initEvent() {
    }

    @Bus(EventTags.ON_RELEASE_OPEN)
    public void onRelease() {
        mView.onOpenRelease();
    }

    @Bus(EventTags.ON_USER_LOGIN)
    public void OnLogin(_User user) {
        mView.initUserInfo(user);
    }

    @BusUnRegister
    public void onDetached() {
        super.onDetached();
    }
}
