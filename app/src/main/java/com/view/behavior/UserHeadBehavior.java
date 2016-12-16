package com.view.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.base.util.DensityUtil;

/**
 * Created by baixiaokang on 16/12/16.
 */

public class UserHeadBehavior extends CoordinatorLayout.Behavior<View> {

    public UserHeadBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    //tab向上向下移动是改变头像
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
//        //计算移动的比例 0~1
        float fraction = Math.abs(dependency.getY() + dependency.getHeight()) / DensityUtil.dip2px(200);
        child.setScaleX(fraction);
        child.setScaleY(fraction);
        child.setAlpha(fraction * fraction * fraction);
        float round = 100 * fraction;
        child.setY(dependency.getY() + dependency.getHeight() / 2 - DensityUtil.dip2px(round) / 2);
        return true;
    }
}