package com.data.entity;

import com.app.annotation.apt.InstanceFactory;
import com.base.BaseBean;
import com.data.repository.MessageInfoRepository;

/**
 * Created by baixiaokang on 16/12/24.
 */
@InstanceFactory(clazz = MessageInfoRepository.class)
public class MessageInfo extends BaseBean {

    public _User receiver;
    public String message;
    public String uId;
    public _User creater;
}
