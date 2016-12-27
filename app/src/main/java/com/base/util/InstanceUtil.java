package com.base.util;

import android.view.View;
import android.widget.LinearLayout;

import com.App;
import com.app.annotation.aspect.MemoryCache;
import com.app.annotation.aspect.TimeLog;
import com.base.BaseViewHolder;
import com.ui.article.InstanceFactory;

import java.lang.reflect.ParameterizedType;

import static com.data.entity.RepositoryFactory.create;

//import com.ui.article.InstanceFactory;

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
            return (T) InstanceFactory.create(clazz, BaseViewHolder.class.isAssignableFrom(clazz) ? new LinearLayout(App.getAppContext()) : null);
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
    public static <T> T getViewHolder(Class clazz, View view) {
        return (T) InstanceFactory.createVH(clazz, view);
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

    public static <T> T getRepositoryInstance(Class cla) {
        try {
            return (T) create((Class) ((ParameterizedType) (cla
                    .getGenericSuperclass())).getActualTypeArguments()[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
