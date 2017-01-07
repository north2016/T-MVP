package com.base.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.base.BaseBean;
import com.base.entity.DataArr;
import com.ui.main.R;

import java.util.List;


public class TRecyclerView<M extends BaseBean> extends FrameLayout implements AdapterPresenter.IAdapterView {
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerview;
    private LinearLayout ll_emptyView;
    private LinearLayoutManager mLayoutManager;
    private CoreAdapter<M> mCommAdapter;
    private AdapterPresenter mCoreAdapterPresenter;
    private boolean isRefreshable = true, isHasHeadView = false, isHasFootView = false, isEmpty = false, isReverse = false;

    public TRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public TRecyclerView(Context context, AttributeSet att) {
        super(context, att);
        init(context);
    }

    public AdapterPresenter getPresenter() {
        return mCoreAdapterPresenter;
    }

    public void init(Context context) {
        View layout = inflate(context, R.layout.layout_list_recyclerview, this);
        swipeRefresh = (SwipeRefreshLayout) layout.findViewById(R.id.swiperefresh);
        recyclerview = (RecyclerView) layout.findViewById(R.id.recyclerview);
        ll_emptyView = (LinearLayout) layout.findViewById(R.id.ll_emptyview);
        mCoreAdapterPresenter = new AdapterPresenter(this);
        initView(context);
    }

    public TRecyclerView<M> setReverse() {
        isReverse = true;
        mLayoutManager.setStackFromEnd(true);//列表再底部开始展示，反转后由上面开始展示
        mLayoutManager.setReverseLayout(true);//列表翻转
        recyclerview.setLayoutManager(mLayoutManager);
        return this;
    }

    private void initView(Context context) {
        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright);
        swipeRefresh.setEnabled(isRefreshable);
        swipeRefresh.setOnRefreshListener(() -> reFetch());
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
            swipeRefresh.setVisibility(View.VISIBLE);
            reFetch();
        }));
    }

    public TRecyclerView<M> setIsRefreshable(boolean i) {
        isRefreshable = i;
        swipeRefresh.setEnabled(i);
        return this;
    }


    public TRecyclerView<M> setHeadView(@LayoutRes int type, Object data) {
        isHasHeadView = type != 0;
        if (!isHasHeadView) {
            this.mCommAdapter.setHeadViewType(0, null);
        } else {
            this.mCommAdapter.setHeadViewType(type, data);
        }
        return this;
    }

    public TRecyclerView setTypeSelector(TypeSelector mTypeSelector) {
        this.mCommAdapter.setTypeSelector(mTypeSelector);
        return this;
    }

    public TRecyclerView setFooterView(@LayoutRes int type, Object data) {
        isHasFootView = type != 0;
        if (type == 0) {
            this.mCommAdapter.setFooterViewType(0, data);
        } else {
            mCoreAdapterPresenter.setBegin(0);
            this.mCommAdapter.setFooterViewType(type, data);
        }
        return this;
    }

    public TRecyclerView<M> setViewType(@LayoutRes int type) {
        this.mCommAdapter.setViewType(type);
        return this;
    }


    public TRecyclerView<M> setData(List<M> data) {
        reSetEmpty();
        mCommAdapter.setBeans(data, 1);
        return this;
    }

    public void reFetch() {
        mCoreAdapterPresenter.setBegin(0);
        swipeRefresh.setRefreshing(true);
        mCoreAdapterPresenter.fetch();
    }


    @Override
    public void setEmpty() {
        if ((!isHasHeadView || isReverse && !isHasFootView) && !isEmpty) {
            isEmpty = true;
            ll_emptyView.setVisibility(View.VISIBLE);
            swipeRefresh.setVisibility(View.GONE);
        }
    }

    @Override
    public void setData(DataArr response, int begin) {
        swipeRefresh.setRefreshing(false);
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
            swipeRefresh.setVisibility(View.VISIBLE);
        }
    }
}