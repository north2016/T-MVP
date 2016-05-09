package com.base.util;

import android.view.animation.Animation;

public class AnimationUtil {

    public static void setAnimationListener(Animation aninm, final AnimListener listener) {
        aninm.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                listener.onAnimFinish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    public interface AnimListener {
        void onAnimFinish();
    }
}
