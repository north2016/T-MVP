package com.data.repository;

import com.C;
import com.app.annotation.apt.InstanceFactory;
import com.apt.ApiFactory;
import com.base.util.ApiUtil;
import com.data.Repository;
import com.data.entity.CommentInfo;

import rx.Observable;

/**
 * Created by baixiaokang on 16/5/4.
 */
@InstanceFactory
public class CommentInfoRepository extends Repository<CommentInfo> {

    @Override
    public Observable getPageAt(int page) {
        return ApiFactory.getCommentList(
                ApiUtil.getInclude(param),
                ApiUtil.getWhere(param),
                C.PAGE_COUNT * (page - 1),
                C.PAGE_COUNT);
    }
}
