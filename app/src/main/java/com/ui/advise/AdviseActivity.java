package com.ui.advise;

import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;

import com.C;
import com.app.annotation.apt.Router;
import com.app.annotation.aspect.SingleClick;
import com.apt.ApiFactory;
import com.base.BaseActivity;
import com.base.adapter.TypeSelector;
import com.base.util.SpUtil;
import com.base.util.ViewUtil;
import com.model.MessageInfo;
import com.ui.main.R;
import com.ui.main.databinding.ActivityFeedbackBinding;

@Router(C.ADVISE)
public class AdviseActivity extends BaseActivity<AdvisePresenter, ActivityFeedbackBinding> implements View.OnClickListener, AdviseContract.View {

    TypeSelector<MessageInfo> mTypeSelector
            = (item -> TextUtils.equals(item.creater.objectId, C.ADMIN_ID)
            ? R.layout.list_item_comment_admin : R.layout.list_item_comment_user);//AdminID发送的为Admin消息，其他都是普通消息

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initView() {
        setTitle("用户反馈");
        mViewBinding.lvMsg.setReverse().setIsRefreshable(false)
                .setTypeSelector(mTypeSelector)
                .setFooterView(R.layout.list_item_comment_admin, C.getAdminMsg());
        mViewBinding.lvMsg.getPresenter()
                .setRepository(ApiFactory::getMessageList)
                .setParam(C.INCLUDE, C.CREATER)
                .setParam(C.UID, SpUtil.getUser().objectId)
                .fetch();
        mViewBinding.btSend.setOnClickListener(this);
    }

    @SingleClick
    public void onClick(View view) {
        String msg = mViewBinding.etMessage.getText().toString();
        if (TextUtils.isEmpty(msg))
            Snackbar.make(view, "内容不能为空!", Snackbar.LENGTH_LONG).show();
        else mPresenter.createMessage(msg);
    }

    @Override
    public void sendSuc() {
        mViewBinding.lvMsg.reFetch();
        ViewUtil.hideKeyboard(this);
        mViewBinding.etMessage.setText("");
    }

    @Override
    public void sendFail() {
        Snackbar.make(mViewBinding.btSend, "消息发送失败!", Snackbar.LENGTH_LONG).show();
    }
}
