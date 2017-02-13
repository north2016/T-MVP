package com.ui.release;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.App;
import com.app.annotation.aspect.CheckLogin;
import com.apt.ApiFactory;
import com.base.util.ApiUtil;
import com.base.util.SpUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.model.Image;
import com.model._User;

/**
 * Created by baixiaokang on 17/1/21.
 */

public class ReleasePresenter extends ReleaseContract.Presenter {

    @CheckLogin
    public void upArticle(String url, String title, String content) {
        Glide.with(App.getAppContext()).load(url).asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                        if (bitmap == null) {
                            mView.showMsg("大哥，你敢放个真图片吗？");
                            return;
                        }
                        _User user = SpUtil.getUser();
                        ApiFactory.createArticle(
                                new Image(url, content, user.username, title, ApiUtil.getPointer(user)))
                                .subscribe(
                                        res -> mView.releaseSuc(),
                                        e -> mView.showMsg(e.getMessage()));
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        mView.showMsg("大哥，你敢放个真图片吗？");
                    }
                });
    }
}
