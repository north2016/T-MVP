package com.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.view.View;

import com.app.annotation.aspect.SingleClick;
import com.base.util.BaseUtils;
import com.ui.main.R;

public class ChartView extends View implements View.OnClickListener {
    private Paint mTextPaint, mYellowTextPaint, mGreenTextPaint, mYellowPaint, mGreenPaint;// 画笔
    private Double[] Lines, num0, num1;
    private float base;
    private Double max;
    private float itemWidth;
    private float itemHeight;
    private Path mPath = new Path();
    private boolean isLine = false;

    public ChartView(Context context, Double[] Lines, Double[] num0, Double[] num1) {
        super(context);
        this.Lines = Lines;
        this.num0 = num0;
        this.num1 = num1;
        base = (float) ((Lines[Lines.length - 1] - Lines[0]) * 100 / 50.0);
        mTextPaint = BaseUtils.getPaint(Style.STROKE, Color.WHITE);
        mYellowTextPaint = BaseUtils.getPaint(Style.FILL, Color.parseColor("#ffba00"));
        mGreenTextPaint = BaseUtils.getPaint(Style.FILL, Color.parseColor("#00ff36"));
        mYellowPaint = BaseUtils.getPaint(Style.STROKE, Color.parseColor("#ffba00"));
        mGreenPaint = BaseUtils.getPaint(Style.STROKE, Color.parseColor("#00ff36"));
        this.setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(getResources().getColor(R.color.colorPrimary));
        itemWidth = getWidth() / 9;
        itemHeight = getHeight() / 8;
        canvas.drawText("活跃用户", getWidth() / 4, itemHeight * 4 / 3, mYellowTextPaint);
        canvas.drawText(isLine ? "折线图" : "曲线图", getWidth() / 2, itemHeight * 4 / 3, isLine ? mYellowTextPaint : mGreenTextPaint);
        canvas.drawText("联网用户", getWidth() * 3 / 4, itemHeight * 4 / 3, mGreenTextPaint);
        max = Lines[Lines.length - 1];
        for (int i = 1; i < 9; i++) {
            if (i < 7) {
                canvas.drawLine(itemWidth, (i + 1) * itemHeight, itemWidth * 8 + itemWidth / 2, (i + 1) * itemHeight, mTextPaint);// 画横线
                canvas.drawText(String.valueOf(Lines[6 - i]), itemWidth / 4, itemHeight * (i + 1), mTextPaint);// 写横字
            }
            canvas.drawLine(i * itemWidth, itemHeight * 3 / 2, i * itemWidth, itemHeight * 7, mTextPaint);// 画竖线
            canvas.drawText(BaseUtils.getOldWeekDays().get(8 - i), i * itemWidth, itemHeight * 7 + itemHeight / 2, mTextPaint);// 写竖字
            if (isLine) {
                canvas.drawCircle(i * itemWidth, getBaseHeight(num0[i - 1]), 5, mYellowTextPaint);     // 画黄点
                canvas.drawCircle(i * itemWidth, getBaseHeight(num1[i - 1]), 5, mGreenTextPaint);    // 画绿点
            }
            if (i > 1) {
                float x0 = (i - 1) * itemWidth, y0 = getBaseHeight(num0[i - 2]), x1 = i * itemWidth;
                float y1 = getBaseHeight(num0[i - 1]), y2 = getBaseHeight(num1[i - 2]), y3 = getBaseHeight(num1[i - 1]);
                if (isLine) {// 画折线
                    canvas.drawLine(x0, y0, x1, y1, mYellowTextPaint);// 画黄折线
                    canvas.drawLine(x0, y2, x1, y3, mGreenTextPaint); // 画绿折线
                } else {// 画曲线
                    mPath.reset();
                    mPath.moveTo(x0, y0);
                    mPath.cubicTo((x0 + x1) / 2, y0, (x0 + x1) / 2, y1, x1, y1);
                    canvas.drawPath(mPath, mYellowPaint);
                    mPath.reset();
                    mPath.moveTo(x0, y2);
                    mPath.cubicTo((x0 + x1) / 2, y2, (x0 + x1) / 2, y3, x1, y3);
                    canvas.drawPath(mPath, mGreenPaint);
                }
            }
        }
    }

    private float getBaseHeight(Double value) {
        return 2 * itemHeight + 1 / base * (int) ((max - value) * 100) * itemHeight / 10;
    }

    @SingleClick
    public void onClick(View view) {
        isLine = !isLine;
        invalidate();
    }
}

