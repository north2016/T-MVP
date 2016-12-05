package com.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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
        LayoutInflater.from(context).inflate(R.layout.tab_item, this);
        image = (ImageView) findViewById(R.id.image);
    }

    public void setImage(String url) {
        ImageUtil.loadRoundImg(image, url);
    }

}