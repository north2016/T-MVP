package com.ui.login;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.TextView;

import com.base.BaseActivity;
import com.ui.home.HomeActivity;
import com.ui.login.LoginContract.View;
import com.ui.main.R;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/1/14.
 */
public class LoginActivity extends BaseActivity<LoginPresenter, LoginModel> implements View {
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.tl_name)
    TextInputLayout tlName;
    @Bind(R.id.tl_pass)
    TextInputLayout tlPass;
    @Bind(R.id.tv_sign)
    TextView tv_sign;
    @Bind(R.id.tv_title)
    TextView tv_title;
    boolean isLogin = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        fab.setOnClickListener(v -> {
            String name = tlName.getEditText().getText().toString();
            String pass = tlPass.getEditText().getText().toString();
            String msg = TextUtils.isEmpty(name) ? "用户名不能为空!" : TextUtils.isEmpty(pass) ? "密码不能为空!" : "";
            if (!TextUtils.isEmpty(msg)) showMsg(msg);
            else if (isLogin) mPresenter.login(name, pass);
            else mPresenter.sign(name, pass);
        });
        tv_sign.setOnClickListener(v -> swich());
    }

    private void swich() {
        if (isLogin) {
            isLogin = false;
            tv_title.setText("注册");
            tv_sign.setText("去登录");
        } else {
            isLogin = true;
            tv_title.setText("登录");
            tv_sign.setText("去注册");
        }
    }


    @Override
    public void loginSuccess() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
    }

    @Override
    public void signSuccess() {
        swich();
    }

    @Override
    public void showMsg(String msg) {
        Snackbar.make(fab, msg, Snackbar.LENGTH_LONG).show();
    }
}
