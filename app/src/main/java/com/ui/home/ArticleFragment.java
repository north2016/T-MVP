package com.ui.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.C;
import com.DbFactory;
import com.EventTags;
import com.app.annotation.javassist.Bus;
import com.apt.ApiFactory;
import com.base.adapter.TRecyclerView;
import com.ui.main.R;


public class ArticleFragment extends Fragment {
    private TRecyclerView mXRecyclerView;
    private String type;

    public static ArticleFragment newInstance(String type) {
        Bundle arguments = new Bundle();
        arguments.putString(C.TYPE, type);
        ArticleFragment fragment = new ArticleFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mXRecyclerView = new TRecyclerView(getContext(),true);
        mXRecyclerView.setViewType(R.layout.list_item_card_main);
        return mXRecyclerView;
    }

    @Bus(EventTags.ON_RELEASE_OPEN)
    public void onRelease() {
        if (TextUtils.equals(type, C.OPEN_TYPE))
            mXRecyclerView.getPresenter().fetch();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        type = getArguments().getString(C.TYPE);
//        /**
//         * 测试多head type
//         */
//        mXRecyclerView.mCommAdapter.addHeadViewType(R.layout.list_item_comment_user, C.getAdminMsg());
//        mXRecyclerView.mCommAdapter.addHeadViewType(R.layout.list_item_comment_admin, C.getAdminMsg());
//        mXRecyclerView.mCommAdapter.addHeadViewType(R.layout.list_item_comment_admin, C.getAdminMsg());
//
//        /**
//         * 测试多foot type
//         */
//        mXRecyclerView.mCommAdapter.addFooterViewType(R.layout.list_item_comment_admin, C.getAdminMsg());
//        mXRecyclerView.mCommAdapter.addFooterViewType(R.layout.list_item_comment_user, C.getAdminMsg());
//        mXRecyclerView.mCommAdapter.addFooterViewType(R.layout.list_item_comment_admin, C.getAdminMsg());

        mXRecyclerView.getPresenter()
                .setDbRepository(DbFactory::getAllImages)
                .setNetRepository(ApiFactory::getAllImages)
                .setParam(C.TYPE, type)
                .fetch();

    }
}
