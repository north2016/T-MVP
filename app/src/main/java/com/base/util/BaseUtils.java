package com.base.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.util.Log;

import com.App;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    /**
     * @param fraction
     * @param startValue
     * @param endValue
     * @return
     */
    public static Integer evaluate(float fraction, Object startValue, Object endValue) {
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
     * 压缩图片
     *
     * @param srcPath
     * @return
     */
    public static String getUpLoadimage(String srcPath) {
        try {
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
            newOpts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空
            newOpts.inJustDecodeBounds = false;
            int w = newOpts.outWidth;
            int h = newOpts.outHeight;
            // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
            float hh = 800f;// 这里设置高度为800f
            float ww = 480f;// 这里设置宽度为480f
            // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int be = 1;// be=1表示不缩放
            if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
                be = (int) (newOpts.outWidth / ww);
            } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
                be = (int) (newOpts.outHeight / hh);
            }
            if (be <= 0)
                be = 1;
            newOpts.inSampleSize = be;// 设置缩放比例
            // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
            bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
            return compressImage(bitmap);// 压缩好比例大小后再进行质量压缩
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("Exception", "大小压缩失败");
            return srcPath;
        }

    }

    private static String compressImage(Bitmap image) throws Exception {
        String url = "";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 99, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            options -= 10;// 每次都减少10
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
        }
        image.recycle();
        Long dd = new Date().getTime();
        try {
            url = saveFile(baos, dd + ".png");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e("Exception", "质量压缩失败");
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 保存文件
     *
     * @param baos
     * @param fileName
     * @throws Exception
     */
    public static String saveFile(ByteArrayOutputStream baos, String fileName)
            throws Exception {
        String Path = App.getAppContext().getFilesDir().getPath();
        File dirFile = new File(Path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(Path + "/" + fileName);
        FileOutputStream fi = new FileOutputStream(myCaptureFile);
        baos.writeTo(fi);
        baos.flush();
        baos.close();
        return myCaptureFile.getAbsolutePath();
    }


}
