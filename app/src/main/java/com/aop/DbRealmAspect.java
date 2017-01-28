package com.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 数据库切片，根据注解DbRealm，缓存方法中含有需要缓存到数据库的RealmObject的参数
 * Created by baixiaokang on 17/1/24.
 */
@Aspect
public class DbRealmAspect {

    @Pointcut("execution(@com.app.annotation.aspect.DbRealm * *(..))")//方法切入点
    public void methodAnnotated() {
    }

    @Around("methodAnnotated()")//在连接点进行方法替换
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        joinPoint.proceed();//执行原方法
        Realm realm = Realm.getDefaultInstance();
        Observable.from(joinPoint.getArgs())
                .filter(new Func1<Object, Boolean>() {
                    @Override
                    public Boolean call(Object obj) {
                        return obj instanceof RealmObject || obj instanceof List;
                    }
                })
                .subscribe(
                        new Action1<Object>() {
                            @Override
                            public void call(Object obj) {
                                realm.beginTransaction();
                                if (obj instanceof List) realm.copyToRealmOrUpdate((List) obj);
                                else realm.copyToRealmOrUpdate((RealmObject) obj);
                                realm.commitTransaction();
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable e) {
                                e.printStackTrace();
                            }
                        });
    }
}
