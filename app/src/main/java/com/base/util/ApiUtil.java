package com.base.util;

import android.text.TextUtils;

import com.C;
import com.base.BaseBean;
import com.base.entity.Pointer;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by baixiaokang on 16/5/5.
 */
public class ApiUtil {

    public static String getWhere(Map<String, Object> param) {
        String where = "";
        for (Map.Entry<String, Object> entry : param.entrySet()) {
            if (!TextUtils.equals(entry.getKey(), C.INCLUDE) && !TextUtils.equals(entry.getKey(), C.OBJECT_ID)) {
                if (entry.getValue() instanceof String) {
                    boolean isJson = ((String) entry.getValue()).endsWith("}");
                    where += "\"" + entry.getKey() + "\":" + (isJson ? "" : "\"") + entry.getValue() + (isJson ? "" : "\"") + ",";
                }
            }
        }
        return "{" + where.substring(0, where.length() - 1) + "}";
    }

    public static <T extends Serializable> Pointer getPointer(BaseBean obj) {
        return new Pointer(obj.getClass().getSimpleName(), obj.getObjectId());
    }

    public static <T extends Serializable> Pointer getPointer(String className,BaseBean obj) {
        return new Pointer(className, obj.getObjectId());
    }


    public static String getInclude(Map<String, Object> param) {
        return (String) param.get("include");
    }

    public static int getSkip(Map<String, Object> param) {
        return C.PAGE_COUNT * ((int) param.get(C.PAGE) - 1);
    }
}
