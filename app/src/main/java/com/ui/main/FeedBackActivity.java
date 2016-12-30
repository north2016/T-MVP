package com.ui.main;

import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.C;
import com.app.annotation.aspect.SingleClick;
import com.apt.ApiFactory;
import com.base.BaseActivity;
import com.base.CoreAdapter;
import com.base.util.ApiUtil;
import com.base.util.SpUtil;
import com.base.util.ViewUtil;
import com.data.entity.Message;
import com.data.entity.MessageInfo;
import com.data.entity._User;
import com.view.layout.TRecyclerView;
import com.view.viewholder.MessageAdminVH;
import com.view.viewholder.MessageUserVH;

import butterknife.Bind;
import butterknife.OnClick;

public class FeedBackActivity extends BaseActivity implements View.OnClickListener {
    _User user = SpUtil.getUser();
    @Bind(R.id.lv_msg)
    TRecyclerView<MessageInfo> lvMsg;
    @Bind(R.id.et_message)
    EditText etMessage;

    CoreAdapter.VHClassSelector<MessageInfo> mTypeSelector
            = (item -> item != null && TextUtils.equals(item.creater.objectId, C.ADMIN_ID)
            ? MessageAdminVH.class : MessageUserVH.class);//AdminID发送的为Admin消息，其他都是普通消息

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initView() {
        setTitle("用户反馈");
        lvMsg.setReverse().setIsRefreshable(false)
                .setTypeSelector(mTypeSelector)
                .setFooterView(MessageAdminVH.class, C.getAdminMsg())
                .setParam(C.INCLUDE, C.CREATER)
                .setParam(C.UID, user.objectId)
                .fetch();
    }

    @SingleClick
    @OnClick(R.id.bt_send)
    public void onClick(View view) {
        String msg = etMessage.getText().toString();
        if (TextUtils.isEmpty(msg)) {
            Snackbar.make(view, "内容不能为空!", Snackbar.LENGTH_LONG).show();
            return;
        }
        ApiFactory.createMessage(new Message(ApiUtil.getPointer(new _User(C.ADMIN_ID)), msg,
                ApiUtil.getPointer(user), user.objectId))
                .subscribe(res -> {
                    lvMsg.reFetch();
                    ViewUtil.hideKeyboard(this);
                    etMessage.setText("");
                }, e -> Snackbar.make(view, "消息发送失败!", Snackbar.LENGTH_LONG).show());
    }
}
