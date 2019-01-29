package com.bw.movie.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.custom.Loading_view;
import com.bw.movie.precenter.IPrecenterImpl;
import com.bw.movie.view.IView;

import java.util.Map;

/**
 * Author: 范瑞旗
 * Date: 2019/1/23 16:08
 * Description: 基类Activity
 */

public abstract class BaseActivity extends AppCompatActivity implements IView{
    private IPrecenterImpl mPrecenter;
    Loading_view loading;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContent());
        mPrecenter = new IPrecenterImpl(this);
        initView();
        initData();
    }

    public void showShortToast(String text){
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }

    public void setLog(String context,String dataString){
        Log.e(context,dataString);
    }

   /* public void setStartActivity(Class isClass,boolean isboolean){
        isStartActivity(isClass,isboolean);
    }

    public void isStartActivity(Class isClass, boolean isboolean) {
        if (isboolean){

        }else {

        }
    }*/

    public void doNetRequestData(String url, Map<String, String> map, Class clazz, String type){

        loading = new Loading_view(this,R.style.CustomDialog);
        loading.show();
//        new Handler().postDelayed(new Runnable() {//定义延时任务模仿网络请求
//            @Override
//            public void run() {
//               //3秒后调用关闭加载的方法
//            }
//        }, 2000);


        mPrecenter.startRequestData(url,map,clazz,type);
    }

    public abstract void initView();
    public abstract void initData();
    public abstract int getContent();

    @Override
    public void onSuccess(Object data) {
        loading.dismiss();
        success(data);
    }

    @Override
    public void onFail(String error) {
        loading.dismiss();
        fail(error);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPrecenter.onDetach();
    }

    public abstract void success(Object data);
    public abstract void fail(String error);

}
