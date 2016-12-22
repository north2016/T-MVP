package com.ui.main;

/**
 * Created by baixiaokang on 16/12/1.
 */

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import com.EventTags;
import com.api.Api;
import com.app.annotation.javassist.Bus;
import com.base.BaseActivity;
import com.base.OkBus;
import com.data.entity._User;
import com.view.widget.TabLayout;

import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * 简单页面无需mvp,该咋写还是咋写
 */
public class UserListActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tl_user)
    TabLayout tlUser;


    @Override
    public int getLayoutId() {
        return R.layout.activity_users;
    }

    @Override
    public void initView() {
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("用户一览");
        Api.getInstance().service
                .getAllUser(0, 1000)
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .flatMap(userData -> Observable.from(userData.results))
                .filter(user -> !TextUtils.isEmpty(user.face))
                .buffer(1000)
                .subscribe(
                        users -> OkBus.getInstance().onEvent(EventTags.ABOUT_INIT_USERS, users)//使用OkBus替换Rxjava的线程切换
                        , e -> e.printStackTrace());
    }

    @Bus(tag = EventTags.ABOUT_INIT_USERS, thread = Bus.UI)
    private void setUsers(List<_User> users) {
        tlUser.setM_Users(users);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}