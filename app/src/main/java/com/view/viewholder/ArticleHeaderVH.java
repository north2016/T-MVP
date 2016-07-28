package com.view.viewholder;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.base.BaseViewHolder;
import com.data.entity.Image;
import com.ui.main.R;

import butterknife.Bind;

/**
 * Created by baixiaokang on 16/5/4.
 */
public class ArticleHeaderVH extends BaseViewHolder<Image> {
    @Bind(R.id.tv_article)
    TextView tv_article;

    public ArticleHeaderVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.list_item_article;
    }

    @Override
    public void onBindViewHolder(View view, Image obj) {
        String article = obj.article.replace("<br>", "\n").replaceAll(" ", "").replaceAll("//", "");
        if (!TextUtils.isEmpty(article)) {
            article = article.substring(article.indexOf("&gt;") + 4, article.length());
            tv_article.setText(article);
        }
    }
}
