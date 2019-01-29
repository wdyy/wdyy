package com.bw.movie.model;

import com.bw.movie.callback.MyCallBack;

import java.util.Map;

public interface IModel {

    void requestData(String url, Map<String, String> map, Class clazz, String type, MyCallBack myCallBack);
}
