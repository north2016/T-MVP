package com.model;

import com.base.BaseBean;
import com.base.entity.Pointer;

/**
 * Created by baixiaokang on 17/1/21.
 */

public class Image extends BaseBean {
    public String image;
    public String article;
    public String author;
    public String title;
    public Pointer creater;

    public Image(String image, String article, String author, String title, Pointer creater) {
        this.image = image;
        this.article = article;
        this.author = author;
        this.title = title;
        this.creater = creater;
    }
}
