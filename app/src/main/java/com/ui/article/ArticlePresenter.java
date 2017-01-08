package com.ui.article;


import com.C;
import com.app.annotation.apt.InstanceFactory;
import com.apt.ApiFactory;
import com.base.adapter.AdapterPresenter;
import com.base.entity.Pointer;
import com.base.util.ApiUtil;
import com.base.util.SpUtil;
import com.google.gson.Gson;
import com.model.Comment;
import com.model.Image;
import com.model._User;

/**
 * Created by baixiaokang on 16/5/4.
 */
@InstanceFactory
public class ArticlePresenter extends ArticleContract.Presenter {

    @Override
    public void createComment(String content, Image article, _User user) {
        if (null == SpUtil.getUser())
            mView.showLoginAction();
        else
            mCompositeSubscription.add(
                    ApiFactory
                            .createComment(
                                    new Comment(
                                            ApiUtil.getPointer(article),
                                            content,
                                            ApiUtil.getPointer(user)))
                            .subscribe(
                                    res -> mView.commentSuc(),
                                    e -> mView.commentFail())
            );
    }

    @Override
    public void initAdapterPresenter(AdapterPresenter mAdapterPresenter,Image mArticle) {
        String article = new Gson().toJson(new Pointer(Image.class.getSimpleName(), mArticle.objectId));
        mAdapterPresenter.setRepository(ApiFactory::getCommentList)
                .setParam(C.INCLUDE, C.CREATER)
                .setParam(C.ARTICLE, article)
                .fetch();
    }
}


