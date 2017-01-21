package com.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.apt.TRouter;
import com.base.util.SpUtil;
import com.ui.main.R;
import com.view.widget.SwipeBackLayout;

public abstract class DataBindingActivity<B extends ViewDataBinding> extends AppCompatActivity {
    protected Toolbar toolbar;
    public Context mContext;
    public B mViewBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = getLayoutInflater().inflate(this.getLayoutId(), null, false);
        mViewBinding = DataBindingUtil.bind(rootView);
        this.setContentView(getLayoutId(), rootView);
        initTransitionView();
        TRouter.bind(this);
        mContext = this;
        initPresenter();
        initToolBar();
        initView();
    }

    protected void initPresenter() {
    }

    protected void initTransitionView() {//在这里给转场view副值
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void reload() {
        AppCompatDelegate.setDefaultNightMode(SpUtil.isNight() ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
        recreate();
    }

    public void setContentView(int layoutResID, View rootView) {
        boolean isNotSwipeBack = layoutResID == R.layout.activity_main || layoutResID == R.layout.activity_flash;
        super.setContentView(isNotSwipeBack ? rootView : getContainer(rootView));
    }

    private View getContainer(View rootView) {
        rootView.setBackgroundColor(getResources().getColor(R.color.alpha_white));
        View container = getLayoutInflater().inflate(R.layout.activity_base, null, false);
        SwipeBackLayout swipeBackLayout = (SwipeBackLayout) container.findViewById(R.id.swipeBackLayout);
        swipeBackLayout.setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        View ivShadow = container.findViewById(R.id.iv_shadow);
        swipeBackLayout.addView(rootView);
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
