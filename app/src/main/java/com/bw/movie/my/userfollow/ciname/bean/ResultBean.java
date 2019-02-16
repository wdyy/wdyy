package com.bw.movie.my.userfollow.ciname.bean;

import com.bw.movie.base.BaseEntity;

/**
 * 郭佳兴
 */
public class ResultBean  extends BaseEntity {
    private String address;
    private int id;
    private String logo;
    private String name;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
