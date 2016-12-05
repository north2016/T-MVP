package com.ui.user;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.ImageView;

import com.C;
import com.base.BaseActivity;
import com.base.util.ImageUtil;
import com.base.util.SpUtil;
import com.base.util.ToastUtil;
import com.data.Pointer;
import com.data.entity._User;
import com.google.gson.Gson;
import com.ui.main.R;
import com.view.layout.TRecyclerView;
import com.view.viewholder.UserCommentVH;

import java.io.File;

import butterknife.Bind;

public class UserActivity extends BaseActivity<UserPresenter, UserModel> implements UserContract.View {
    public static final String TRANSLATE_VIEW = "share_img";
    private static final int IMAGE_REQUEST_CODE = 100;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.lv_comment)
    TRecyclerView lv_comment;
    @Bind(R.id.im_header)
    ImageView im_header;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    public void initView() {
        _User user = (_User) getIntent().getSerializableExtra(C.HEAD_DATA);
        initUser(user);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ViewCompat.setTransitionName(image, TRANSLATE_VIEW);
        String creater = new Gson().toJson(new Pointer(_User.class.getSimpleName(), user.objectId));
        lv_comment.setView(UserCommentVH.class)
                .setParam("include", "article")
                .setParam("creater", creater)
                .setIsRefreshable(false)
                .fetch();

        if (SpUtil.getUser() != null && TextUtils.equals(user.objectId, SpUtil.getUser().objectId)) {
            fab.setImageResource(R.drawable.ic_menu_camera);
            fab.setOnClickListener(v -> startActivityForResult(new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT),
                    IMAGE_REQUEST_CODE));
        } else fab.setOnClickListener(v -> ToastUtil.show("ok"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent mdata) {
        if (mdata != null && requestCode == IMAGE_REQUEST_CODE) {
            try {
                File file = new File(ImageUtil.getUrlByIntent(mContext, mdata));
                if (file.exists())
                    mPresenter.upLoadFace(file);
                else showMsg("照片无法打开");
            } catch (Exception e) {
                e.printStackTrace();
                showMsg("照片无法打开");
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showMsg(String msg) {
        Snackbar.make(fab, msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void initUser(_User user) {
        ImageUtil.loadRoundAndBgImg(image, user.face, im_header);
        setTitle(user.username);
    }
}
