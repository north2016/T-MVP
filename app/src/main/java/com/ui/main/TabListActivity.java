package com.ui.main;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.C;
import com.EventTags;
import com.app.annotation.apt.Router;
import com.base.DataBindingActivity;
import com.base.event.OkBus;
import com.base.util.SpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ui.main.databinding.ActivityTablistBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Router(C.TAB)
public class TabListActivity extends DataBindingActivity<ActivityTablistBinding> {
    private ItemTouchHelper mItemTouchHelper;
    private RecyclerListAdapter adapter;
    private final List<String> mItems = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_tablist;
    }

    @Override
    public int getMenuId() {
        return R.menu.menu_tab;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.action_save) {
            SpUtil.setData(C.TAB, new Gson().toJson(mItems));
        } else if (item.getItemId() == R.id.action_refresh) {
            mItems.clear();
            mItems.addAll(Arrays.asList(C.HOME_TABS));
            adapter.notifyDataSetChanged();
            SpUtil.setData(C.TAB, new Gson().toJson(mItems));
        }
        OkBus.getInstance().onEvent(EventTags.SHOW_TAB_LIST, mItems);
        return true;
    }

    @Override
    public void initView() {
        adapter = new RecyclerListAdapter();
        mViewBinding.recyclerview.setHasFixedSize(true);
        mViewBinding.recyclerview.setAdapter(adapter);
        mItemTouchHelper = new ItemTouchHelper(new SimpleItemTouchHelperCallback());
        mItemTouchHelper.attachToRecyclerView(mViewBinding.recyclerview);
    }

    public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder> {

        RecyclerListAdapter() {
            if (TextUtils.isEmpty(SpUtil.getData(C.TAB))) {
                mItems.addAll(Arrays.asList(C.HOME_TABS));
            } else {
                mItems.addAll(new Gson().fromJson(SpUtil.getData(C.TAB), new TypeToken<List<String>>() {
                }.getType()));
            }
        }

        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_tab, parent, false));
        }

        @Override
        public void onBindViewHolder(final ItemViewHolder holder, int position) {
            holder.textView.setText(mItems.get(position));
            holder.handleView.setOnTouchListener((v, event) -> {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN)
                    mItemTouchHelper.startDrag(holder);
                return false;
            });
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        class ItemViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            ImageView handleView;

            ItemViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.text);
                handleView = (ImageView) itemView.findViewById(R.id.handle);
            }
        }
    }

    public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
            if (source.getItemViewType() != target.getItemViewType()) return false;
            Collections.swap(mItems, source.getAdapterPosition(), target.getAdapterPosition());
            adapter.notifyItemMoved(source.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
            mItems.remove(viewHolder.getAdapterPosition());
            adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                final float alpha = 1.0f - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
                viewHolder.itemView.setAlpha(alpha);
                viewHolder.itemView.setTranslationX(dX);
            } else {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE)
                viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setAlpha(1.0f);
            viewHolder.itemView.setBackgroundColor(0);
        }
    }
}
