package com.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * 火焰类
 */
public class FireView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder holder;
    private boolean running = true, isLeft = true;
    private int startColor = Color.parseColor("#FFFF00"), endColor = Color.parseColor("#FF0000"), bottomColor = Color.parseColor("#70392f");
    int fireHeight, fireWidth, halfWidth, distance;
    private List<FirePice> mFirePices = new ArrayList<>();
    private Paint mPaint;

    public FireView(Context context) {
        super(context);
        intit();
    }

    public FireView(Context context, AttributeSet att) {
        super(context, att);
        intit();
    }

    private void intit() {
        holder = this.getHolder();//获取holder
        holder.addCallback(this);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Style.FILL);
        mPaint.setStrokeWidth(0);
        mPaint.setColor(bottomColor);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        fireHeight = getHeight() * 3 / 4;
        halfWidth = getWidth() / 2;
        fireWidth = halfWidth / 4;
        distance = fireWidth / 8;
        running = true;
        new RefreshThread().start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        running = false;
    }

    private class RefreshThread extends Thread {
        @Override
        public void run() {
            while (running) {
                Canvas canvas = null;
                try {
                    canvas = holder.lockCanvas();//获取画布
                    if (canvas != null)
                        drawFire(canvas);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (canvas != null)
                        holder.unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像
                }
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void drawFire(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        if (mFirePices.size() < 10) {
            if (mFirePices.size() == 0 || mFirePices.size() > 0 && mFirePices.get(mFirePices.size() - 1).i > fireWidth / 4) {
                isLeft = !isLeft;
                mFirePices.add(new FirePice(isLeft));
            }
        } else {
            mFirePices.remove(0);
        }
        Observable.from(mFirePices).subscribe(mFirePice -> mFirePice.draw(canvas));
        fireHeight = fireHeight - fireWidth / 6;
        Path mpath1 = new Path();
        mpath1.moveTo(halfWidth - fireWidth * 3 / 4, fireHeight - 2 * distance);
        mpath1.lineTo(halfWidth + distance - fireWidth * 3 / 4, fireHeight - 4 * distance);
        mpath1.lineTo(halfWidth + fireWidth * 3 / 4 + distance, fireHeight);
        mpath1.lineTo(halfWidth + fireWidth * 3 / 4, fireHeight + 2 * distance);
        mpath1.close();
        canvas.drawPath(mpath1, mPaint);
        Path mpath2 = new Path();
        mpath2.moveTo(halfWidth - fireWidth * 3 / 4, fireHeight);
        mpath2.lineTo(halfWidth + distance - fireWidth * 3 / 4, fireHeight + 2 * distance);
        mpath2.lineTo(halfWidth + fireWidth * 3 / 4 + distance, fireHeight - 2 * distance);
        mpath2.lineTo(halfWidth + fireWidth * 3 / 4, fireHeight - 4 * distance);
        mpath2.close();
        canvas.drawPath(mpath2, mPaint);
        fireHeight = fireHeight + fireWidth / 6;
    }


    /**
     * @param fraction
     * @param startValue
     * @param endValue
     * @return
     */
    private Integer evaluate(float fraction, Object startValue, Object endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;
        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;
        return (int) ((startA + (int) (fraction * (endA - startA))) << 24)
                | (int) ((startR + (int) (fraction * (endR - startR))) << 16)
                | (int) ((startG + (int) (fraction * (endG - startG))) << 8)
                | (int) ((startB + (int) (fraction * (endB - startB))));
    }

    /**
     * 火苗类
     */
    public class FirePice {
        boolean isLeft;
        Paint mPaint;
        Path mpath;
        int width, diff_X = 0, i = 0, height;

        public FirePice(boolean isLeft) {
            this.isLeft = isLeft;
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Style.FILL);
            mPaint.setStrokeWidth(0);
        }

        public void move() {
            i = i + 2;
            height = fireHeight - i;
            if (i < fireWidth / 4) {
                diff_X = isLeft ? diff_X - 1 : diff_X + 1;
            } else if (i < fireWidth * 6 / 7) {
                diff_X = isLeft ? diff_X + 1 : diff_X - 1;
            } else if (i < fireWidth * 3 / 2) {
                diff_X = isLeft ? diff_X - 1 : diff_X + 1;
            } else {
                diff_X = isLeft ? diff_X + 1 : diff_X - 1;
            }
            if (i < fireWidth) {
                width = i;
            } else if (i < 2 * fireWidth) {
                width = 2 * fireWidth - i;
            } else if (i == 2 * fireWidth) {
                width = 0;
                i = 0;
                height = fireHeight;
                diff_X = 0;
            }
            mpath = new Path();
            mpath.moveTo(halfWidth - distance * (isLeft ? 1 : -1) + diff_X - width / 2, height);
            mpath.lineTo(halfWidth - distance * (isLeft ? 1 : -1) + diff_X, height - width / 2);
            mpath.lineTo(halfWidth - distance * (isLeft ? 1 : -1) + diff_X + width / 2, height);
            mpath.lineTo(halfWidth - distance * (isLeft ? 1 : -1) + diff_X, height + width / 2);
            mpath.close();
        }

        public void draw(Canvas mCanvas) {
            move();
            if (i < 2 * fireWidth && i > fireWidth / 5) {
                mPaint.setColor(evaluate((float) (i * 1.0 / (2 * fireWidth)), startColor, endColor));
                mCanvas.drawPath(mpath, mPaint);
            }
        }
    }
}
