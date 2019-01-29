package com.bw.movie.my.updateuserinfo.bean;


import com.bw.movie.base.BaseEntity;

/*
*  修改用户信息bean类
* */
public class UpDateUserInfoEntity extends BaseEntity {

    private ResultBean result;
    private String message;
    private String status;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
