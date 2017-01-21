package com.ui.home;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.C;
import com.app.annotation.apt.Router;
import com.app.annotation.aspect.SingleClick;
import com.apt.TRouter;
import com.base.BaseActivity;
import com.base.entity.ExtraData;
import com.base.util.BindingUtils;
import com.base.util.SpUtil;
import com.base.util.helper.FragmentAdapter;
import com.base.util.helper.PagerChangeListener;
import com.model._User;
import com.ui.main.R;
import com.ui.main.TMVPFragment;
import com.ui.main.databinding.ActivityMainBinding;

import rx.Observable;

@Router(C.HOME)
public class HomeActivity extends BaseActivity<HomePresenter, ActivityMainBinding> implements HomeContract.View, NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public int getMenuId() {
        return R.menu.menu_overaction;
    }

    @Override
    public void onBackPressed() {
        if (mViewBinding.dlMainDrawer.isDrawerOpen(Gravity.LEFT))
            mViewBinding.dlMainDrawer.closeDrawers();
        else super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) TRouter.go(C.ABOUT);
        else if (item.getItemId() == R.id.action_feedback) TRouter.go(C.ADVISE);
        else if (item.getItemId() == R.id.action_about)
            TMVPFragment.getInstance().start(getSupportFragmentManager());
        else if (item.getItemId() == android.R.id.home)
            mViewBinding.dlMainDrawer.openDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void initView() {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mViewBinding.dlMainDrawer, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mViewBinding.dlMainDrawer.addDrawerListener(mDrawerToggle);
        mViewBinding.nvMainNavigation.setNavigationItemSelectedListener(this);
    }

    @Override
    public void showTabList(String[] mTabs) {
        Observable.from(mTabs).map(ArticleFragment::newInstance).toList()
                .map(fragments -> FragmentAdapter.newInstance(getSupportFragmentManager(), fragments, mTabs))
                .subscribe(mFragmentAdapter -> mViewBinding.viewpager.setAdapter(mFragmentAdapter));
        PagerChangeListener mPagerChangeListener = PagerChangeListener.newInstance(mViewBinding.collapsingToolbar, mViewBinding.toolbarIvTarget, mViewBinding.toolbarIvOutgoing);
        mViewBinding.viewpager.addOnPageChangeListener(mPagerChangeListener);
        mViewBinding.tabs.setupWithViewPager(mViewBinding.viewpager);
    }

    @Override
    public void initUserInfo(_User user) {
        View mHeaderView = mViewBinding.nvMainNavigation.getHeaderView(0);
        ImageView im_face = (ImageView) mHeaderView.findViewById(R.id.im_face);
        TextView tv_name = (TextView) mHeaderView.findViewById(R.id.tv_name);
        BindingUtils.loadRoundImg(im_face, user.face);
        tv_name.setText(user.username);
    }

    @Override
    public void onOpenRelease() {
        mViewBinding.viewpager.setCurrentItem(0);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_manage) TRouter.go(C.SETTING);
        else if (item.getItemId() == R.id.nav_share) TRouter.go(C.LOGIN);
        else if (item.getItemId() == R.id.nav_theme) SpUtil.setNight(mContext, !SpUtil.isNight());
        return true;
    }

    @SingleClick
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_face:
                TRouter.go(C.USER_INFO, new ExtraData(C.HEAD_DATA, SpUtil.getUser()).build(), v);
                break;
            case R.id.fab:
                TRouter.go(C.USER_RELEASE, new ExtraData(C.HEAD_DATA, SpUtil.getUser()).build(), v);
                break;
        }
    }
}
