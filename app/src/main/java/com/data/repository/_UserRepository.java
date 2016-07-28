package com.data.repository;

import com.C;
import com.api.Api;
import com.data.Repository;
import com.base.util.helper.RxSchedulers;
import com.data.entity._User;

import rx.Observable;

/**
 * Created by baixiaokang on 16/4/29.
 */
public class _UserRepository extends Repository<_User> {
    @Override
    public Observable getPageAt(int page) {
        return Api.getInstance().service.getAllUser(C.PAGE_COUNT * (page - 1), C.PAGE_COUNT).compose(RxSchedulers.io_main());
    }
}
