package com.base.util;

import android.view.View;

import com.app.annotation.aspect.MemoryCache;
import com.app.annotation.aspect.TimeLog;
import com.data.repository.InstanceFactory;

import java.lang.reflect.ParameterizedType;

/**
 * Created by baixiaokang on 16/4/30.
 */
public class InstanceUtil {
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
            return (T) InstanceFactory.create(clazz);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
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
    public static <T> T getInstance(Class clazz, View view) {
        return (T) InstanceFactory.create(clazz, view);
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
