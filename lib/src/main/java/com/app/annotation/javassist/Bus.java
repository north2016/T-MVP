package com.app.annotation.javassist;

/**
 * Created by baixiaokang on 16/11/15.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 利用javassist根据bus注解自动添加注册和反注册代码，目前仅支持Activity和Fragment中使用
 * 在其他地方使用 需要配合@BusRegister以及@BusUnRegister一起使用，才能自动生成注册和反注册代码
 * <p>
 * 不按照规则使用，编译器间会直接报错
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Bus {
    int DEFAULT = -1;
    int UI = 0;
    int BG = 1;

    /**
     * 事件订阅的线程
     *
     * @return
     */
    int thread() default DEFAULT;

    /**
     * 事件id
     *
     * @return
     */
    int value();
}
