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
public class AdviseActivity extends BaseActivity<AdvisePresenter, ActivityFeedbackBinding> implements View.OnClickListener, AdviseContract.View {
    TypeSelector<MessageInfo> mTypeSelector = (item) -> TextUtils.equals(item.creater.objectId, C.ADMIN_ID)
            ? R.layout.list_item_comment_admin : R.layout.list_item_comment_user;// AdminID发送的为Admin消息，其他都是普通消息

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initView() {
        mViewBinding.lvMsg.setFootData(C.getAdminMsg()).setTypeSelector(mTypeSelector);
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
