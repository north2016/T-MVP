package com.base;

import android.os.Message;
import android.util.SparseArray;

import com.app.aop.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by baixiaokang on 16/11/15.
 */

public class OkBus<T> {
    private volatile SparseArray<List<Event>> mEventList = new SparseArray<>();//存储所有事件ID以及其回调
    private volatile SparseArray<Object> mStickyEventList = new SparseArray<>();//存储粘连事件ID以及其数据

    private OkBus() {
    }

    private static class Holder {
        public static OkBus eb = new OkBus();
    }

    public static OkBus getInstance() {
        return Holder.eb;
    }

    public OkBus register(int tag, Event ev) {
        if (mEventList.get(tag) != null) {
            mEventList.get(tag).add(ev);
        } else {
            List<Event> mList = new ArrayList<>();
            mList.add(ev);
            mEventList.put(tag, mList);
        }
        LogUtils.e("Bus register", tag + " :");
        if (mStickyEventList.get(tag) != null) {//注册时分发粘连事件
            Message msg = new Message();
            msg.obj = mStickyEventList.get(tag);
            msg.what = tag;
            ev.call(msg);
            LogUtils.e("mStickyEvent register  and  onEvent", tag + " :");
        }
        return this;
    }

    public OkBus unRegister(int tag) {
        if (mEventList.get(tag) != null)
            mEventList.remove(tag);
        return this;
    }

    public OkBus onEvent(int tag, T data) {
        LogUtils.e("Bus onEvent", tag + " ");
        Message msg = new Message();
        msg.obj = data;
        msg.what = tag;
        if (mEventList.get(tag) != null)
            for (Event ev : mEventList.get(tag)) ev.call(msg);
        return this;
    }

    public OkBus onStickyEvent(int tag, T data) {
        LogUtils.e("Bus onStickyEvent", tag + " ");
        mStickyEventList.put(tag, (data == null ? tag : data));
        onEvent(tag, data);
        return this;
    }
}
