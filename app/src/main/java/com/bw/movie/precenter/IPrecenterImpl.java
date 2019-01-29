package com.bw.movie.precenter;

import android.util.Log;

import com.bw.movie.callback.MyCallBack;
import com.bw.movie.model.IModelImpl;
import com.bw.movie.view.IView;
import java.util.Map;

public class IPrecenterImpl implements IPrecenter{  //MVP-P

    private IView mIView;
    private IModelImpl mIModel;

    public IPrecenterImpl(IView IView) {
        mIView = IView;
        mIModel = new IModelImpl();

    }

    @Override
    public void startRequestData(String url, Map<String, String> map, Class clazz, String type) {

        mIModel.requestData(url, map, clazz, type, new MyCallBack() {
            @Override
            public void onSuccess(Object data) {

                Log.e("hzjhzj",data+"");
                mIView.onSuccess(data);
            }


            @Override
            public void onFail(String error) {
                mIView.onFail(error);
            }
        });
    }

    public void onDetach(){
        if (mIModel!=null){
            mIModel=null;
        }

        if (mIView!=null){
            mIView=null;
        }
    }


}
