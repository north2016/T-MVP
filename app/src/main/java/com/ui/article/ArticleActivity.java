package com.ui.article;

import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.C;
import com.app.annotation.apt.Extra;
import com.app.annotation.apt.Router;
import com.app.annotation.apt.SceneTransition;
import com.app.annotation.aspect.SingleClick;
import com.base.BaseActivity;
import com.base.util.BindingUtils;
import com.base.util.SpUtil;
import com.base.util.ViewUtil;
import com.model.ImageInfo;
import com.ui.main.R;
import com.ui.main.databinding.ActivityDetailBinding;

@Router(C.ARTICLE)
public class ArticleActivity extends BaseActivity<ArticlePresenter, ActivityDetailBinding> implements ArticleContract.View, View.OnClickListener {
    @Extra(C.HEAD_DATA)
    public ImageInfo mArticle;
    @SceneTransition(C.TRANSLATE_VIEW)
    public ImageView image;
    private Menu collapsedMenu;
    private boolean appBarExpanded;
    private boolean isChecked;

    @Override
    public int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public void initView() {
        BindingUtils.loadImg(mViewBinding.image, mArticle.image);
        setTitle(mArticle.title);
        mViewBinding.lvComment.setHeadData(mArticle);
        mPresenter.initAdapterPresenter(mViewBinding.lvComment.getPresenter(), mArticle);
        mViewBinding.appbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            boolean newAppBarExpanded = Math.abs(verticalOffset) <= 200;
            if (appBarExpanded != newAppBarExpanded) {
                appBarExpanded = newAppBarExpanded;
                invalidateOptionsMenu();
            }
        });
        mViewBinding.liked.setOnClickListener(v->{
            isChecked = !isChecked;
            final int[] stateSet = {android.R.attr.state_checked * (isChecked ? 1 : -1)};
            mViewBinding.liked.setImageState(stateSet, true);
        });
    }

    @Override
    public void commentSuc() {
        mViewBinding.etComment.setText("");
        mViewBinding.lvComment.reFetch();
        Snackbar.make(mViewBinding.fab, "评论成功!", Snackbar.LENGTH_LONG).show();
        ViewUtil.hideKeyboard(this);
    }

    @Override
    public void commentFail() {
        Snackbar.make(mViewBinding.fab, "评论失败!", Snackbar.LENGTH_LONG).show();
    }

    @SingleClick
    public void onClick(View view) {
        String comment = mViewBinding.etComment.getText().toString();
        if (TextUtils.isEmpty(comment))
            Snackbar.make(mViewBinding.fab, "评论不能为空!", Snackbar.LENGTH_LONG).show();
        else mPresenter.createComment(comment, mArticle, SpUtil.getUser());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        collapsedMenu = menu;
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (collapsedMenu != null && !appBarExpanded)
            collapsedMenu.add("Add").setIcon(R.drawable.ic_menu_send).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        return super.onPrepareOptionsMenu(collapsedMenu);
    }
}
