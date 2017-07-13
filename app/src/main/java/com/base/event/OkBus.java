package com.base.event;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.SparseArray;

import com.app.annotation.javassist.Bus;
import com.base.util.LogUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by baixiaokang on 16/11/15.
 */
@SuppressWarnings("unchecked")
public class OkBus {
    private ConcurrentHashMap<Integer, CopyOnWriteArrayList<SparseArray<Event>>> mEventList = new ConcurrentHashMap<>();//存储所有事件ID以及其回调
    private ConcurrentHashMap<Integer, Object> mStickyEventList = new ConcurrentHashMap<>();//存储粘连事件ID以及其数据
    private ScheduledExecutorService mPool = Executors.newScheduledThreadPool(5);
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private OkBus() {
    }

    private static class Holder {
        public static OkBus eb = new OkBus();
    }

    public static OkBus getInstance() {
        return Holder.eb;
    }

    public OkBus register(int tag, Event ev) {
        register(tag, ev, Bus.DEFAULT);
        return this;
    }

    public OkBus register(int tag, final Event ev, int thread) {
        SparseArray<Event> mEvent = new SparseArray<>();
        mEvent.put(thread, ev);
        if (mEventList.get(tag) != null) {
            mEventList.get(tag).add(mEvent);
        } else {
            CopyOnWriteArrayList<SparseArray<Event>> mList = new CopyOnWriteArrayList<>();
            mList.add(mEvent);
            mEventList.put(tag, mList);
        }
        LogUtils.e("Bus register", tag + " :" + mEventList.get(tag).size());
        if (mStickyEventList.get(tag) != null) {//注册时分发粘连事件
            final Message msg = new Message();
            msg.obj = mStickyEventList.get(tag);
            msg.what = tag;
            callEvent(msg, ev, thread);
            LogUtils.e("mStickyEvent register  and  onEvent", tag + " :" + mEventList.get(tag).size());
        }
        return this;
    }

    private void callEvent(final Message msg, final Event ev, int thread) {
        switch (thread) {
            case Bus.DEFAULT:
                ev.call(msg);
                break;
            case Bus.UI:
                mHandler.post(() -> ev.call(msg));
                break;
            case Bus.BG:
                mPool.execute(() -> ev.call(msg));
                break;
        }
    }

    /**
     * 一次性注销所有当前事件监听器
     *
     * @param ev
     * @return
     */
    public OkBus unRegister(Event ev) {
        for (int i = 0; i < mEventList.values().size(); i++) {
            CopyOnWriteArrayList<SparseArray<Event>> list = (CopyOnWriteArrayList<SparseArray<Event>>) mEventList.values().toArray()[i];
            int key = (int) mEventList.keySet().toArray()[i];
            for (SparseArray<Event> item : list) {
                if (item.indexOfValue(ev) >= 0) {
                    list.remove(item);
                    LogUtils.e("remove Event", "key :" + key + "   keys:" + item.toString());
                }
            }
        }
        return this;
    }

    public OkBus unRegister(int tag) {
        if (mEventList.get(tag) != null)
            mEventList.remove(tag);
        return this;
    }

    public OkBus onEvent(int tag, Object data) {
        Message msg = new Message();
        msg.obj = data;
        msg.what = tag;
        if (mEventList.get(tag) != null) {
            LogUtils.e("Bus onEvent", tag + " :" + mEventList.get(tag).size());
            for (SparseArray<Event> ev : mEventList.get(tag))
                callEvent(msg, ev.valueAt(0), ev.keyAt(0));
        }
        return this;
    }

    public OkBus onEvent(int tag) {
        onEvent(tag, null);
        return this;
    }

    public OkBus onStickyEvent(int tag, Object data) {
        LogUtils.e("Bus onStickyEvent", tag + " ");
        mStickyEventList.put(tag, (data == null ? tag : data));
        onEvent(tag, data);
        return this;
    }

    public OkBus onStickyEvent(int tag) {
        onStickyEvent(tag, null);
        return this;
    }
}
