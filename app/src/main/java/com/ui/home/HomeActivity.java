package com.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.C;
import com.base.BaseActivity;
import com.base.BaseListFragment;
import com.base.util.ImageUtil;
import com.base.util.SpUtil;
import com.base.util.StatusBarUtil;
import com.base.util.helper.FragmentAdapter;
import com.base.util.helper.PagerChangeListener;
import com.data.entity._User;
import com.ui.article.ArticleActivity;
import com.ui.login.LoginActivity;
import com.ui.main.AboutActivity;
import com.ui.main.R;
import com.ui.main.SettingsActivity;
import com.ui.main.UserListActivity;
import com.ui.user.UserActivity;
import com.view.viewholder.ArticleItemVH;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import rx.Observable;

public class HomeActivity extends BaseActivity<HomePresenter, HomeModel> implements HomeContract.View, NavigationView.OnNavigationItemSelectedListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
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
    ImageView im_face;
    TextView tv_name;
    PagerChangeListener mPagerChangeListener;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_overaction, menu);
        return true;
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
        else if (item.getItemId() == android.R.id.home)
            dlMainDrawer.openDrawer(GravityCompat.START);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void initView() {
        StatusBarUtil.setTranslucentBackground(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, dlMainDrawer, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        dlMainDrawer.addDrawerListener(mDrawerToggle);
        fab.setOnClickListener(v -> startActivity(new Intent(this, UserListActivity.class)));
        View headerView = nvMainNavigation.inflateHeaderView(R.layout.nav_header_main);
        im_face = (ImageView) headerView.findViewById(R.id.im_face);
        tv_name = (TextView) headerView.findViewById(R.id.tv_name);
        nvMainNavigation.setNavigationItemSelectedListener(this);
    }

    @Override
    public void showTabList(String[] mTabs) {
        List<Fragment> fragments = new ArrayList<>();
        Observable.from(mTabs).subscribe(tab -> fragments.add(BaseListFragment.newInstance(ArticleItemVH.class, tab)));
        FragmentAdapter mFragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, Arrays.asList(mTabs));
        viewpager.setAdapter(mFragmentAdapter);
        mPagerChangeListener = PagerChangeListener.newInstance(mFragmentAdapter, mIvTarget, mIvOutgoing);
        viewpager.addOnPageChangeListener(mPagerChangeListener);
        tabs.setupWithViewPager(viewpager);
    }

    @Override
    public void initUserInfo(_User user) {
        ImageUtil.loadRoundImg(im_face, user.face);
        tv_name.setText(user.username);
        im_face.setOnClickListener(v ->
                ActivityCompat.startActivity(mContext, new Intent(mContext, UserActivity.class).putExtra(C.HEAD_DATA, user)
                        , ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, im_face, ArticleActivity.TRANSLATE_VIEW).toBundle())
        );
    }

    @Override
    protected void onDestroy() {
        viewpager.removeOnPageChangeListener(mPagerChangeListener);
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        item.setChecked(true);
        dlMainDrawer.closeDrawers();
        if (item.getItemId() == R.id.nav_manage)
            startActivity(new Intent(mContext, SettingsActivity.class));
        else if (item.getItemId() == R.id.nav_share)
            startActivity(new Intent(mContext, LoginActivity.class));
        else if (item.getItemId() == R.id.nav_send)
            SpUtil.setNight(mContext, !SpUtil.isNight());
        return true;
    }
}
