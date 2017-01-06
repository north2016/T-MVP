package com.ui.login;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;

import com.C;
import com.app.annotation.apt.Router;
import com.base.BaseActivity;
import com.ui.home.HomeActivity;
import com.ui.main.R;
import com.ui.main.databinding.ActivityLoginBinding;

/**
 * Created by Administrator on 2016/1/14.
 */

@Router(C.LOGIN)
public class LoginActivity extends BaseActivity<LoginPresenter, ActivityLoginBinding> implements LoginContract.View {
    boolean isLogin = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        mViewBinding.fab.setOnClickListener(v -> doAction());
        mViewBinding.tvSign.setOnClickListener(v -> {
            isLogin = false;
            mViewBinding.tvTitle.setText("注册");
            mViewBinding.tvSign.setVisibility(View.GONE);
        });
    }

    private void doAction() {
        String name = mViewBinding.tlName.getEditText().getText().toString();
        String pass = mViewBinding.tlPass.getEditText().getText().toString();
        String msg = TextUtils.isEmpty(name) ? "用户名不能为空!" : TextUtils.isEmpty(pass) ? "密码不能为空!" : "";
        if (!TextUtils.isEmpty(msg)) showMsg(msg);
        else if (isLogin) mPresenter.login(name, pass);
        else mPresenter.sign(name, pass);
    }

    @Override
    public void loginSuccess() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
    }

    @Override
    public void signSuccess() {
        isLogin = true;
        doAction();
    }

    @Override
    public void showMsg(String msg) {
        Snackbar.make(mViewBinding.fab, msg, Snackbar.LENGTH_LONG).show();
    }
}
