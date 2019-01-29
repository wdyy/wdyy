package com.bw.movie.model;

import android.util.Log;

import com.bw.movie.callback.MyCallBack;
import com.bw.movie.netutil.RetrofitManager;
import com.google.gson.Gson;

import java.util.Map;

public class IModelImpl implements IModel{

    @Override
    public void requestData(String url, Map<String, String> map, final Class clazz, String type, final MyCallBack myCallBack) {

        switch (type){
            case "get":

                RetrofitManager.getInstance().get(url, new RetrofitManager.HttpListener() {
                    @Override
                    public void onSuccess(String data) {

                        try {
                            Object o = new Gson().fromJson(data, clazz);
                            if (myCallBack!=null){
                                myCallBack.onSuccess(o);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Log.d("sss",e.getMessage());
                            if (myCallBack!=null){
                                myCallBack.onFail(e.getMessage());
                            }
                        }

                    }

                    @Override
                    public void onFail(String error) {
                        Log.d("sss",error);
                        if (myCallBack!=null){
                            myCallBack.onFail(error);
                        }
                    }
                });

                break;
            case "post":

                RetrofitManager.getInstance().post(url, map,new RetrofitManager.HttpListener() {
                    @Override
                    public void onSuccess(String data) {

                        try {
                            Object o = new Gson().fromJson(data, clazz);
                            if (myCallBack!=null){
                                myCallBack.onSuccess(o);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Log.d("sss",e.getMessage());
                            if (myCallBack!=null){
                                myCallBack.onFail(e.getMessage());
                            }
                        }

                    }

                    @Override
                    public void onFail(String error) {
                        Log.d("sss",error);
                        if (myCallBack!=null){
                            myCallBack.onFail(error);
                        }
                    }
                });

                break;
        }

    }



}
