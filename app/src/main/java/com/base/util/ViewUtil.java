package com.base.util;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;


public class ViewUtil {
    /**
     * 隐藏软键盘
     */
    public static void hideKeyboard(Activity c) {
        try {
            InputMethodManager imm = (InputMethodManager) c
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(c.getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException e) {
        }
    }
}
