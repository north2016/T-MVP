package com.ui.login;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.C;
import com.app.annotation.apt.Router;
import com.base.BaseActivity;
import com.ui.home.HomeActivity;
import com.ui.main.R;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/1/14.
 */

@Router(C.LOGIN)
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
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
        fab.setOnClickListener(v -> doAction());
        tv_sign.setOnClickListener(v -> {
            isLogin = false;
            tv_title.setText("注册");
            tv_sign.setVisibility(View.GONE);
        });
    }

    private void doAction() {
        String name = tlName.getEditText().getText().toString();
        String pass = tlPass.getEditText().getText().toString();
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
        Snackbar.make(fab, msg, Snackbar.LENGTH_LONG).show();
    }
}
