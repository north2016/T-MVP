package com.data.entity;

import com.app.annotation.apt.Repository;
import com.base.BaseEntity;
import com.data.repository.MessageInfoRepository;

/**
 * Created by baixiaokang on 16/12/24.
 */
@Repository(clazz = MessageInfoRepository.class)
public class MessageInfo extends BaseEntity.BaseBean {

    public _User receiver;
    public String message;
    public String uId;
    public _User creater;
}
