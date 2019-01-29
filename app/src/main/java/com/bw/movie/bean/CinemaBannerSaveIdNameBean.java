package com.bw.movie.bean;

/**
 * Author: 范瑞旗
 * Date: 2019/1/28 11:08
 * Description: 获取影院详情页轮播图 影片Id和影片Name
 */
public class CinemaBannerSaveIdNameBean {

    private int id;
    private String name;

    public CinemaBannerSaveIdNameBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
