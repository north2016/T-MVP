package com.data.entity;

import com.api.Api;
import com.C;
import com.base.util.ApiUtil;
import com.base.util.helper.RxSchedulers;
import com.data.Data;
import com.base.BaseEntity;

import rx.Observable;

/**
 * Created by Administrator on 2016/4/7.
 */
public class Image extends BaseEntity.ListBean {
    public String image;
    public String createdAt;
    public String article;
    public String author;
    public String title;
    public String type;

    @Override
    public Observable<Data<Image>> getPageAt(final int page) {
        return Api.getInstance().movieService
                .getAllImages(ApiUtil.getWhere(param),"-createdAt", C.PAGE_COUNT * (page - 1), C.PAGE_COUNT)
                .compose(RxSchedulers.io_main());
    }
}
