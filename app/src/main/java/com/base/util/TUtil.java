package com.base.util;

import com.app.annotation.aspect.TimeLog;
import com.ui.article.InstanceFactory;

import java.lang.reflect.ParameterizedType;

/**
 * Created by baixiaokang on 16/4/30.
 */
public class TUtil {
    /**
     * 通过实例工厂去实例化相应类
     *
     * @param o   带泛型的对象
     * @param i   需要实例化的对象在泛型中的位置
     * @param <T> 返回实例的泛型类型
     * @return
     */
    @TimeLog
    public static <T> T getT(Object o, int i) {
        if(o.getClass().getGenericSuperclass() instanceof  ParameterizedType){
            Class mClass = (Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i];
            try {
                return (T) InstanceFactory.create(mClass);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
