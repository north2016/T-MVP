package com.base.util;

import android.graphics.Paint;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by baixiaokang on 16/12/10.
 */

public class BaseUtils {

    public static List<String> getOldWeekDays() {
        final Calendar c = Calendar.getInstance();
        String[] months = new String[8];
        for (int i = 0; i < 8; i++) {
            months[i] = new SimpleDateFormat("MM.dd").format(new Date(c
                    .getTimeInMillis()));
            c.add(Calendar.DAY_OF_MONTH, -1);
        }
        return Arrays.asList(months);
    }

    public static Paint getPaint(Paint.Style style, int color) {
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(style);
        mPaint.setColor(color);
        mPaint.setTextSize(30);
        return mPaint;
    }
}
