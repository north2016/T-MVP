package com.view.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.C;
import com.app.annotation.apt.InstanceFactory;
import com.app.annotation.aspect.SingleClick;
import com.apt.TRouter;
import com.base.adapter.BaseViewHolder;
import com.base.util.ImageUtil;
import com.data.bean.ExtraData;
import com.data.entity.CommentInfo;
import com.ui.main.R;

import butterknife.Bind;

/**
 * Created by baixiaokang on 16/5/4.
 */
@InstanceFactory(InstanceFactory.TYPE_VH)
public class UserCommentVH extends BaseViewHolder<CommentInfo> implements View.OnClickListener {
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
    public void onBindViewHolder(View view, final CommentInfo data) {
        super.onBindViewHolder(view, data);
        tv_content.setText(data.content);
        tv_title.setText(data.article.title);
        ImageUtil.loadImg(im_article, data.article.image);
        view.setOnClickListener(this);
    }

    @SingleClick
    public void onClick(View view) {
        TRouter.go(C.ARTICLE, new ExtraData(C.HEAD_DATA, data.article).build(), im_article);
    }
}
