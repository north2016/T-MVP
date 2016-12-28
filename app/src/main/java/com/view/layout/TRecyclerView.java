package com.view.layout;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.C;
import com.base.BaseViewHolder;
import com.base.CoreAdapter;
import com.base.CoreAdapterPresenter;
import com.base.util.InstanceUtil;
import com.data.DataArr;
import com.ui.main.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TRecyclerView<T> extends FrameLayout implements CoreAdapterPresenter.IAdapterView {
    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.ll_emptyview)
    LinearLayout ll_emptyView;
    private LinearLayoutManager mLayoutManager;
    private Context context;
    private CoreAdapter mCommAdapter;
    private CoreAdapterPresenter mCoreAdapterPresenter;
    private boolean isRefreshable = true, isHasHeadView = false, isEmpty = false, isReverse = false;

    public TRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public TRecyclerView(Context context, AttributeSet att) {
        super(context, att);
        init(context);
    }

    public void init(Context context) {
        this.context = context;
        View layout = inflate(context, R.layout.layout_list_recyclerview, this);
        ButterKnife.bind(this, layout);
        mCoreAdapterPresenter = new CoreAdapterPresenter(this);
        initView(context);
    }

    public TRecyclerView setReverse() {
        isReverse = true;
        mLayoutManager.setStackFromEnd(true);//列表再底部开始展示，反转后由上面开始展示
        mLayoutManager.setReverseLayout(true);//列表翻转
        recyclerview.setLayoutManager(mLayoutManager);
        return this;
    }

    private void initView(Context context) {
        swiperefresh.setColorSchemeResources(android.R.color.holo_blue_bright);
        swiperefresh.setEnabled(isRefreshable);
        swiperefresh.setOnRefreshListener(() -> reFetch());
        recyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        mCommAdapter = new CoreAdapter(context);
        recyclerview.setAdapter(mCommAdapter);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            protected int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (recyclerview.getAdapter() != null
                        && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == recyclerview.getAdapter()
                        .getItemCount() && mCommAdapter.isHasMore)
                    mCoreAdapterPresenter.fetch();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int arg0, int arg1) {
                super.onScrolled(recyclerView, arg0, arg1);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
        ll_emptyView.setOnClickListener((view -> {
            isEmpty = false;
            ll_emptyView.setVisibility(View.GONE);
            swiperefresh.setVisibility(View.VISIBLE);
            reFetch();
        }));
    }

    public TRecyclerView setIsRefreshable(boolean i) {
        isRefreshable = i;
        swiperefresh.setEnabled(i);
        return this;
    }

    public TRecyclerView setHeadView(Class<? extends BaseViewHolder> cla) {
        isHasHeadView = cla == null ? false : true;
        if (!isHasHeadView) {
            this.mCommAdapter.setHeadViewType(0, cla, null);
        } else {
            int mHeadViewType = ((BaseViewHolder) (InstanceUtil.getInstance(cla))).getType();
            this.mCommAdapter.setHeadViewType(mHeadViewType, cla, ((Activity) context).getIntent().getSerializableExtra(C.HEAD_DATA));
        }
        return this;
    }


    public TRecyclerView setFooterView(Class<? extends BaseViewHolder> cla, Object data) {
        if (cla == null) {
            this.mCommAdapter.setFooterViewType(0, cla, data);
        } else {
            mCoreAdapterPresenter.setBegin(0);
            this.mCommAdapter.setFooterViewType(((BaseViewHolder) (InstanceUtil.getInstance(cla))).getType(), cla, data);
        }
        return this;
    }

    public TRecyclerView setView(Class<? extends BaseViewHolder> cla) {
        mCoreAdapterPresenter.setRepository(InstanceUtil.getRepositoryInstance(cla));
        this.mCommAdapter.setViewType(((BaseViewHolder) (InstanceUtil.getInstance(cla))).getType(), cla);
        return this;
    }

    public TRecyclerView setParam(String key, String value) {
        mCoreAdapterPresenter.setParam(key, value);
        return this;
    }

    public TRecyclerView setData(List<T> data) {
        if (isEmpty) {
            ll_emptyView.setVisibility(View.GONE);
            swiperefresh.setVisibility(View.VISIBLE);
        }
        mCommAdapter.setBeans(data, 1);
        return this;
    }

    public void reFetch() {
        mCoreAdapterPresenter.setBegin(0);
        swiperefresh.setRefreshing(true);
        mCoreAdapterPresenter.fetch();
    }

    public void fetch() {
        mCoreAdapterPresenter.fetch();
    }

    @Override
    public void setEmpty() {
        if (!isHasHeadView && !isEmpty) {
            isEmpty = true;
            ll_emptyView.setVisibility(View.VISIBLE);
            swiperefresh.setVisibility(View.GONE);
        }
    }

    @Override
    public void setData(DataArr response, int begin) {
        swiperefresh.setRefreshing(false);
        mCommAdapter.setBeans(response.results, begin);
        if (begin == 1 && (response.results == null || response.results.size() == 0))
            setEmpty();
        else if (isReverse)
            recyclerview.scrollToPosition(mCommAdapter.getItemCount() - response.results.size() - 2);
    }

    @Override
    public void reSetEmpty() {
        if (isEmpty) {
            ll_emptyView.setVisibility(View.GONE);
            swiperefresh.setVisibility(View.VISIBLE);
        }
    }
}