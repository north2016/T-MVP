package com.base.adapter;

import android.util.Log;

import com.C;
import com.base.entity.DataArr;
import com.base.Repository;

import java.util.HashMap;

/**
 * Created by baixiaokang on 16/12/27.
 */

public class AdapterPresenter {
    private Repository mRepository;//仓库
    private HashMap<String, Object> param = new HashMap<>();//设置仓库钥匙
    private int begin = 0;
    private final IAdapterView view;

    public interface IAdapterView {
        void setEmpty();

        void setData(DataArr response, int begin);

        void reSetEmpty();
    }

    public AdapterPresenter(IAdapterView mIAdapterViewImpl) {
        this.view = mIAdapterViewImpl;
    }

    public AdapterPresenter setRepository(Repository repository) {
        this.mRepository = repository;
        return this;
    }

    public AdapterPresenter setParam(String key, String value) {
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
        param.put(C.PAGE, begin);
        mRepository
                .getData(param)
                .subscribe(
                        res -> view.setData(res, begin),
                        e -> view.setEmpty());
    }
}
