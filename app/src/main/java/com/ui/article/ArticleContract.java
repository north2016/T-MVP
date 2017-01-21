package com.ui.article;

import com.base.BasePresenter;
import com.base.BaseView;
import com.base.adapter.AdapterPresenter;
import com.model.ImageInfo;
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
        public abstract void createComment(String content, ImageInfo article, _User user);

        public abstract void initAdapterPresenter(AdapterPresenter mAdapterPresenter, ImageInfo article);

        @Override
        public void onAttached() {
        }
    }
}

