package com.bw.movie.base;

import java.io.Serializable;

public class BaseEntity implements Serializable {

    private String success;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
