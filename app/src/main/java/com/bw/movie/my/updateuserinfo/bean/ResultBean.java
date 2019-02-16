package com.bw.movie.my.updateuserinfo.bean;


import com.bw.movie.base.BaseEntity;

public class ResultBean extends BaseEntity {
    private int id;
    private String nickName;
    private int sex;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
