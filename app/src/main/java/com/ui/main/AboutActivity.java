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


    public static String[] pricesLines = {"0", "100", "200", "300", "400", "500"};
    public static String[] prices0 = {"80", "220", "350", "450", "210", "100", "350", "500"};
    public static String[] prices10 = {"90", "290", "450", "250", "310", "200", "150", "400"};

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
        lv_user.setView(UserItemVH.class).fetch();
        llHeader.addView(new ChartView(this, pricesLines, prices0, prices10));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}