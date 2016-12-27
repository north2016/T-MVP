package com.ui.main;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.widget.LinearLayout;

import com.base.BaseActivity;
import com.view.layout.TRecyclerView;
import com.view.viewholder.UserItemVH;
import com.view.widget.ChartView;

import butterknife.Bind;

/**
 * 简单页面无需mvp,该咋写还是咋写
 */
public class AboutActivity extends BaseActivity {


    @Bind(R.id.lv_user)
    TRecyclerView lv_user;
    @Bind(R.id.ll_header)
    LinearLayout llHeader;

    public Double[] Lines = {0.0, 100.0, 200.0, 300.0, 400.0, 500.0};
    public Double[] num0 = {200.0, 400.0, 230.0, 350.0, 210.0, 310.0, 350.0, 200.0};
    public Double[] num1 = {400.0, 480.0, 300.0, 450.0, 310.0, 500.0, 450.0, 400.0};
    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initView() {
        setTitle("用户列表");
        fab.setOnClickListener(v -> startActivity(new Intent(this, UserListActivity.class)));
        lv_user.setView(UserItemVH.class).setIsRefreshable(false).fetch();
        llHeader.addView(new ChartView(this, Lines, num0, num1));
    }
}