package com.bw.movie.precenter;

import java.util.Map;

public interface IPrecenter {

    void startRequestData(String url, Map<String, String> map, Class clazz, String type);
}
