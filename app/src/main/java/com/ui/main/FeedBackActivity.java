package com.ui.main;

import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;

import com.C;
import com.app.annotation.apt.Router;
import com.app.annotation.aspect.SingleClick;
import com.apt.ApiFactory;
import com.base.DataBindingActivity;
import com.base.adapter.VHSelector;
import com.base.util.ApiUtil;
import com.base.util.SpUtil;
import com.base.util.ViewUtil;
import com.data.bean.Message;
import com.data.entity.MessageInfo;
import com.data.entity._User;
import com.data.repository.MessageInfoRepository;
import com.ui.main.databinding.ActivityFeedbackBinding;


@Router(C.FEED_BACK)
public class FeedBackActivity extends DataBindingActivity<ActivityFeedbackBinding> implements View.OnClickListener {
    _User user = SpUtil.getUser();

    VHSelector<MessageInfo> mTypeSelector = (item -> TextUtils.equals(item.creater.objectId, C.ADMIN_ID)
            ? R.layout.list_item_comment_admin : R.layout.list_item_comment_user);//AdminID发送的为Admin消息，其他都是普通消息

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initView() {
        setTitle("用户反馈");
        mViewBinding.lvMsg.setReverse().setIsRefreshable(false)
                .setTypeSelectorAndRepository(mTypeSelector, MessageInfoRepository.class)
                .setFooterView(R.layout.list_item_comment_admin, C.getAdminMsg())
                .setParam(C.INCLUDE, C.CREATER)
                .setParam(C.UID, user.objectId)
                .fetch();
        mViewBinding.btSend.setOnClickListener(this);
    }

    @SingleClick
    public void onClick(View view) {
        String msg = mViewBinding.etMessage.getText().toString();
        if (TextUtils.isEmpty(msg)) {
            Snackbar.make(view, "内容不能为空!", Snackbar.LENGTH_LONG).show();
            return;
        }
        ApiFactory.createMessage(new Message(ApiUtil.getPointer(new _User(C.ADMIN_ID)), msg,
                ApiUtil.getPointer(user), user.objectId))
                .subscribe(res -> {
                    mViewBinding.lvMsg.reFetch();
                    ViewUtil.hideKeyboard(this);
                    mViewBinding.etMessage.setText("");
                }, e -> Snackbar.make(view, "消息发送失败!", Snackbar.LENGTH_LONG).show());
    }
}
