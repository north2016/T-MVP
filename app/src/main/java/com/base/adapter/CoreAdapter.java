package com.base.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.C;
import com.ui.main.BR;
import com.ui.main.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baixiaokang on 16/12/27.
 */
@SuppressWarnings("unchecked")
public class CoreAdapter<M> extends RecyclerView.Adapter<BaseViewHolder> {
    private final boolean needHint;
    private TypeSelector<M> mTypeSelector;
    private List<M> mItemList = new ArrayList<>();
    public boolean isHasMore = true;
    private List<Item> mHeadTypeDatas = new ArrayList<>();
    private List<Item> mFootTypeDatas = new ArrayList<>();
    private int viewType;
    private int mFooterViewType = R.layout.list_footer_view;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), viewType, parent, false));
    }

    CoreAdapter(boolean needHint) {
        this.needHint = needHint;
        mFootTypeDatas.add(new Item(mFooterViewType, true));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        Object item = getItem(position);
        if (needHint && holder.itemView.getTag() == null) {
            holder.itemView.setTag(item);
            holder.itemView.postDelayed(() -> {
                holder.mViewDataBinding.setVariable(BR.item, holder.itemView.getTag());
                holder.mViewDataBinding.executePendingBindings();
            }, 800);
        } else {
            if (needHint) holder.itemView.setTag(item);
            holder.mViewDataBinding.setVariable(BR.item, item);
            holder.mViewDataBinding.executePendingBindings();
        }
    }

    public void setViewType(@LayoutRes int type) {
        this.viewType = type;
    }

    public void setTypeSelector(TypeSelector mTypeSelector) {
        this.mTypeSelector = mTypeSelector;
        this.viewType = C.FLAG_MULTI_VH;
    }

    public void addHeadViewType(@LayoutRes int i, Object data) {
        mHeadTypeDatas.add(new Item(i, data));
    }

    public void addFooterViewType(@LayoutRes int i, Object data) {
        mFootTypeDatas.add(mFootTypeDatas.size() - 1, new Item(i, data));
    }

    public Object getItem(int position) {
        if (position < mHeadTypeDatas.size()) {
            return mHeadTypeDatas.get(position).data;
        } else if (position >= (mHeadTypeDatas.size() + mItemList.size())) {
            int index = position - (mHeadTypeDatas.size() + mItemList.size());
            if (mFootTypeDatas.get(index).type == mFooterViewType && !isHasMore) return false;
            else return mFootTypeDatas.get(index).data;
        } else {
            return mItemList.get(position - mHeadTypeDatas.size());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mHeadTypeDatas.size()) {
            return mHeadTypeDatas.get(position).type;
        } else if (position >= (mHeadTypeDatas.size() + mItemList.size())) {
            return mFootTypeDatas.get(position - (mHeadTypeDatas.size() + mItemList.size())).type;
        } else {
            return viewType == C.FLAG_MULTI_VH ?
                    mTypeSelector.getType((M) getItem(position)) :
                    viewType;
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size() + mHeadTypeDatas.size() + mFootTypeDatas.size();
    }

    public void setBeans(List<M> data, int begin) {
        if (data == null) data = new ArrayList<>();
        this.isHasMore = data.size() >= C.PAGE_COUNT && (data.size() > 0 && begin > 0);
        if (begin > 1) this.mItemList.addAll(data);
        else this.mItemList = data;
        notifyDataSetChanged();
    }

    public class Item {
        int type;
        Object data;

        public Item(int type, Object data) {
            this.type = type;
            this.data = data;
        }
    }
}