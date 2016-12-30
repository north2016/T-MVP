package com.data.entity;

import com.app.annotation.apt.InstanceFactory;
import com.base.BaseBean;
import com.data.repository.CommentInfoRepository;

/**
 * Created by baixiaokang on 16/5/4.
 */
@InstanceFactory(clazz= CommentInfoRepository.class)
public class CommentInfo extends BaseBean {
    public Image article;
    public String content;
    public _User creater;
}
