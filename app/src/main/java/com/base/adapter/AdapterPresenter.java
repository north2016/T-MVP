package com.base.adapter;

import android.util.Log;

import com.App;
import com.C;
import com.base.DbRepository;
import com.base.NetRepository;
import com.base.util.NetWorkUtil;

import java.util.HashMap;
import java.util.List;

/**
 * Created by baixiaokang on 16/12/27.
 */
@SuppressWarnings("unchecked")
public class AdapterPresenter<M> {
    private NetRepository mNetRepository;//仓库
    private HashMap<String, Object> param = new HashMap<>();//设置远程网络仓库钥匙
    private DbRepository mDbRepository;
    private int begin = 0;
    private final IAdapterView<M> view;

    interface IAdapterView<M> {
        void setEmpty();

        void setNetData(List<M> data, int begin);

        void setDBData(List<M> data);

        void reSetEmpty();
    }

    AdapterPresenter(IAdapterView mIAdapterViewImpl) {
        this.view = mIAdapterViewImpl;
    }

    public HashMap<String, Object> getParam() {
        return param;
    }

    public AdapterPresenter setNetRepository(NetRepository netRepository) {
        this.mNetRepository = netRepository;
        return this;
    }

    public AdapterPresenter setParam(String key, Object value) {
        this.param.put(key, value);
        return this;
    }

    public AdapterPresenter setDbRepository(DbRepository mDbRepository) {
        this.mDbRepository = mDbRepository;
        return this;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public void fetch() {
        if (!NetWorkUtil.isNetConnected(App.getAppContext())) {
            getDbData();
            return;
        }
        begin++;
        view.reSetEmpty();
        if (mNetRepository == null) {
            Log.e("mNetRepository", "null");
            return;
        }
        param.put(C.PAGE, begin);
        mNetRepository
                .getData(param)
                .subscribe(res -> view.setNetData(res.results, begin),
                        err -> getDbData());
    }

    private void getDbData() {
        if (mDbRepository != null)
            mDbRepository
                    .getData(param)
                    .subscribe(
                            r -> view.setDBData((List<M>) r),
                            e -> view.setEmpty());
        else view.setEmpty();
    }
}
