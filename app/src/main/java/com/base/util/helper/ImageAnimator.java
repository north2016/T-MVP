package com.base.util.helper;

import android.content.res.Resources;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.App;
import com.base.util.BaseUtils;
import com.base.util.BindingUtils;
import com.base.util.SpUtil;
import com.ui.main.R;

public class ImageAnimator {
    private String[] mImages = {
            "http://img-cdn.luoo.net/pics/essays/201701/Ftwd9K_sxJFVZyy2bCtEtWlyF5Le.jpg",
            "http://img-cdn.luoo.net/pics/vol/585800ce78e88.jpg?imageView2/1/w/640/h/452",
            "http://img-cdn.luoo.net/pics/vol/58138dab67978.jpg?imageView2/1/w/640/h/452",
            "http://img-cdn.luoo.net/pics/vol/5838629f22305.jpg?imageView2/1/w/640/h/452",
            "http://img-cdn.luoo.net/pics/vol/583b045d826cb.jpg?imageView2/1/w/640/h/452",
            "http://img-cdn.luoo.net/pics/vol/5736055312e75.jpg?imageView2/1/w/640/h/452",
            "http://img-cdn.luoo.net/pics/vol/5824406291aef.jpg?imageView2/1/w/640/h/452",
            "http://img-cdn.luoo.net/pics/vol/56cf4715dd2fa.jpg?imageView2/1/w/640/h/452",
            "http://img-cdn.luoo.net/pics/vol/57e2c6fd714e2.jpg?imageView2/1/w/640/h/452",
            "http://img-cdn.luoo.net/pics/vol/579255f04b1da.jpg?imageView2/1/w/640/h/452",
            "http://img-cdn.luoo.net/pics/vol/581b681b678f1.jpg?imageView2/1/w/640/h/452"};

    private int[] mColors;
    private static final float FACTOR = 0.1f;

    private final ImageView mTargetImage; // 原始图片
    private final ImageView mOutgoingImage; // 渐变图片

    private int mActualStart; // 实际起始位置

    private int mStart;
    private int mEnd;

    private boolean isSkip = false;//是否跳页
    private CollapsingToolbarLayout collapsingToolbar;

    ImageAnimator(CollapsingToolbarLayout collapsingToolbar, ImageView targetImage, ImageView outgoingImage) {
        this.collapsingToolbar = collapsingToolbar;
        mTargetImage = targetImage;
        mOutgoingImage = outgoingImage;
        BindingUtils.loadImg(mTargetImage, mImages[0]);
        Resources mResources = App.getAppContext().getResources();
        int[] mColorDay = mResources.getIntArray(R.array.day_color_tab);
        int[] mColorNight = mResources.getIntArray(R.array.night_color_tab);
        mColors = SpUtil.isNight() ? mColorNight : mColorDay;
        collapsingToolbar.setContentScrimColor(mColors[0]);
        collapsingToolbar.setStatusBarScrimColor(mColors[0]);
    }

    /**
     * 启动动画, 之后选择向前或向后滑动
     *
     * @param startPosition 起始位置
     * @param endPosition   终止位置
     */
    public void start(int startPosition, int endPosition) {
        if (Math.abs(endPosition - startPosition) > 1) {
            isSkip = true;
        }
        mActualStart = startPosition;
        Log.e("DEBUG", "startPosition: " + startPosition + ", endPosition: " + endPosition);
        // 终止位置的图片

        //@DrawableRes int incomeId = ids[endPosition % ids.length];

        // 原始图片
        mOutgoingImage.setImageDrawable(mTargetImage.getDrawable()); // 原始的图片

        // 起始图片
        mOutgoingImage.setTranslationX(0f);

        mOutgoingImage.setVisibility(View.VISIBLE);
        mOutgoingImage.setAlpha(1.0f);

        // 目标图片
        //   mTargetImage.setImageResource(incomeId);
        BindingUtils.loadImg(mTargetImage, mImages[endPosition]);
        mStart = Math.min(startPosition, endPosition);
        mEnd = Math.max(startPosition, endPosition);
    }

    /**
     * 滑动结束的动画效果
     *
     * @param endPosition 滑动位置
     */
    public void end(int endPosition) {
        isSkip = false;
        //@DrawableRes int incomeId = ids[endPosition % ids.length];
        Log.e("DEBUG", "endPosition: " + endPosition);
        mTargetImage.setTranslationX(0f);

        // 设置原始图片
        if (endPosition == mActualStart) {
            mTargetImage.setImageDrawable(mOutgoingImage.getDrawable());
        } else {
            BindingUtils.loadImg(mTargetImage, mImages[endPosition]);
            collapsingToolbar.setContentScrimColor(mColors[endPosition]);
            collapsingToolbar.setStatusBarScrimColor(mColors[endPosition]);
            //mTargetImage.setImageResource(incomeId);
            mTargetImage.setAlpha(1f);
            mOutgoingImage.setVisibility(View.GONE);
        }
    }

    // 向前滚动, 比如0->1, offset滚动的距离(0->1), 目标渐渐淡出
    void forward(int position, float positionOffset) {
        if (isSkip) return;
        // Log.e("DEBUG-WCL", "forward-positionOffset: " + positionOffset);
        int width = mTargetImage.getWidth();
        mOutgoingImage.setTranslationX(-positionOffset * (FACTOR * width));
        mTargetImage.setTranslationX((1 - positionOffset) * (FACTOR * width));
        int color = BaseUtils.evaluate(positionOffset, mColors[position], mColors[position + 1]);
        collapsingToolbar.setContentScrimColor(color);
        collapsingToolbar.setStatusBarScrimColor(color);
        mTargetImage.setAlpha(positionOffset);
    }

    // 向后滚动, 比如1->0, offset滚动的距离(1->0), 目标渐渐淡入
    void backwards(int position, float positionOffset) {
        if (isSkip) return;
        // Log.e("DEBUG-WCL", "backwards-positionOffset: " + positionOffset);
        int width = mTargetImage.getWidth();
        mOutgoingImage.setTranslationX((1 - positionOffset) * (FACTOR * width));
        mTargetImage.setTranslationX(-(positionOffset) * (FACTOR * width));

        int color = BaseUtils.evaluate(1 - positionOffset, mColors[position + 1], mColors[position]);
        collapsingToolbar.setContentScrimColor(color);
        collapsingToolbar.setStatusBarScrimColor(color);
        mTargetImage.setAlpha(1 - positionOffset);
    }

    // 判断停止
    boolean isWithin(int position) {
        return position >= mStart && position < mEnd;
    }
}
