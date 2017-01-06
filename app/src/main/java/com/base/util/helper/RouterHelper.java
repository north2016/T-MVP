package com.base.util.helper;

import android.view.View;

import com.apt.TRouter;

import java.util.HashMap;

/**
 * Created by baixiaokang on 17/1/6.
 */

public class RouterHelper {
    public static void go(String actionName, HashMap data, View view) {
        TRouter.go(actionName, data, view);
    }
}
