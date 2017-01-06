package com.view.viewholder;

import android.text.Html;
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
import com.data.entity.MessageInfo;
import com.ui.main.R;

import butterknife.Bind;

/**
 * Created by baixiaokang on 16/12/29.
 */
@InstanceFactory(InstanceFactory.typeVH)
public class MessageUserVH extends BaseViewHolder<MessageInfo> implements View.OnClickListener {
    @Bind(R.id.tv_content)
    TextView tv_content;
    @Bind(R.id.im_user)
    ImageView im_user;

    public MessageUserVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.list_item_comment;
    }

    @Override
    public void onBindViewHolder(View view, final MessageInfo data) {
        super.onBindViewHolder(view, data);
        tv_content.setText(Html.fromHtml("<font color='#ff7200'>" + data.creater.username + "<br/><br/>" + "</font>" + data.message));
        ImageUtil.loadRoundImg(im_user, data.creater.face);
        im_user.setOnClickListener(this);
    }

    @SingleClick
    public void onClick(View view) {
        TRouter.go(C.USER_INFO, new ExtraData(C.HEAD_DATA, data.creater).build(), im_user);
    }
}
