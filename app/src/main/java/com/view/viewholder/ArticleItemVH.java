package com.view.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.C;
import com.app.annotation.apt.InstanceFactory;
import com.app.annotation.aspect.SingleClick;
import com.apt.TRouter;
import com.base.adapter.BaseViewHolder;
import com.base.util.ImageUtil;
import com.data.bean.ExtraData;
import com.data.entity.Image;
import com.ui.main.R;

import butterknife.Bind;

/**
 * Created by baixiaokang on 16/4/23.
 */
@InstanceFactory(InstanceFactory.typeVH)
public class ArticleItemVH extends BaseViewHolder<Image> implements View.OnClickListener {
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.tv_des)
    TextView tv_des;
    @Bind(R.id.tv_info)
    TextView tv_info;
    @Bind(R.id.tv_time)
    TextView tv_time;

    public ArticleItemVH(View v) {
        super(v);
    }

    @Override
    public int getType() {
        return R.layout.list_item_card_main;
    }

    @Override
    public void onBindViewHolder(View view, Image data) {
        super.onBindViewHolder(view, data);
        ImageUtil.loadImg(image, data.image);
        tv_title.setText(data.title);
        tv_des.setText(data.author);
        tv_info.setText(data.type);
        tv_time.setText(data.createdAt);
        view.setOnClickListener(this);
    }

    @SingleClick
    public void onClick(View view) {
        TRouter.go(C.ARTICLE, new ExtraData(C.HEAD_DATA, data).build(), image);
    }
}
