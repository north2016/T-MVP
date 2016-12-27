package com.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.C;
import com.base.BaseActivity;
import com.base.BaseListFragment;
import com.base.util.ImageUtil;
import com.base.util.SpUtil;
import com.base.util.ToastUtil;
import com.base.util.helper.FragmentAdapter;
import com.base.util.helper.PagerChangeListener;
import com.data.entity._User;
import com.ui.article.ArticleActivity;
import com.ui.login.LoginActivity;
import com.ui.main.AboutActivity;
import com.ui.main.FeedBackActivity;
import com.ui.main.R;
import com.ui.main.SettingsActivity;
import com.ui.main.TMVPFragment;
import com.ui.user.UserActivity;
import com.view.viewholder.ArticleItemVH;

import butterknife.Bind;
import rx.Observable;

public class HomeActivity extends BaseActivity<HomePresenter, HomeModel> implements HomeContract.View, NavigationView.OnNavigationItemSelectedListener {
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.nv_main_navigation)
    NavigationView nvMainNavigation;
    @Bind(R.id.dl_main_drawer)
    DrawerLayout dlMainDrawer;
    @Bind(R.id.toolbar_iv_outgoing)
    ImageView mIvOutgoing;
    @Bind(R.id.toolbar_iv_target)
    ImageView mIvTarget;
    ImageView im_face, im_bg;
    TextView tv_name;
    PagerChangeListener mPagerChangeListener;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

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
        if (dlMainDrawer.isDrawerOpen(Gravity.LEFT)) dlMainDrawer.closeDrawers();
        else super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings)
            startActivity(new Intent(mContext, AboutActivity.class));
        else if (item.getItemId() == R.id.action_feedback)
            TMVPFragment.getInstance().start(getSupportFragmentManager());
        else if (item.getItemId() == android.R.id.home)
            dlMainDrawer.openDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void initView() {
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, dlMainDrawer, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        dlMainDrawer.addDrawerListener(mDrawerToggle);
        fab.setOnClickListener(v -> {
            if (SpUtil.getUser() == null) ToastUtil.show("Not Login!!!");
            else startActivity(new Intent(this, FeedBackActivity.class));
        });
        View mHeaderView = nvMainNavigation.getHeaderView(0);
        im_face = (ImageView) mHeaderView.findViewById(R.id.im_face);
        im_bg = (ImageView) mHeaderView.findViewById(R.id.im_bg);
        tv_name = (TextView) mHeaderView.findViewById(R.id.tv_name);
        nvMainNavigation.setNavigationItemSelectedListener(this);
    }

    @Override
    public void showTabList(String[] mTabs) {
        Observable.from(mTabs)
                .map(tab -> BaseListFragment.newInstance(ArticleItemVH.class, tab)).toList()
                .map(fragments -> FragmentAdapter.newInstance(getSupportFragmentManager(), fragments, mTabs))
                .subscribe(mFragmentAdapter -> viewpager.setAdapter(mFragmentAdapter));
        mPagerChangeListener = PagerChangeListener.newInstance(collapsingToolbar, mIvTarget, mIvOutgoing);
        viewpager.addOnPageChangeListener(mPagerChangeListener);
        tabs.setupWithViewPager(viewpager);
    }

    @Override
    public void initUserInfo(_User user) {
        ImageUtil.loadRoundAndBgImg(im_face, user.face, im_bg);
        tv_name.setText(user.username);
        im_face.setOnClickListener(v ->
                ActivityCompat.startActivity(mContext, new Intent(mContext, UserActivity.class).putExtra(C.HEAD_DATA, user)
                        , ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, im_face, ArticleActivity.TRANSLATE_VIEW).toBundle())
        );
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        item.setChecked(true);
        dlMainDrawer.closeDrawers();
        if (item.getItemId() == R.id.nav_manage)
            startActivity(new Intent(mContext, SettingsActivity.class));
        else if (item.getItemId() == R.id.nav_share)
            startActivity(new Intent(mContext, LoginActivity.class));
        else if (item.getItemId() == R.id.nav_send) SpUtil.setNight(mContext, !SpUtil.isNight());
        return true;
    }
}
