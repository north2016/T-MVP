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
import com.data.repository.CommentInfoRepository;
import com.ui.article.ArticleActivity;
import com.ui.main.R;

import butterknife.Bind;

/**
 * Created by baixiaokang on 16/5/4.
 */
public class UserCommentVH extends BaseViewHolder<CommentInfoRepository> {
    @Bind(R.id.tv_content)
    TextView tv_content;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.im_article)
    ImageView im_article;

    public UserCommentVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.list_item_user_comment;
    }

    @Override
    public void onBindViewHolder(View view, final CommentInfoRepository mSubject) {
        tv_content.setText(mSubject.data.content);
        tv_title.setText(mSubject.data.article.title);
        ImageUtil.loadImg(im_article,mSubject.data.article.image);
        view.setOnClickListener((v) ->
                ActivityCompat.startActivity((Activity) mContext, new Intent(mContext, ArticleActivity.class).putExtra(C.HEAD_DATA, mSubject.data.article)
                        , ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, im_article, ArticleActivity.TRANSLATE_VIEW).toBundle())
        );
    }
}
