package com.base.util.helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ui.home.ArticlesFragment;

import java.util.List;

/**
 * Created by baixiaokang on 16/5/8.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<ArticlesFragment> mFragments;
    private String[] mTitles;

    public static FragmentAdapter newInstance(FragmentManager fm, List<ArticlesFragment> fragments, String[] titles) {
        FragmentAdapter mFragmentAdapter = new FragmentAdapter(fm);
        mFragmentAdapter.mFragments = fragments;
        mFragmentAdapter.mTitles = titles;
        return mFragmentAdapter;
    }

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}