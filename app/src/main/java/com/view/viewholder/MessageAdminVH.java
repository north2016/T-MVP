package com.view.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.C;
import com.app.annotation.apt.InstanceFactory;
import com.app.annotation.aspect.SingleClick;
import com.base.BaseViewHolder;
import com.base.util.ImageUtil;
import com.data.entity.MessageInfo;
import com.ui.article.ArticleActivity;
import com.ui.main.R;
import com.ui.user.UserActivity;

import butterknife.Bind;

/**
 * Created by baixiaokang on 16/12/29.
 */
@InstanceFactory(type = InstanceFactory.typeVH)
public class MessageAdminVH extends BaseViewHolder<MessageInfo> implements View.OnClickListener {
    @Bind(R.id.tv_content)
    TextView tv_content;
    @Bind(R.id.tv_name)
    TextView tv_name;
    @Bind(R.id.im_user)
    ImageView im_user;

    public MessageAdminVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.list_item_comment_admin;
    }

    @Override
    public void onBindViewHolder(View view, final MessageInfo data) {
        super.onBindViewHolder(view, data);
        tv_name.setText(data.creater.username);
        tv_content.setText(Html.fromHtml(data.message));
        ImageUtil.loadRoundImg(im_user, data.creater.face);
        im_user.setOnClickListener(this);
    }

    @SingleClick
    public void onClick(View view) {
        ActivityCompat.startActivity(mContext, new Intent(mContext, UserActivity.class).putExtra(C.HEAD_DATA, data.creater)
                , ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, im_user, ArticleActivity.TRANSLATE_VIEW).toBundle());
    }
}
