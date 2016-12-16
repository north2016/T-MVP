package com.view.behavior;

import android.animation.Animator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Interpolator;

import java.util.List;


/**
 * Created by baixiaokang on 16/12/15.
 */

public class MyCommentBehavior extends CoordinatorLayout.Behavior<View> {

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    private float viewY;//控件距离coordinatorLayout底部距离
    private boolean isAnimate;//动画是否在进行

    public MyCommentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    //确定所提供的子视图是否有另一个特定的同级视图作为布局从属。
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
//这个方法是说明这个子控件是依赖Snackbar.SnackbarLayout的
        return dependency instanceof Snackbar.SnackbarLayout;
    }


    private float getFabTranslationYForSnackbar(CoordinatorLayout parent,
                                                View fab) {
        float minOffset = 0;
        final List<View> dependencies = parent.getDependencies(fab);
        for (int i = 0, z = dependencies.size(); i < z; i++) {
            final View view = dependencies.get(i);
            if (view instanceof Snackbar.SnackbarLayout && parent.doViewsOverlap(fab, view)) {
                minOffset = Math.min(minOffset,
                        ViewCompat.getTranslationY(view) - view.getHeight());
            }
        }

        return minOffset;
    }

    float mFabTranslationY = 0;

    //用于响应从属布局的变化
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View snackbar) {

        if (child.getVisibility() != View.VISIBLE) {
            return false;
        }

        final float targetTransY = getFabTranslationYForSnackbar(parent, child);

        if (mFabTranslationY == targetTransY) {
            // We're already at (or currently animating to) the target value, return...
            return false;
        }

        mFabTranslationY = targetTransY;
        final float currentTransY = ViewCompat.getTranslationY(child);
        final float dy = currentTransY - targetTransY;

        if (Math.abs(dy) > (child.getHeight() * 0.667f)) {
            // If the FAB will be travelling by more than 2/3 of it's height, let's animate
            // it instead
            ViewCompat.animate(child)
                    .translationY(targetTransY)
                    .scaleX(1f)
                    .scaleY(1f)
                    .alpha(1f)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .setListener(null);
        } else {
            // Make sure that any current animation is cancelled
            ViewCompat.animate(child).cancel();
            // Now update the translation Y
            ViewCompat.setTranslationY(child, targetTransY);
        }
        return true;
    }

    private boolean isHided = false;

    //在嵌套滑动开始前回调
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        if (!isHided) {
            isHided = true;
            ViewCompat.setTranslationY(child, child.getHeight());
        }
        if (child.getVisibility() == View.VISIBLE && viewY == 0) {
            //获取控件距离父布局（coordinatorLayout）底部距离
            viewY = coordinatorLayout.getHeight() - child.getY();
        }
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;//判断是否竖直滚动
    }

    //在嵌套滑动进行时，对象消费滚动距离前回调
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        //dy大于0是向上滚动 小于0是向下滚动
        if (dy >= 10 && !isAnimate && child.getVisibility() == View.GONE) {
            show(child);
        } else if (dy < -10 && !isAnimate && child.getVisibility() == View.VISIBLE) {
            hide(child);
        }
    }

    //隐藏时的动画
    private void hide(final View view) {
        ViewPropertyAnimator animator = view.animate().translationY(viewY).setInterpolator(INTERPOLATOR).setDuration(200);

        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                isAnimate = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.GONE);
                isAnimate = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                show(view);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        animator.start();
    }

    //显示时的动画
    private void show(final View view) {
        ViewPropertyAnimator animator = view.animate().translationY(0).setInterpolator(INTERPOLATOR).setDuration(200);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                view.setVisibility(View.VISIBLE);
                isAnimate = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                isAnimate = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                hide(view);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        animator.start();
    }
}