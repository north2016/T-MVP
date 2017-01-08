package com.ui.advise;

import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;

import com.C;
import com.app.annotation.apt.Router;
import com.app.annotation.aspect.SingleClick;
import com.base.BaseActivity;
import com.base.adapter.TypeSelector;
import com.base.util.ViewUtil;
import com.model.MessageInfo;
import com.ui.main.R;
import com.ui.main.databinding.ActivityFeedbackBinding;

@Router(C.ADVISE)
public class AdviseActivity extends BaseActivity<AdvisePresenter, ActivityFeedbackBinding> implements View.OnClickListener, TypeSelector<MessageInfo>, AdviseContract.View {

    @Override // AdminID发送的为Admin消息，其他都是普通消息
    public int getType(MessageInfo item) {
        return TextUtils.equals(item.creater.objectId, C.ADMIN_ID)
                ? R.layout.list_item_comment_admin : R.layout.list_item_comment_user;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initView() {
        setTitle("用户反馈");
        mViewBinding.lvMsg.setFootData(C.getAdminMsg()).setTypeSelector(this);
        mPresenter.initAdapterPresenter(mViewBinding.lvMsg.getPresenter());
    }

    @SingleClick
    public void onClick(View view) {
        String msg = mViewBinding.etMessage.getText().toString();
        if (TextUtils.isEmpty(msg)) showMsg("内容不能为空!");
        else mPresenter.createMessage(msg);
    }

    @Override
    public void sendSuc() {
        mViewBinding.lvMsg.reFetch();
        ViewUtil.hideKeyboard(this);
        mViewBinding.etMessage.setText("");
    }

    @Override
    public void showMsg(String msg) {
        Snackbar.make(mViewBinding.btSend, msg, Snackbar.LENGTH_SHORT).show();
    }
}
