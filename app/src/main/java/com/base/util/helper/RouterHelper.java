package com.base.util.helper;

import android.view.View;

import com.app.annotation.aspect.SingleClick;
import com.apt.TRouter;

import java.util.HashMap;

/**
 * Created by baixiaokang on 17/1/6.
 */

public class RouterHelper {

    @SingleClick // 防止连续点击
    public static void go(String actionName, HashMap data, View view) {
        if (data.containsValue(null)) return;
        TRouter.go(actionName, data, view);
    }
}
