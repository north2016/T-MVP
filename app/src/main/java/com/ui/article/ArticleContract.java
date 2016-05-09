package com.ui.article;

import com.base.BaseModel;
import com.base.BasePresenter;
import com.base.BaseView;
import com.data.Pointer;
import com.data.entity.Image;
import com.data.entity._User;

import rx.Observable;


/**
 * Created by baixiaokang on 16/4/22.
 */
public interface ArticleContract {
    interface Model extends BaseModel {
        Observable createComment(String content, Pointer article, Pointer user);
    }


    interface View extends BaseView {
        void commentSuc();
        void commentFail();
        void showLoginAction();
    }

    abstract class Presenter extends BasePresenter<Model, View> {
        public abstract void createComment(String content, Image article, _User user);
        @Override
        public void onStart() {}
    }
}

