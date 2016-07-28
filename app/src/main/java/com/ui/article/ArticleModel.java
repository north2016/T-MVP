package com.ui.article;


import com.api.Api;
import com.base.util.helper.RxSchedulers;
import com.data.CreatedResult;
import com.data.Pointer;
import com.data.entity.Comment;

import rx.Observable;

/**
 * Created by baixiaokang on 16/5/4.
 */
public class ArticleModel implements ArticleContract.Model {

    @Override
    public Observable<CreatedResult> createComment(String content, Pointer article, Pointer user) {
        return Api.getInstance().service
                .createComment(new Comment(article, content, user))
                .compose(RxSchedulers.io_main());
    }
}