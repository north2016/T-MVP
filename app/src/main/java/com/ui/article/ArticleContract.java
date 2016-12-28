package com.ui.article;

import com.base.BasePresenter;
import com.base.BaseView;
import com.data.entity.Image;
import com.data.entity._User;


/**
 * Created by baixiaokang on 16/4/22.
 */
public interface ArticleContract {

    interface View extends BaseView {
        void commentSuc();

        void commentFail();

        void showLoginAction();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void createComment(String content, Image article, _User user);

        @Override
        public void onAttached() {
        }
    }
}

