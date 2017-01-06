package com.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.C;
import com.data.repository.ImageRepository;
import com.view.layout.TRecyclerView;


public class BaseListFragment extends Fragment {
    private TRecyclerView mXRecyclerView;

    public static BaseListFragment newInstance(@LayoutRes int res, String type) {
        Bundle arguments = new Bundle();
        arguments.putInt(C.VH_CLASS, res);
        arguments.putString("type", type);
        BaseListFragment fragment = new BaseListFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mXRecyclerView = new TRecyclerView(getContext()).setParam("type", getArguments().getString("type"))
                .setView(getArguments().getInt(C.VH_CLASS), ImageRepository.class);
        return mXRecyclerView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mXRecyclerView != null) mXRecyclerView.fetch();
    }
}
