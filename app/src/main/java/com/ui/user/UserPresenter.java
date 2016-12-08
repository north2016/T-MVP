package com.ui.user;

import com.EventTags;
import com.app.annotation.apt.Instance;
import com.app.annotation.javassist.Bus;
import com.app.annotation.javassist.BusRegister;
import com.app.annotation.javassist.BusUnRegister;
import com.base.OkBus;
import com.base.util.SpUtil;
import com.data.entity._User;

import java.io.File;

/**
 * Created by baixiaokang on 16/5/5.
 */
@Instance
public class UserPresenter extends UserContract.Presenter {

    @Override
    public void upLoadFace(File file) {
        mRxManager.add(mModel.upFile(file).subscribe(
                res -> upUserInfo(res.url),
                e -> mView.showMsg("上传失败!")));

    }

    @Override
    public void upUserInfo(String face) {
        _User user = SpUtil.getUser();
        user.face = face;
        mRxManager.add(mModel.upUser(user).subscribe(
                res -> {
                    SpUtil.setUser(user);
                    OkBus.getInstance().onEvent(EventTags.ON_USER_LOGIN, user);
                    //mRxManager.post(C.EVENT_LOGIN, user);
                    mView.showMsg("更新成功!");
                },
                e -> mView.showMsg("更新失败!")));
    }

    @Override
    @BusRegister
    public void onAttached() {
    }

    @Bus(tag = EventTags.ON_USER_LOGIN)
    public void OnLogin(_User user) {
        mView.initUser(user);
    }

    @Override
    @BusUnRegister
    public void onDetached() {
        super.onDetached();
    }
}
