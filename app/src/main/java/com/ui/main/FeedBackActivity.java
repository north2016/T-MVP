package com.ui.main;

import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.C;
import com.apt.ApiFactory;
import com.base.BaseActivity;
import com.base.util.ApiUtil;
import com.base.util.SpUtil;
import com.base.util.ViewUtil;
import com.data.entity.Message;
import com.data.entity._User;
import com.view.layout.TRecyclerView;
import com.view.viewholder.MessageVH;

import butterknife.Bind;

/**
 * Created by baixiaokang on 16/12/26.
 */

public class FeedBackActivity extends BaseActivity {
    @Bind(R.id.lv_msg)
    TRecyclerView lvMsg;
    @Bind(R.id.et_message)
    EditText etMessage;
    @Bind(R.id.bt_send)
    Button btSend;

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initView() {
        setTitle("用户反馈");
        _User user = SpUtil.getUser();
        lvMsg.setFooterView(MessageVH.class, C.getHeaderMessageInfo())
                .setView(MessageVH.class).setReverse().setIsRefreshable(false)
                .setParam(C.INCLUDE, C.CREATER).setParam(C.UID, user.objectId).fetch();
        btSend.setOnClickListener(v -> {
            String msg = etMessage.getText().toString();
            if (TextUtils.isEmpty(msg)) {
                Snackbar.make(btSend, "内容不能为空!", Snackbar.LENGTH_LONG).show();
                return;
            }
            ApiFactory.createMessage(new Message(ApiUtil.getPointer(new _User(C.ADMIN_ID)), msg,
                    ApiUtil.getPointer(user), user.objectId))
                    .subscribe(res -> {
                        lvMsg.reFetch();
                        ViewUtil.hideKeyboard(this);
                        etMessage.setText("");
                    }, e -> Snackbar.make(btSend, "消息发送失败!", Snackbar.LENGTH_LONG).show());
        });
    }
}
