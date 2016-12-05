package com.view.viewholder;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.C;
import com.app.annotation.apt.Instance;
import com.base.BaseViewHolder;
import com.base.util.ImageUtil;
import com.data.entity._User;
import com.data.repository._UserRepository;
import com.ui.article.ArticleActivity;
import com.ui.main.R;
import com.ui.user.UserActivity;

import butterknife.Bind;

/**
 * Created by baixiaokang on 16/5/4.
 */
@Instance(type = Instance.typeVH)
public class UserItemVH extends BaseViewHolder<_UserRepository> {
    @Bind(R.id.tv_content)
    TextView tv_content;
    @Bind(R.id.im_user)
    ImageView im_user;

    public UserItemVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.list_item_user;
    }

    @Override
    public void onBindViewHolder(View view, final _UserRepository user) {
        _User data=user.data;//拆箱，从集装箱中取货
        tv_content.setText(data.username);
        ImageUtil.loadRoundImg(im_user,data.face);
        im_user.setOnClickListener(v ->
                ActivityCompat.startActivity((Activity) mContext, new Intent(mContext, UserActivity.class).putExtra(C.HEAD_DATA, data)
                        , ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, im_user, ArticleActivity.TRANSLATE_VIEW).toBundle())
        );
    }
}
