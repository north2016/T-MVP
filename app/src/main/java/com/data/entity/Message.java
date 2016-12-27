package com.data.entity;

import com.base.BaseEntity;
import com.data.Pointer;

/**
 * Created by baixiaokang on 16/12/24.
 */

public class Message extends BaseEntity.BaseBean {

    public Pointer receiver;
    public String message;
    public String uId;
    public Pointer creater;

    public Message(Pointer receiver, String message, Pointer creater, String uId) {
        this.receiver = receiver;
        this.message = message;
        this.creater = creater;
        this.uId = uId;
    }
}