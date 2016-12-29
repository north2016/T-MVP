package com.data.repository;

import com.C;
import com.apt.ApiFactory;
import com.base.util.ApiUtil;
import com.data.Repository;
import com.data.entity.MessageInfo;

import rx.Observable;

/**
 * Created by baixiaokang on 16/12/24.
 */

public class MessageInfoRepository extends Repository<MessageInfo> {

    @Override
    public Observable getPageAt(int page) {
        return ApiFactory.getMessageList(
                ApiUtil.getInclude(param),
                ApiUtil.getWhere(param),
                C.PAGE_COUNT * (page - 1),
                C.PAGE_COUNT, C._CREATED_AT);
    }
}