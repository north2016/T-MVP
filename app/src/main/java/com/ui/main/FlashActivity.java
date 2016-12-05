package com.ui.main;

import android.content.Intent;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.EventTags;
import com.app.annotation.javassist.Bus;
import com.base.BaseActivity;
import com.base.OkBus;
import com.base.util.AnimationUtil;
import com.base.util.StatusBarUtil;
import com.ui.home.HomeActivity;

import butterknife.Bind;

/**
 * Created by baixiaokang on 16/4/28.
 */
public class FlashActivity extends BaseActivity {

    @Bind(R.id.view)
    View view;

    @Override
    public int getLayoutId() {
        return R.layout.activity_flash;
    }

    @Override
    public void initView() {
        OkBus.getInstance().onStickyEvent(EventTags.FLASH_INIT_UI, null);
    }

    @Bus(tag = EventTags.FLASH_INIT_UI)
    public void initUI() {
        StatusBarUtil.setTranslucentBackground(this);
        AlphaAnimation anim = new AlphaAnimation(0.8f, 0.1f);
        anim.setDuration(5000);
        view.startAnimation(anim);
        AnimationUtil.setAnimationListener(anim, () -> {
            OkBus.getInstance().onEvent(EventTags.JUMP_TO_MAIN, null);
        });
    }

    @Bus(tag = EventTags.JUMP_TO_MAIN)
    public void jumpToMainPage() {
        startActivity(new Intent(mContext, HomeActivity.class));
        finish();
    }
}
