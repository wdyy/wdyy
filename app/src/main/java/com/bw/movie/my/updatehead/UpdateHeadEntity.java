package com.bw.movie.my.updatehead;

import com.bw.movie.base.BaseEntity;

/**
 * 郭佳兴
 * 修改头像bean类
 */
public class UpdateHeadEntity extends BaseEntity {

    private String headPath;
    private String message;
    private String status;

    public String getHeadPath() {
        return headPath;
    }

    public void setHeadPath(String headPath) {
        this.headPath = headPath;
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
