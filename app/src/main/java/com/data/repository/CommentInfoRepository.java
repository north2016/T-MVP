package com.data.repository;

import com.C;
import com.api.ApiFactory;
import com.base.util.ApiUtil;
import com.data.Repository;
import com.data.entity.CommentInfo;

import rx.Observable;

/**
 * Created by baixiaokang on 16/5/4.
 */
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
