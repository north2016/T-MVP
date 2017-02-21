package com.model;

import com.base.entity.Pointer;

import java.io.Serializable;

/**
 * Created by baixiaokang on 16/5/4.
 */
public class Comment implements Serializable {

    public Pointer article;
    public String content;
    public Pointer creater;

    public Comment(Pointer article, String content, Pointer creater) {
        this.article = article;
        this.content = content;
        this.creater = creater;
    }
}
