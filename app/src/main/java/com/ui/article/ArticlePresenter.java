package com.ui.article;


import com.C;
import com.DbFactory;
import com.app.annotation.apt.InstanceFactory;
import com.app.annotation.aspect.CheckLogin;
import com.apt.ApiFactory;
import com.base.adapter.AdapterPresenter;
import com.base.entity.Pointer;
import com.base.util.ApiUtil;
import com.google.gson.Gson;
import com.model.Comment;
import com.model.Image;
import com.model.ImageInfo;
import com.model._User;

/**
 * Created by baixiaokang on 16/5/4.
 */
@InstanceFactory
public class ArticlePresenter extends ArticleContract.Presenter {

    @CheckLogin
    public void createComment(String content, ImageInfo article, _User user) {
        mCompositeSubscription.add(
                ApiFactory
                        .createComment(
                                new Comment(
                                        ApiUtil.getPointer(Image.class.getSimpleName(), article),
                                        content,
                                        ApiUtil.getPointer(user)))
                        .subscribe(
                                res -> mView.commentSuc(),
                                e -> mView.commentFail())
        );
    }

    @Override
    public void initAdapterPresenter(AdapterPresenter mAdapterPresenter, ImageInfo mArticle) {
        String article = new Gson().toJson(new Pointer(Image.class.getSimpleName(), mArticle.objectId));
        mAdapterPresenter
                .setDbRepository(DbFactory::getCommentList)
                .setNetRepository(ApiFactory::getCommentList)
                .setParam(C.INCLUDE, C.CREATER)
                .setParam(C.ARTICLE, article)
                .setParam(C.OBJECT_ID, mArticle.objectId)
                .fetch();
    }
}


