package com.app.annotation.apt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by baixiaokang on 16/12/30.
 */

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface SceneTransition {
    String value();
}