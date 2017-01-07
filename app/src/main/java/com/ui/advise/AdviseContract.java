package com.ui.advise;

import com.base.BasePresenter;
import com.base.BaseView;

/**
 * Created by baixiaokang on 17/1/7.
 */

public interface AdviseContract {
    interface View extends BaseView {
        void sendSuc();

        void sendFail();
    }

    abstract class Presenter extends BasePresenter<AdviseContract.View> {
        public abstract void createMessage(String msg);

        @Override
        public void onAttached() {
        }
    }
}
