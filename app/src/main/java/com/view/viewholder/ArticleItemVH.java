package com.view.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.C;
import com.base.BaseViewHolder;
import com.base.util.ImageUtil;
import com.data.repository.ImageRepository;
import com.ui.article.ArticleActivity;
import com.ui.main.R;

import butterknife.Bind;

/**
 * Created by baixiaokang on 16/4/23.
 */
public class ArticleItemVH extends BaseViewHolder<ImageRepository> {
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_des)
    TextView tv_des;
    @Bind(R.id.tv_info)
    TextView tv_info;
    @Bind(R.id.tv_time)
    TextView tv_time;

    public ArticleItemVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.list_item_card_main;
    }

    @Override
    public void onBindViewHolder(View view, final ImageRepository mSubject) {
        ImageUtil.loadImg(image, mSubject.data.image);
        tv_title.setText(mSubject.data.title);
        tv_des.setText(mSubject.data.author);
        tv_info.setText(mSubject.data.type);
        tv_time.setText(mSubject.data.createdAt);
        view.setOnClickListener((v) ->
                ActivityCompat.startActivity((Activity) mContext, new Intent(mContext, ArticleActivity.class).putExtra(C.HEAD_DATA, mSubject.data)
                        , ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, image, ArticleActivity.TRANSLATE_VIEW).toBundle())
        );
    }
}
