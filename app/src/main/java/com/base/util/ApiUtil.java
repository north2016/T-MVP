package com.base.util;

import android.text.TextUtils;

import com.base.BaseEntity;
import com.data.Pointer;

import java.util.Map;

/**
 * Created by baixiaokang on 16/5/5.
 */
public class ApiUtil {

    public static String getWhere(Map<String, String> param) {
        String where = "";
        for (Map.Entry<String, String> entry : param.entrySet()) {
            if (!TextUtils.equals(entry.getKey(), "include")) {
                boolean isJson = entry.getValue().endsWith("}");
                where += "\"" + entry.getKey() + "\":" + (isJson ? "" : "\"") + entry.getValue() + (isJson ? "" : "\"") + ",";
            }
        }
        return "{" + where.substring(0, where.length() - 1) + "}";
    }

    public static <T extends BaseEntity.BaseBean> Pointer getPointer(T obj) {
        return new Pointer(obj.getClass().getSimpleName(), obj.objectId);
    }

    public static String getInclude(Map<String, String> param) {
        String include = "";
        for (Map.Entry<String, String> entry : param.entrySet()) {
            if (TextUtils.equals(entry.getKey(), "include")) {
                include = entry.getValue();
                break;
            }
        }
        return include;
    }
}
