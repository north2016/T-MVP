package com.ui.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.C;
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
        mXRecyclerView = new TRecyclerView(getContext());
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
        mXRecyclerView.getPresenter()
                .setRepository(ApiFactory::getAllImages)
                .setParam(C.TYPE, type)
                .fetch();
    }
}
