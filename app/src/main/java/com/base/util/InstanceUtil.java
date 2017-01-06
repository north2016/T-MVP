package com.base.util;

import android.view.View;
import android.widget.LinearLayout;

import com.App;
import com.app.annotation.aspect.MemoryCache;
import com.app.annotation.aspect.TimeLog;
import com.apt.InstanceFactory;
import com.base.adapter.BaseViewHolder;

import java.lang.reflect.ParameterizedType;


/**
 * Created by baixiaokang on 16/4/30.
 */
public class InstanceUtil {

    private static View PUPPET_VIEW = new LinearLayout(App.getAppContext());// 傀儡view

    /**
     * 通过实例工厂去实例化相应类
     *
     * @param o   带泛型的对象
     * @param i   需要实例化的对象在泛型中的位置
     * @param <T> 返回实例的泛型类型
     * @return
     */
    @TimeLog
    public static <T> T getInstance(Object o, int i) {
        if (o.getClass().getGenericSuperclass() instanceof ParameterizedType) {
            Class mClass = (Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i];
            return getInstance(mClass);
        }
        return null;
    }

    /**
     * 通过实例工厂去实例化相应类
     *
     * @param <T> 返回实例的泛型类型
     * @return
     */
    @TimeLog
    public static <T> T getInstance(Class clazz) {
        try {
            return (T) InstanceFactory.create(clazz, BaseViewHolder.class.isAssignableFrom(clazz) ? PUPPET_VIEW : null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @MemoryCache
    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
