package com.data.repository;

import com.C;
import com.api.Api;
import com.base.util.ApiUtil;
import com.base.util.helper.RxSchedulers;
import com.data.Repository;
import com.data.entity.MessageInfo;

import rx.Observable;

/**
 * Created by baixiaokang on 16/12/24.
 */

public class MessageInfoRepository extends Repository<MessageInfo> {

    @Override
    public Observable getPageAt(int page) {
        return Api.getInstance().service
                .getMessageList(
                        ApiUtil.getInclude(param),
                        ApiUtil.getWhere(param),
                        C.PAGE_COUNT * (page - 1),
                        C.PAGE_COUNT, "-createdAt")
                .compose(RxSchedulers.io_main());
    }
}