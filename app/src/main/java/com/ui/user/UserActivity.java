package com.ui.user;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.widget.ImageView;

import com.C;
import com.app.annotation.apt.Extra;
import com.app.annotation.apt.Router;
import com.app.annotation.apt.SceneTransition;
import com.app.annotation.aspect.Permission;
import com.base.BaseActivity;
import com.base.util.BindingUtils;
import com.base.util.SpUtil;
import com.base.util.ToastUtil;
import com.model._User;
import com.ui.main.R;
import com.ui.main.databinding.ActivityUserBinding;

import java.io.File;

@Router(C.USER_INFO)
public class UserActivity extends BaseActivity<UserPresenter, ActivityUserBinding> implements UserContract.View {
    @Extra(C.HEAD_DATA)
    public _User user;
    @SceneTransition(C.TRANSLATE_VIEW)
    public ImageView image;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    public void initView() {
        initUser(user);
        mPresenter.initAdapterPresenter(mViewBinding.lvComment.getPresenter(), user);
        if (SpUtil.getUser() != null && TextUtils.equals(user.objectId, SpUtil.getUser().objectId)) {
            mViewBinding.fab.setImageResource(R.drawable.ic_menu_camera);
            mViewBinding.fab.setOnClickListener(v -> takePhoto());
        } else mViewBinding.fab.setOnClickListener(v -> ToastUtil.show("ok"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent mdata){
            try {
                File file = new File(getExternalCacheDir() + "user_photo.png");
                if (!file.exists()) file = new File(BindingUtils.getUrlByIntent(mContext, mdata));
                if (file.exists()) mPresenter.upLoadFace(file);
                else showMsg("照片无法打开");
            } catch (Exception e) {
                e.printStackTrace();
                showMsg("照片无法打开");
            }
    }

    @Override
    public void showMsg(String msg) {
        Snackbar.make(mViewBinding.fab, msg, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void initUser(_User user) {
        BindingUtils.loadRoundAndBgImg(mViewBinding.image, user.face, mViewBinding.imHeader);
        setTitle(user.username);
    }

    @Permission(Manifest.permission.CAMERA)
    public void takePhoto() {
        startActivityForResult(
                new Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        .putExtra(MediaStore.EXTRA_OUTPUT,
                                Uri.fromFile(new File(getExternalCacheDir()+ "user_photo.png"))),
                C.IMAGE_REQUEST_CODE);
    }
}
