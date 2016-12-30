package com.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.C;
import com.base.util.InstanceUtil;
import com.view.viewholder.CommFooterVH;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baixiaokang on 16/12/27.
 */

public class CoreAdapter<M> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected VHSelector<M> mTypeSelector;
    protected List<M> mItemList = new ArrayList<>();
    private SparseArray<Class> mMultiVHClass = new SparseArray<>();
    public boolean isHasMore = true;
    public int viewType, isHasFooter = 1, isHasHeader = 0, mHeadViewType, mFooterViewType = CommFooterVH.LAYOUT_TYPE;
    public Object mHeadData, mFootData;
    public Class<? extends BaseViewHolder> mItemViewClass, mHeadViewClass, mFooterViewClass = CommFooterVH.class;
    public Context context;

    public CoreAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return (RecyclerView.ViewHolder) InstanceUtil.getViewHolder(getVHClassByType(viewType),
                LayoutInflater.from(context).inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((BaseViewHolder) holder).onBindViewHolder(holder.itemView, getItem(position));
    }

    public Class getVHClassByType(int type) {
        Class clazz = mMultiVHClass.get(type);
        if (clazz != null) return clazz;
        else if (type == mHeadViewType) return mHeadViewClass;
        else if (type == mFooterViewType) return mFooterViewClass;
        else return mItemViewClass;
    }

    public void setViewType(int i, Class<? extends BaseViewHolder> cla) {
        this.viewType = i;
        this.mItemList = new ArrayList<>();
        this.mItemViewClass = cla;
    }

    public void setTypeSelector(VHSelector<M> mTypeSelector) {
        this.viewType = C.FLAG_MULTI_VH;
        this.mTypeSelector = mTypeSelector;
    }

    public void setHeadViewType(int i, Class<? extends BaseViewHolder> cla, Object data) {
        this.isHasHeader = cla == null ? 0 : 1;
        if (isHasHeader == 1) {
            this.isHasHeader = 1;
            this.mHeadViewType = i;
            this.mHeadViewClass = cla;
            this.mHeadData = data;
        }
    }

    public void setFooterViewType(int i, Class<? extends BaseViewHolder> cla, Object data) {
        this.isHasFooter = cla == null ? 0 : 1;
        if (isHasFooter == 1) {
            this.mFootData = data;
            this.isHasFooter = 1;
            this.mFooterViewType = i;
            this.mFooterViewClass = cla;
        }
    }

    public Object getItem(int position) {
        return isHasFooter == 1 && position + 1 == getItemCount()
                ? (mFootData == null ? isHasMore : mFootData)
                : isHasHeader == 1 && position == 0 ? mHeadData : mItemList.get(position - isHasHeader);
    }

    @Override
    public int getItemViewType(int position) {
        int mViewType = getViewType(viewType, getItem(position));
        int mFooterType = isHasFooter == 1 ? getViewType(mFooterViewType, mFootData) : mFooterViewType;
        int mHeaderType = isHasHeader == 1 ? getViewType(mHeadViewType, mHeadData) : mHeadViewType;
        return isHasHeader == 1 && position == 0 ? mHeaderType : (isHasFooter == 1 && position + 1 == getItemCount() ? mFooterType : mViewType);
    }

    public int getViewType(int viewType, Object item) {
        if (viewType == C.FLAG_MULTI_VH) {
            Class child = mTypeSelector.getTypeClass((M) item);
            BaseViewHolder childBVH = InstanceUtil.getInstance(child);
            int type = childBVH.getType();
            mMultiVHClass.put(type, child);
            return type;
        } else return viewType;
    }

    @Override
    public int getItemCount() {
        return mItemList.size() + isHasFooter + isHasHeader;
    }

    public void setBeans(List<M> data, int begin) {
        if (data == null) data = new ArrayList<>();
        this.isHasMore = data.size() >= C.PAGE_COUNT;
        if (begin > 1) this.mItemList.addAll(data);
        else this.mItemList = data;
        notifyDataSetChanged();
    }
}