package com;

import android.app.Application;
import android.content.Context;

import com.app.annotation.aspect.TimeLog;
import com.base.util.SpUtil;

/**
 * Created by baixiaokang on 16/4/23.
 */
public class App extends Application {
    private static App mApp;

    @Override
    @TimeLog
    public void onCreate() {
        super.onCreate();
        mApp = this;
        SpUtil.init(this);
    }

    public static Context getAppContext() {
        return mApp;
    }
}
