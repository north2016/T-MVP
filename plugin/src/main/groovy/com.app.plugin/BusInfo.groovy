package com.app.plugin

import javassist.CtClass
import javassist.CtMethod
import javassist.bytecode.annotation.Annotation
import org.gradle.api.Project

/**
 * Created by baixiaokang on 16/11/14.
 * 事件信息类
 */

public class BusInfo {

    Project project//保留当前工程的引用
    CtClass clazz//当前处理的class
    List<CtMethod> methods = new ArrayList<>()//带有Bus注解的方法列表
    List<Annotation> annotations = new ArrayList<>()//带有Bus注解的注解列表
    List<Integer> eventIds = new ArrayList<>()//带有Bus注解的注解id列表
    boolean isActivity = false;//是否是在Activity
    CtMethod OnCreateMethod//Activity或Fragment的初始化方法
    CtMethod OnDestroyMethod//Activity或Fragment的销毁方法
    CtMethod BusRegisterMethod//被Register注解标注的初始化方法
    CtMethod BusUnRegisterMethod//被UnRegister注解标注的销毁方法
}
