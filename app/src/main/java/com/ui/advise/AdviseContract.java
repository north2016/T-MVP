package com.ui.advise;

import com.base.BasePresenter;
import com.base.BaseView;
import com.base.adapter.AdapterPresenter;

/**
 * Created by baixiaokang on 17/1/7.
 */

public interface AdviseContract {
    interface View extends BaseView {
        void sendSuc();

        void showMsg(String msg);
    }

    abstract class Presenter extends BasePresenter<AdviseContract.View> {
        public abstract void createMessage(String msg);

        public abstract void initAdapterPresenter(AdapterPresenter mAdapterPresenter);

        @Override
        public void onAttached() {
        }
    }
}
