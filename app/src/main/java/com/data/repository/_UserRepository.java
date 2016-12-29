package com.data.repository;

import com.C;
import com.apt.ApiFactory;
import com.data.Repository;
import com.data.entity._User;

import rx.Observable;

/**
 * Created by baixiaokang on 16/4/29.
 */
public class _UserRepository extends Repository<_User> {
    @Override
    public Observable getPageAt(int page) {
        return ApiFactory.getAllUser(C.PAGE_COUNT * (page - 1), C.PAGE_COUNT);
    }
}
