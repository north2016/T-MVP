package com;

import com.model.MessageInfo;
import com.model._User;

/**
 * Created by baixiaokang on 16/4/23.
 */
public class C {
    //==================API============//
    public static final String X_LC_Id = "i7j2k7bm26g7csk7uuegxlvfyw79gkk4p200geei8jmaevmx";
    public static final String X_LC_Key = "n6elpebcs84yjeaj5ht7x0eii9z83iea8bec9szerejj7zy3";
    public static final String BASE_URL = "https://leancloud.cn:443/1.1/";

    public static final String ADMIN_ID = "53d9076ce4b0ef69707fc78c";
    public static final String ADMIN_FACE = "https://avatars0.githubusercontent.com/u/7598555?v=3&s=460";
    //==================Base============//
    public static final int PAGE_COUNT = 10;
    public static final int FLAG_MULTI_VH = 0x000001;
    public static final String OPEN_TYPE = "公开";

    //==================intent============//
    public static final String TRANSLATE_VIEW = "share_img";
    public static final String TYPE = "type";
    public static final String HEAD_DATA = "data";
    public static final String VH_CLASS = "vh";
    public static final int IMAGE_REQUEST_CODE = 100;


    //==================API============//
    public static final String _CREATED_AT = "-createdAt";
    public static final String INCLUDE = "include";
    public static final String CREATER = "creater";
    public static final String UID = "uId";
    public static final String PAGE = "page";

    //==================Router============//
    public static final String HOME = "home";
    public static final String TAB = "tab";
    public static final String ARTICLE = "article";
    public static final String LOGIN = "login";
    public static final String ABOUT = "about";
    public static final String ADVISE = "advise";
    public static final String SETTING = "setting";
    public static final String USER_INFO = "userInfo";
    public static final String USER_RELEASE = "release";
    public static final String OBJECT_ID = "objectId";


    //==================static============//
    public static MessageInfo getAdminMsg() {
        MessageInfo mMessageInfo = new MessageInfo();
        mMessageInfo.message = "您好，我是本应用的开发者North，如果您有什么好的建议和反馈，可以在这里和我直接对话，谢谢你的支持哦";
        _User admin = new _User();
        admin.username = "North";
        admin.objectId = C.ADMIN_ID;
        admin.face = ADMIN_FACE;
        mMessageInfo.creater = admin;
        return mMessageInfo;
    }

    public static String[] HOME_TABS = {"公开", "民谣", "摇滚", "电子", "流行", "爵士", "独立", "故事", "新世纪", "精品推荐", "原声"};

}
