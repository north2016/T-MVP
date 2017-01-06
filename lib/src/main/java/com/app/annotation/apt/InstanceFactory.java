package com.app.annotation.apt;


/**
 * Created by baixiaokang on 16/10/8.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
/**
 * 实例化注解,会被主动添加到实例化工厂,自动生成new来替换掉反射的newInstance代码
 */
public @interface InstanceFactory {
    int TYPE_VH = 1;//ViewHolder
    int TYPE_DEFAULT = 0;//普通的

    int value() default TYPE_DEFAULT;

    Class clazz() default InstanceFactory.class;//被初始化的目标类，默认为当前加注解的类
}


