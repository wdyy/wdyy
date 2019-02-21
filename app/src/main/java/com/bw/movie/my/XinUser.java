package com.bw.movie.my;

import com.bw.movie.base.BaseEntity;

/**
 * 郭佳兴
 */
public class XinUser extends BaseEntity {

    private String message;
    private String status;

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
