package com.data.entity;

import com.app.annotation.apt.Repository;
import com.base.BaseBean;
import com.data.repository.ImageRepository;

/**
 * Created by Administrator on 2016/4/7.
 */
@Repository(clazz= ImageRepository.class)
public class Image extends BaseBean {
    public String image;
    public String createdAt;
    public String article;
    public String author;
    public String title;
    public String type;
}
