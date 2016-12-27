package com.base.util.helper;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

/**
 * ViewPager滑动页面监听
 * <p/>
 * Created by wangchenlong on 15/11/9.
 */
public class PagerChangeListener implements ViewPager.OnPageChangeListener {
    private ImageAnimator mImageAnimator;

    private int mCurrentPosition;

    private int mFinalPosition;

    private boolean mIsScrolling = false;

    public PagerChangeListener(ImageAnimator imageAnimator) {
        mImageAnimator = imageAnimator;
    }

    public static PagerChangeListener newInstance(CollapsingToolbarLayout collapsingToolbar, ImageView originImage, ImageView outgoingImage) {
        ImageAnimator imageAnimator = new ImageAnimator(collapsingToolbar, originImage, outgoingImage);
        return new PagerChangeListener(imageAnimator);
    }

    /**
     * 滑动监听
     *
     * @param position             当前位置
     * @param positionOffset       偏移[当前值+-1]
     * @param positionOffsetPixels 偏移像素
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        //Log.e("DEBUG-WCL", "position: " + position + ", positionOffset: " + positionOffset);

        // 以前滑动, 现在终止
        if (isFinishedScrolling(position, positionOffset)) {
            finishScroll(position);
        }

        // 判断前后滑动
        if (isStartingScrollToPrevious(position, positionOffset)) {
            startScroll(position);
        } else if (isStartingScrollToNext(position, positionOffset)) {
            startScroll(position + 1); // 向后滚动需要加1
        }

        // 向后滚动
        if (isScrollingToNext(position, positionOffset)) {
            mImageAnimator.forward(position, positionOffset);
        } else if (isScrollingToPrevious(position, positionOffset)) { // 向前滚动
            mImageAnimator.backwards(position, positionOffset);
        }
    }

    /**
     * 终止滑动
     * 滑动 && [偏移是0&&滑动终点] || 动画之中
     *
     * @param position       位置
     * @param positionOffset 偏移量
     * @return 终止滑动
     */
    public boolean isFinishedScrolling(int position, float positionOffset) {
        return mIsScrolling && (positionOffset == 0f && position == mFinalPosition) || !mImageAnimator.isWithin(position);
    }

    /**
     * 从静止到开始滑动, 下一个
     * 未滑动 && 位置是当前位置 && 偏移量不是0
     *
     * @param position       位置
     * @param positionOffset 偏移量
     * @return 是否
     */
    private boolean isStartingScrollToNext(int position, float positionOffset) {
        return !mIsScrolling && position == mCurrentPosition && positionOffset != 0f;
    }

    /**
     * 从静止到开始滑动, 前一个[position会-1]
     *
     * @param position       位置
     * @param positionOffset 偏移量
     * @return 是否
     */
    private boolean isStartingScrollToPrevious(int position, float positionOffset) {
        return !mIsScrolling && position != mCurrentPosition && positionOffset != 0f;
    }

    /**
     * 开始滚动, 向后
     *
     * @param position       位置
     * @param positionOffset 偏移
     * @return 是否
     */
    private boolean isScrollingToNext(int position, float positionOffset) {
        return mIsScrolling && position == mCurrentPosition && positionOffset != 0f;
    }

    /**
     * 开始滚动, 向前
     *
     * @param position       位置
     * @param positionOffset 偏移
     * @return 是否
     */
    private boolean isScrollingToPrevious(int position, float positionOffset) {
        return mIsScrolling && position != mCurrentPosition && positionOffset != 0f;
    }

    /**
     * 开始滑动
     * 滚动开始, 结束位置是position[前滚时position会自动减一], 动画从当前位置到结束位置.
     *
     * @param position 滚动结束之后的位置
     */
    private void startScroll(int position) {
        mIsScrolling = true;
        mFinalPosition = position;

        // 开始滚动动画
        mImageAnimator.start(mCurrentPosition, position);
    }

    /**
     * 如果正在滚动, 结束时, 固定position位置, 停止滚动, 调动截止动画
     *
     * @param position 位置
     */
    private void finishScroll(int position) {
        if (mIsScrolling) {
            mCurrentPosition = position;
            mIsScrolling = false;
            mImageAnimator.end(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //NO-OP
    }

    @Override
    public void onPageSelected(int position) {
        if (!mIsScrolling) {
            mIsScrolling = true;
            mFinalPosition = position;
            mImageAnimator.start(mCurrentPosition, position);
        }
    }
}
