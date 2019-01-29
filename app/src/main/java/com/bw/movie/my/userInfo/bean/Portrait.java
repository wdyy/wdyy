package com.bw.movie.my.userInfo.bean;


import com.bw.movie.base.BaseEntity;

/**
 * 郭佳兴
 */
public class Portrait extends BaseEntity {

    private String image;

    public Portrait(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
