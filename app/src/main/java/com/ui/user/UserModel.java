package com.ui.user;

import android.os.Handler;

import com.api.Api;
import com.base.util.ApiUtil;
import com.base.util.ImageUtil;
import com.base.util.helper.RxSchedulers;
import com.data.CreatedResult;
import com.data.entity._User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
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
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
        return Api.getInstance()
                .movieService
                .upFile(file.getName(), fileBody)
                .compose(RxSchedulers.io_main());
    }

    @Override
    public Observable upUser(_User user) {
        return Api.getInstance().movieService
                .upUser(user.sessionToken, user.objectId, new Face(user.face))
                .compose(RxSchedulers.io_main());
    }
}
