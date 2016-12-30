package com.app.aop.aspect;


import com.app.annotation.aspect.JoinPointCallBack;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;


/**
 * 根据Router注解自动添加初始化代码，通过aop切片的方式在编译期间织入源代码中
 * 功能：给Router注解的Activity自动添加参数和转场View的赋值。
 */
@Aspect
public class RouterAspect {
    @Pointcut("(execution(* *..Activity+.initView(..)))")//方法切入点, 所有Activity的initView之前
    public void methodAnnotated() {
    }

    @Before("methodAnnotated()")//在连接点之前进行Router初始化
    public void aroundJoinPoint(JoinPoint joinPoint) throws Throwable {
        //获取目标对象,插入自己的实现，控制目标对象的执行
        ((JoinPointCallBack) joinPoint.getTarget()).onJoinPointCallBack();
    }
}
