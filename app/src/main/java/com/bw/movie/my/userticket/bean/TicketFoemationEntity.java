package com.bw.movie.my.userticket.bean;

import com.bw.movie.base.BaseEntity;

import java.util.List;

/**
 * 郭佳兴
 * 购票记录
 */
public class TicketFoemationEntity extends BaseEntity {

    private String message;
    private String status;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }
}
