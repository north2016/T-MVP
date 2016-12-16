package com.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.View;

import com.base.util.BaseUtils;
import com.ui.main.R;

public class ChartView extends View {
    private Paint mTextPaint, mYellowTextPaint, mGreenTextPaint;// 画笔
    private Double[] Lines, num0, num1;
    private float base;
    private Double max;
    private float itemWidth;
    private float itemHeight;

    public ChartView(Context context, Double[] Lines, Double[] num0, Double[] num1) {
        super(context);
        this.Lines = Lines;
        this.num0 = num0;
        this.num1 = num1;
        base = (float) ((Lines[Lines.length - 1] - Lines[0]) * 100 / 50.0);
        mTextPaint = BaseUtils.getPaint(Style.STROKE, Color.WHITE);
        mYellowTextPaint = BaseUtils.getPaint(Style.FILL, Color.parseColor("#ffba00"));
        mGreenTextPaint = BaseUtils.getPaint(Style.FILL, Color.parseColor("#00ff36"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(getResources().getColor(R.color.colorPrimaryDark));
        itemWidth = getWidth() / 9;
        itemHeight = getHeight() / 8;
        canvas.drawText("活跃用户", getWidth() / 4, itemHeight*4/3, mYellowTextPaint);
        canvas.drawText("联网用户", getWidth() * 3 / 4, itemHeight*4/3, mGreenTextPaint);
        max = Lines[Lines.length - 1];
        for (int i = 1; i < 9; i++) {
            if (i < 7) {
                canvas.drawLine(itemWidth, (i + 1) * itemHeight, itemWidth * 8 + itemWidth / 2, (i + 1) * itemHeight, mTextPaint);// 画横线
                canvas.drawText(String.valueOf(Lines[6 - i]), itemWidth / 4, itemHeight * (i + 1), mTextPaint);// 写横字
            }
            canvas.drawLine(i * itemWidth, itemHeight * 3 / 2, i * itemWidth, itemHeight * 7, mTextPaint);// 画竖线
            canvas.drawText(BaseUtils.getOldWeekDays().get(8 - i), i * itemWidth, itemHeight * 7 + itemHeight / 2, mTextPaint);// 写竖字
            canvas.drawCircle(i * itemWidth, getBaseHeight(num0[i - 1]), 5, mYellowTextPaint);     // 画黄点
            canvas.drawCircle(i * itemWidth, getBaseHeight(num1[i - 1]), 5, mGreenTextPaint);    // 画绿点
            if (i > 1) {// 画折线
                canvas.drawLine((i - 1) * itemWidth, getBaseHeight(num0[i - 2]), i * itemWidth, getBaseHeight(num0[i - 1]), mYellowTextPaint);// 画黄折线
                canvas.drawLine((i - 1) * itemWidth, getBaseHeight(num1[i - 2]), i * itemWidth, getBaseHeight(num1[i - 1]), mGreenTextPaint); // 画绿折线
            }
        }
    }

    private float getBaseHeight(Double value) {
        return 2 * itemHeight + 1 / base * (int) ((max - value) * 100) * itemHeight / 10;
    }
}

