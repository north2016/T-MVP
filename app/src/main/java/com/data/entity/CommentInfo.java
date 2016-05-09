package com.data.entity;

import com.C;
import com.api.Api;
import com.base.util.ApiUtil;
import com.base.BaseEntity;
import com.base.util.helper.RxSchedulers;
import com.data.Data;

import rx.Observable;

/**
 * Created by baixiaokang on 16/5/4.
 */
public class CommentInfo extends BaseEntity.ListBean {
    public Image article;
    public String content;
    public _User creater;

    @Override
    public Observable<Data<CommentInfo>> getPageAt(int page) {
        return Api.getInstance().movieService
                .getCommentList(ApiUtil.getInclude(param), ApiUtil.getWhere(param), C.PAGE_COUNT * (page - 1), C.PAGE_COUNT)
                .compose(RxSchedulers.io_main());
    }
}
