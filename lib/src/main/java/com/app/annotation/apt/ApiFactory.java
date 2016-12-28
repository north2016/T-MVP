package com.app.annotation.apt;

/**
 * Created by baixiaokang on 16/12/28.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface ApiFactory {
}
