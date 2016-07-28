package com.data.repository;

import com.C;
import com.api.Api;
import com.data.Repository;
import com.base.util.ApiUtil;
import com.base.util.helper.RxSchedulers;
import com.data.Data;
import com.data.entity.Image;

import rx.Observable;

/**
 * Created by baixiaokang on 16/7/19.
 */
public class ImageRepository extends Repository<Image> {

    @Override
    public Observable<Data<Image>> getPageAt(final int page) {
        return Api.getInstance().service
                .getAllImages(ApiUtil.getWhere(param), "-createdAt", C.PAGE_COUNT * (page - 1), C.PAGE_COUNT)
                .compose(RxSchedulers.io_main());
    }
}
