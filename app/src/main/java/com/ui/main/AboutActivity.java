package com.ui.main;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.lv_user)
    TRecyclerView lv_user;
    @Bind(R.id.ll_header)
    LinearLayout llHeader;

    public static Double[] Lines = {0.0, 100.0, 200.0, 300.0, 400.0, 500.0};
    public static Double[] num0 = {200.0, 400.0, 230.0, 350.0, 210.0, 310.0, 350.0, 200.0};
    public static Double[] num1 = {400.0, 480.0, 300.0, 450.0, 310.0, 500.0, 450.0, 400.0};

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initView() {
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("用户列表");
        lv_user.setView(UserItemVH.class).setIsRefreshable(false).fetch();
        llHeader.addView(new ChartView(this, Lines, num0, num1));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}