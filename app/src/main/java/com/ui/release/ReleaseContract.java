package com.ui.release;

import com.base.BasePresenter;
import com.base.BaseView;

/**
 * Created by baixiaokang on 17/1/21.
 */

public interface ReleaseContract {
    interface View extends BaseView {
        void showMsg(String msg);

        void releaseSuc();
    }

    abstract class Presenter extends BasePresenter<ReleaseContract.View> {

        public abstract void upArticle(String url, String title, String content);

        @Override
        public void onAttached() {

        }

    }
}
