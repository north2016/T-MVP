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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.data.entity.Comment;
import com.data.entity.CommentInfo;
import com.ui.article.ArticleActivity;
import com.ui.main.R;
import com.ui.user.UserActivity;

/**
 * Created by baixiaokang on 16/5/4.
 */
public class CommentItemVH extends BaseViewHolder<CommentInfo> {
    TextView tv_content;
    ImageView im_user;

    public CommentItemVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.list_item_comment;
    }

    @Override
    public void onBindViewHolder(View view, final CommentInfo mSubject) {
        tv_content.setText(mSubject.content);
        ImageUtil.loadRoundImg(im_user,mSubject.creater.face);
        im_user.setOnClickListener(v ->
                ActivityCompat.startActivity((Activity) mContext, new Intent(mContext, UserActivity.class).putExtra(C.HEAD_DATA, mSubject.creater)
                        , ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, im_user, ArticleActivity.TRANSLATE_VIEW).toBundle())
        );
    }
}
