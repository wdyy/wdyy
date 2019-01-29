package com.bw.movie.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.custom.Loading_view;
import com.bw.movie.precenter.IPrecenterImpl;
import com.bw.movie.view.IView;

import java.util.Map;

public abstract class BaseFragment extends Fragment implements IView{
    private IPrecenterImpl mPrecenter;
    Loading_view loading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(getContent(), container, false);
        mPrecenter = new IPrecenterImpl(this);
        initView(view);
        initData(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void setLog(String context, String dataString){
        Log.e(context,dataString);
    }
    public abstract void initView(View view);
    public abstract void initData(View view);
    public abstract int getContent();

    public void doNetRequestData(String url, Map<String, String> map, Class clazz, String type){

        loading = new Loading_view(getActivity(),R.style.CustomDialog);
        loading.show();
        mPrecenter.startRequestData(url,map,clazz,type);
    }

    @Override
    public void onSuccess(Object data) {
        loading.dismiss();
        success(data);
    }

    @Override
    public void onFail(String error) {
        loading.dismiss();
        Toast.makeText(getActivity(),R.string.not_NetWork,Toast.LENGTH_SHORT).show();
        fail(error);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPrecenter.onDetach();
    }

    public abstract void success(Object data);
    public abstract void fail(String error);
}

