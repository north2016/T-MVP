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
import com.data.entity.CommentInfo;
import com.ui.article.ArticleActivity;
import com.ui.main.R;

/**
 * Created by baixiaokang on 16/5/4.
 */
public class UserCommentVH extends BaseViewHolder<CommentInfo> {
    TextView tv_content,tv_title;
    ImageView im_article;

    public UserCommentVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.list_item_user_comment;
    }

    @Override
    public void onBindViewHolder(View view, final CommentInfo mSubject) {
        tv_content.setText(mSubject.content);
        tv_title.setText(mSubject.article.title);
        ImageUtil.loadImg(im_article,mSubject.article.image);
        view.setOnClickListener((v) ->
                ActivityCompat.startActivity((Activity) mContext, new Intent(mContext, ArticleActivity.class).putExtra(C.HEAD_DATA, mSubject.article)
                        , ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, im_article, ArticleActivity.TRANSLATE_VIEW).toBundle())
        );
    }
}
