package com.ui.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.C;
import com.apt.ApiFactory;
import com.base.adapter.TRecyclerView;
import com.ui.main.R;


public class ArticlesFragment extends Fragment {
    private TRecyclerView mXRecyclerView;

    public static ArticlesFragment newInstance(String type) {
        Bundle arguments = new Bundle();
        arguments.putString(C.TYPE, type);
        ArticlesFragment fragment = new ArticlesFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mXRecyclerView = new TRecyclerView(getContext());
        mXRecyclerView.setViewType(R.layout.list_item_card_main);
        return mXRecyclerView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mXRecyclerView != null) {
            mXRecyclerView.getPresenter()
                    .setRepository(ApiFactory::getAllImages)
                    .setParam(C.TYPE, getArguments().getString(C.TYPE))
                    .fetch();
        }
    }
}
