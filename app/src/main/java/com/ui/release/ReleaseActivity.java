package com.ui.release;

import android.support.design.widget.Snackbar;
import android.text.TextUtils;

import com.C;
import com.EventTags;
import com.app.annotation.apt.Router;
import com.base.BaseActivity;
import com.base.event.OkBus;
import com.ui.main.R;
import com.ui.main.databinding.ActivityReleaseBinding;


@Router(C.USER_RELEASE)
public class ReleaseActivity extends BaseActivity<ReleasePresenter, ActivityReleaseBinding> implements ReleaseContract.View {
    @Override
    public int getLayoutId() {
        return R.layout.activity_release;
    }

    @Override
    public void initView() {
        mViewBinding.btRelease.setOnClickListener(v -> {
            String url, title, content;
            url = mViewBinding.etUrl.getText().toString().trim();
            title = mViewBinding.etTitle.getText().toString().trim();
            content = mViewBinding.etContent.getText().toString().trim();
            if (TextUtils.isEmpty(url) || TextUtils.isEmpty(title) || TextUtils.isEmpty(content))
                showMsg("请完善信息!");
            else mPresenter.upArticle(url, title, content);
        });
    }

    @Override
    public void showMsg(String msg) {
        Snackbar.make(mViewBinding.btRelease, msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void releaseSuc() {
        showMsg("发布成功!");
        OkBus.getInstance().onEvent(EventTags.ON_RELEASE_OPEN);
        finish();
    }
}
