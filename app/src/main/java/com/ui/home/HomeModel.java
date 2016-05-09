package com.ui.home;

/**
 * Created by baixiaokang on 16/5/2.
 */
public class HomeModel implements HomeContract.Model {
    @Override
    public String[] getTabs() {
        String[] mTabs = {"民谣", "摇滚", "电子", "流行", "爵士", "独立", "故事", "新世纪", "精品推荐", "原声", "乐评", "影评", "古典", "游记"};
        return mTabs;//暂时不从网络取
    }
}
