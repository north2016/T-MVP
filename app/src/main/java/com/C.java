package com;

import com.data.entity.MessageInfo;
import com.data.entity._User;

/**
 * Created by baixiaokang on 16/4/23.
 */
public class C {
    //base
    public static final int PAGE_COUNT = 10;

    // intent
    public static final String HEAD_DATA = "data";
    public static final String VH_CLASS = "vh";


    public static final String INCLUDE = "include";
    public static final String CREATER = "creater";
    public static final String ARTICLE = "article";
    public static final String UID = "uId";

    public static final String ADMIN_ID = "53d9076ce4b0ef69707fc78c";
    public static final String ADMIN_FACE = "https://avatars0.githubusercontent.com/u/7598555?v=3&s=460";


    public static MessageInfo getHeaderMessageInfo() {
        MessageInfo mMessageInfo = new MessageInfo();
        mMessageInfo.message = "您好，我是本应用的开发者North，如果您有什么好的建议和反馈，可以在这里和我直接对话，谢谢你的支持哦";
        _User admin = new _User();
        admin.username = "North";
        admin.objectId = C.ADMIN_ID;
        admin.face = ADMIN_FACE;
        mMessageInfo.creater = admin;
        return mMessageInfo;
    }
}
