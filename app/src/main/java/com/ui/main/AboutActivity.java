package com.ui.main;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.base.BaseActivity;
import com.view.layout.TRecyclerView;
import com.view.viewholder.UserItemVH;

import butterknife.Bind;

/**
 * 简单页面无需mvp,该咋写还是咋写
 */
public class AboutActivity extends BaseActivity {

    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.lv_user)
    TRecyclerView lv_user;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initView() {
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("");
        fab.setOnClickListener(v -> {});
        lv_user.setView(UserItemVH.class).fetch();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

   /* @Override
    public void initPresenter() {

    }*/
}