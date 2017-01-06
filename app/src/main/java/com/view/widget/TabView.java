package com.view.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.base.util.ImageUtil;
import com.ui.main.R;

/**
 * Created by baixiaokang on 16/12/1.
 */

public class TabView extends FrameLayout {
    private ImageView image;//


    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.tab_item, this);
        image = (ImageView) findViewById(R.id.image);
    }

    public void setImage(String url) {
        if (image.getTag(R.id.im_face) != null && TextUtils.equals((String) image.getTag(R.id.im_face), url))
            return;
        image.setTag(R.id.im_face, url);
        ImageUtil.loadRoundImg(image, url);
    }

    public void releaseImage() {
        try {
            // 释放图片
            BitmapDrawable drawableRel = (BitmapDrawable) image.getBackground();
            if (drawableRel != null) {
                System.gc();
                drawableRel.setCallback(null);
                drawableRel.getBitmap().recycle();
                drawableRel = null;
                image.setBackgroundResource(0);
                image.setBackground(null);
                if (image instanceof ImageView) {
                    ((ImageView) image).setImageResource(0);
                    image.setTag(R.id.im_face, null);
                }
                System.gc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}