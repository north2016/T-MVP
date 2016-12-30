package com.ui.user;

import com.EventTags;
import com.app.annotation.apt.InstanceFactory;
import com.app.annotation.javassist.Bus;
import com.app.annotation.javassist.BusRegister;
import com.app.annotation.javassist.BusUnRegister;
import com.apt.ApiFactory;
import com.base.OkBus;
import com.base.util.SpUtil;
import com.data.entity.Face;
import com.data.entity._User;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by baixiaokang on 16/5/5.
 */
@InstanceFactory
public class UserPresenter extends UserContract.Presenter {

    @Override
    public void upLoadFace(File file) {
        mCompositeSubscription.add(ApiFactory.upFile(file.getName(), RequestBody.create(MediaType.parse("image/*"), file)).subscribe(
                res -> upUserInfo(res.url),
                e -> mView.showMsg("上传失败!")));
    }

    @Override
    public void upUserInfo(String face) {
        _User user = SpUtil.getUser();
        user.face = face;
        mCompositeSubscription.add(ApiFactory.upUser(user.sessionToken, user.objectId, new Face(user.face)).subscribe(
                res -> {
                    SpUtil.setUser(user);
                    OkBus.getInstance().onEvent(EventTags.ON_USER_LOGIN, user);
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
