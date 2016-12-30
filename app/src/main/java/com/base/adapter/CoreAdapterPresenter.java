package com.base.adapter;

import android.util.Log;

import com.data.DataArr;
import com.data.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by baixiaokang on 16/12/27.
 */

public class CoreAdapterPresenter<T extends Repository> {
    private T mRepository;//仓库
    private Map<String, String> param = new HashMap<>();//设置仓库钥匙
    private int begin = 0;
    private final IAdapterView view;

    public interface IAdapterView {
        void setEmpty();

        void setData(DataArr response, int begin);

        void reSetEmpty();
    }

    public CoreAdapterPresenter(IAdapterView mIAdapterViewImpl) {
        this.view = mIAdapterViewImpl;
    }

    public void setRepository(T mRepository) {
        this.mRepository = mRepository;
    }

    public CoreAdapterPresenter setParam(String key, String value) {
        this.param.put(key, value);
        return this;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public void fetch() {
        begin++;
        view.reSetEmpty();
        if (mRepository == null) {
            Log.e("mRepository", "null");
            return;
        }
        mRepository.param = param;//设置仓库钥匙
        mRepository
                .getPageAt(begin)//根据仓库货物来源取出货物
                .subscribe(
                        res -> view.setData((DataArr) res, begin),
                        e -> view.setEmpty());
    }
}
