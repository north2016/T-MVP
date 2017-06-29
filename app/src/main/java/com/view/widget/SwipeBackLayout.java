package com.view.widget;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class SwipeBackLayout extends FrameLayout {


    private ViewDragHelper mViewDragHelper;

    private int mCurrentX;

    private int mCurEdgeFlag = ViewDragHelper.EDGE_LEFT;

    private OnScrollListener mOnScrollListener;

    public SwipeBackLayout(Context context) {
        super(context);
        init();
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new DragBackCallback());
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            // 持续绘制
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    private class DragBackCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return false;
        }


        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {

            mCurrentX = (left < 0) ? 0 : left;

            return mCurrentX; //只允许mDragView左滑

        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {

            return 0;//防止上下滑动
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (mCurEdgeFlag == ViewDragHelper.EDGE_LEFT) {

                if (mCurrentX > getWidth() / 2) {
                    mViewDragHelper.settleCapturedViewAt(getWidth(), 0);
                } else {
                    mViewDragHelper.settleCapturedViewAt(0, 0);
                    mCurrentX = 0;
                }
                invalidate();
            }

        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            mCurEdgeFlag = edgeFlags; //获取边缘状态

            // 捕捉布局界面
            mViewDragHelper.captureChildView(getChildAt(0), pointerId);

        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            if (mCurEdgeFlag == ViewDragHelper.EDGE_LEFT) {
                if (mOnScrollListener != null)
                    mOnScrollListener.complete((float) (Math.abs(left) * 1.00 / getWidth()));
                if (left >= getWidth()) finish();
            }
        }
    }

    //设置滑动结束监听
    public void setOnScroll(OnScrollListener finishScroll) {
        this.mOnScrollListener = finishScroll;
    }

    //滑动结束接口
    public interface OnScrollListener {
        void complete(float i);
    }

    private void finish() {
        Activity act = (Activity) getContext();
        act.finish();
        act.overridePendingTransition(0, android.R.anim.fade_out);
    }

}