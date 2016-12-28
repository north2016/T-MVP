package com.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.base.util.InstanceUtil;
import com.base.util.SpUtil;
import com.ui.main.R;
import com.view.layout.SwipeBackLayout;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/4/5.
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    protected Toolbar toolbar;
    public boolean isNight;
    public P mPresenter;
    public Context mContext;
    private SwipeBackLayout swipeBackLayout;
    private ImageView ivShadow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNight = SpUtil.isNight();
        setTheme(isNight ? R.style.AppThemeNight : R.style.AppThemeDay);
        this.setContentView(this.getLayoutId());
        ButterKnife.bind(this);
        mContext = this;
        initToolBar();
        initView();
        if (this instanceof BaseView) {
            mPresenter = InstanceUtil.getInstance(this, 0);
            mPresenter.setView(this);
        }
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDetached();
        ButterKnife.unbind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNight != SpUtil.isNight()) reload();
    }

    public void reload() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    @Override
    public void setContentView(int layoutResID) {
        if (layoutResID == R.layout.activity_main || layoutResID == R.layout.activity_flash) {
            super.setContentView(layoutResID);
        } else {
            super.setContentView(getContainer());
            View view = LayoutInflater.from(this).inflate(layoutResID, null);
            view.setBackgroundColor(getResources().getColor(R.color.alpha_white));
            swipeBackLayout.addView(view);
        }
    }

    private View getContainer() {
        RelativeLayout container = new RelativeLayout(this);
        swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        ivShadow = new ImageView(this);
        ivShadow.setBackgroundColor(getResources().getColor(R.color.theme_black_7f));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        container.addView(ivShadow, params);
        container.addView(swipeBackLayout);
        swipeBackLayout.setOnSwipeBackListener((fa, fs) -> ivShadow.setAlpha(1 - fs));
        return container;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (-1 != getMenuId()) getMenuInflater().inflate(getMenuId(), menu);
        return true;
    }

    public int getMenuId() {
        return -1;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    public abstract int getLayoutId();

    public abstract void initView();
}
