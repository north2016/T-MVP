package com.base;

import android.util.Log;

import com.data.Data;
import com.data.Repository;

import java.util.HashMap;
import java.util.Map;

import rx.functions.Action1;

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

        void getDataSuc(Data response, int begin);

        void reSetEmpty();
    }

    public CoreAdapterPresenter(IAdapterView mIAdapterViewImpl) {
        this.view = mIAdapterViewImpl;
    }

    public void setRepository(T mRepository) {
        this.mRepository = mRepository;
    }

    public void setParam(String key, String value) {
        this.param.put(key, value);
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
        mRepository.getPageAt(begin)//根据仓库货物来源取出货物
                .subscribe(
                        new Action1<Data>() {
                            @Override
                            public void call(Data response) {
                                view.getDataSuc(response, begin);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable e) {
                                e.printStackTrace();
                                view.setEmpty();
                            }
                        }
                );
    }
}
