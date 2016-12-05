package com.ui.user;

import android.content.Intent;

import com.base.BaseModel;
import com.base.BasePresenter;
import com.base.BaseView;
import com.data.CreatedResult;
import com.data.entity._User;

import java.io.File;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by baixiaokang on 16/5/5.
 */
public interface UserContract {
    interface Model extends BaseModel {
        Observable<CreatedResult> upFile(File file);

        Observable upUser( _User user);
    }


    interface View extends BaseView {

        void showMsg(String msg);
       void  initUser(_User user);
    }

    abstract class Presenter extends BasePresenter<Model, View> {

        public abstract void upLoadFace(File f);

        public abstract void upUserInfo(String face);
    }
}
