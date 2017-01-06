package com.ui.user;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.widget.ImageView;

import com.C;
import com.app.annotation.apt.Extra;
import com.app.annotation.apt.Router;
import com.app.annotation.apt.SceneTransition;
import com.base.BaseActivity;
import com.base.util.ImageUtil;
import com.base.util.SpUtil;
import com.base.util.ToastUtil;
import com.data.Pointer;
import com.data.entity._User;
import com.data.repository.CommentInfoRepository;
import com.google.gson.Gson;
import com.ui.main.R;
import com.view.layout.TRecyclerView;

import java.io.File;

import butterknife.Bind;

@Router(C.USER_INFO)
public class UserActivity extends BaseActivity<UserPresenter> implements UserContract.View {
    @Extra(C.HEAD_DATA)
    public _User user;
    @Bind(R.id.image)
    @SceneTransition(C.TRANSLATE_VIEW)
    public ImageView image;
    @Bind(R.id.fab)
    FloatingActionButton fab;
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
        initUser(user);
        String creater = new Gson().toJson(new Pointer(_User.class.getSimpleName(), user.objectId));
        lv_comment.setViewAndRepository(R.layout.list_item_user_comment, CommentInfoRepository.class)
                .setParam(C.INCLUDE, C.ARTICLE)
                .setParam(C.CREATER, creater)
                .setIsRefreshable(false)
                .fetch();

        if (SpUtil.getUser() != null && TextUtils.equals(user.objectId, SpUtil.getUser().objectId)) {
            fab.setImageResource(R.drawable.ic_menu_camera);
            fab.setOnClickListener(v -> startActivityForResult(new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT),
                    C.IMAGE_REQUEST_CODE));
        } else fab.setOnClickListener(v -> ToastUtil.show("ok"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent mdata) {
        if (mdata != null && requestCode == C.IMAGE_REQUEST_CODE) {
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
    public void showMsg(String msg) {
        Snackbar.make(fab, msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void initUser(_User user) {
        ImageUtil.loadRoundAndBgImg(image, user.face, im_header);
        setTitle(user.username);
    }
}
