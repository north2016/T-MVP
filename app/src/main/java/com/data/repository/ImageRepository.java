package com.data.repository;

import com.C;
import com.app.annotation.apt.InstanceFactory;
import com.apt.ApiFactory;
import com.base.util.ApiUtil;
import com.data.Repository;
import com.data.entity.Image;

import rx.Observable;

/**
 * Created by baixiaokang on 16/7/19.
 */
@InstanceFactory
public class ImageRepository extends Repository<Image> {

    @Override
    public Observable getPageAt(final int page) {
        return ApiFactory.getAllImages(
                ApiUtil.getWhere(param),
                C._CREATED_AT,
                C.PAGE_COUNT * (page - 1),
                C.PAGE_COUNT);
    }
}
