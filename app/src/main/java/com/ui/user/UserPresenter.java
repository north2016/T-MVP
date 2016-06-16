package com.ui.user;

import android.content.Intent;
import android.os.SystemClock;

import com.C;
import com.base.util.ImageUtil;
import com.base.util.SpUtil;
import com.data.entity._User;

import java.io.File;

/**
 * Created by baixiaokang on 16/5/5.
 */
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
                    mRxManager.post(C.EVENT_LOGIN, user);
                    mView.showMsg("更新成功!");
                },
                e -> mView.showMsg("更新失败!")));
    }

    @Override
    public void onStart() {
        mRxManager.on(C.EVENT_LOGIN, user -> mView.initUser((_User) user));
    }
}
