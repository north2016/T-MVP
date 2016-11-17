package com.app.annotation.javassist;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by baixiaokang on 16/11/16.
 */

/**
 * 在非Activity和Fragment中使用该注解，会自动在其方法体后面加反注册逻辑
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BusUnRegister {
}
