package com.ui.main;

import com.C;
import com.DbFactory;
import com.app.annotation.apt.Router;
import com.apt.ApiFactory;
import com.base.DataBindingActivity;
import com.ui.main.databinding.ActivityAboutBinding;
import com.view.widget.ChartView;

/**
 * 简单页面无需mvp,该咋写还是咋写
 */
@Router(C.ABOUT)
public class AboutActivity extends DataBindingActivity<ActivityAboutBinding> {

    public Double[] Lines = {0.0, 100.0, 200.0, 300.0, 400.0, 500.0};
    public Double[] num0 = {200.0, 400.0, 230.0, 350.0, 210.0, 310.0, 350.0, 200.0};
    public Double[] num1 = {400.0, 480.0, 300.0, 450.0, 310.0, 500.0, 450.0, 400.0};

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initView() {
        mViewBinding.lvUser.getPresenter()
                .setDbRepository(DbFactory::getAllUser)
                .setNetRepository(ApiFactory::getAllUser).fetch();
        mViewBinding.llHeader.addView(new ChartView(this, Lines, num0, num1));
    }
}