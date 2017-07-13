package com.ui.main;

import android.view.animation.AlphaAnimation;

import com.C;
import com.EventTags;
import com.app.annotation.javassist.Bus;
import com.apt.TRouter;
import com.base.DataBindingActivity;
import com.base.event.OkBus;
import com.base.util.AnimationUtil;
import com.ui.main.databinding.ActivityFlashBinding;

/**
 * Created by baixiaokang on 16/4/28.
 */
public class FlashActivity extends DataBindingActivity<ActivityFlashBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_flash;
    }

    @Override
    public void initView() {
        OkBus.getInstance().onStickyEvent(EventTags.FLASH_INIT_UI);
    }

    @Bus(EventTags.FLASH_INIT_UI)
    public void initUI() {
        AlphaAnimation anim = new AlphaAnimation(0.8f, 0.1f);
        anim.setDuration(5000);
        mViewBinding.view.startAnimation(anim);
        AnimationUtil.setAnimationListener(anim, () -> OkBus.getInstance().onEvent(EventTags.JUMP_TO_MAIN));
    }

    @Bus(EventTags.JUMP_TO_MAIN)
    public void jumpToMainPage() {
        TRouter.go(C.HOME);
        finish();
    }
}
