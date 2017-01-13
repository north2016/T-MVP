package com.ui.article;

import com.base.BasePresenter;
import com.base.BaseView;
import com.base.adapter.AdapterPresenter;
import com.model.Image;
import com.model._User;


/**
 * Created by baixiaokang on 16/4/22.
 */
public interface ArticleContract {

    interface View extends BaseView {
        void commentSuc();

        void commentFail();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void createComment(String content, Image article, _User user);

        public abstract void initAdapterPresenter(AdapterPresenter mAdapterPresenter, Image article);

        @Override
        public void onAttached() {
        }
    }
}

