package com.bw.movie.view;

public interface IView<T> {

    void onSuccess(T data);
    void onFail(String error);
}
