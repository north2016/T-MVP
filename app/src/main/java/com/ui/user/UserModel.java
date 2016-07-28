package com.ui.user;

import com.api.Api;
import com.base.util.helper.RxSchedulers;
import com.data.CreatedResult;
import com.data.entity._User;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by baixiaokang on 16/5/5.
 */
public class UserModel implements UserContract.Model {

    public class Face {
        String face;

        public Face(String f) {
            this.face = f;
        }
    }

    @Override
    public Observable<CreatedResult> upFile(File file) {
        return Api.getInstance()
                .service
                .upFile(file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                .compose(RxSchedulers.io_main());
    }

    @Override
    public Observable upUser(_User user) {
        return Api.getInstance().service
                .upUser(user.sessionToken, user.objectId, new Face(user.face))
                .compose(RxSchedulers.io_main());
    }
}
