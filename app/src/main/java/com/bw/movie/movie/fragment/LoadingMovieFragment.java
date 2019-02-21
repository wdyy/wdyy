package com.bw.movie.movie.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.bean.moviebean.HotMovieBean;
import com.bw.movie.bean.moviebean.LoadingMovieBean;
import com.bw.movie.general.activity.LoginActivity;
import com.bw.movie.movie.adapter.HotMovieAdapter;
import com.bw.movie.movie.adapter.LoadingMovieAdapter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: 邵文龙
 * Date: 2019/1/27 8:37
 * Description: ${DESCRIPTION}
 */
public class LoadingMovieFragment extends BaseFragment {
    @BindView(R.id.recycle_loading)
    RecyclerView recycle_loading;
    private int p=0;
    private LoadingMovieAdapter mLoadingMovieAdapter;

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData(View view) {
        ButterKnife.bind(this,view);
        Map<String,String> map  = new HashMap<>();
        doNetRequestData(Apis.URL_MOVIE_LOADING,map,LoadingMovieBean.class,"get");
    }

    @Override
    public int getContent() {
        return R.layout.fragment_movie_loading;
    }

    @Override
    public void success(Object data) {
        if (data instanceof LoadingMovieBean){
            LoadingMovieBean loadingMovieBean = (LoadingMovieBean) data;
            mLoadingMovieAdapter = new LoadingMovieAdapter(getActivity());
            recycle_loading.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
            recycle_loading.setAdapter(mLoadingMovieAdapter);
            mLoadingMovieAdapter.setData(loadingMovieBean.getResult());

            mLoadingMovieAdapter.setOnImgClickListener(new LoadingMovieAdapter.OnImgClickListener() {
                @Override
                public void onImgClick(int id, LoadingMovieAdapter.ViewHolder holder, int position) {
                    doNetRequestData(String.format(Apis.URL_FOLLOW_MOVIE,id),null,RegisterBean.class,"get");

                    p=position;
                }

                @Override
                public void onImgCancelClick(int id, LoadingMovieAdapter.ViewHolder holder, int position) {
                    doNetRequestData(String.format(Apis.URL_CANCLE_FLLOW_MOVIE,id),null,RegisterBean.class,"get");

                    p=position;
                }
            });


        }else if (data instanceof RegisterBean){

            RegisterBean registerBean= (RegisterBean) data;
            if (registerBean.getMessage().equals("请先登陆")){

                /*LoginAlertDialog dialog = new LoginAlertDialog(getActivity());
                dialog.alert();*/
                //LoginAlertDialog.alert(getActivity());
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                builder.setTitle("提示：");
                builder.setMessage("请先登录！");
                builder.setIcon(R.mipmap.ic_launcher_round);
                //点击对话框以外的区域是否让对话框消失
                builder.setCancelable(true);
                //设置正面按钮
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                //设置反面按钮
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(getActivity(),LoginActivity.class));
                        //startActivityS();
                    }
                });
                android.support.v7.app.AlertDialog dialog = builder.create();
                //显示对话框
                dialog.show();

            }else if (registerBean.getMessage().equals("关注成功")){

                mLoadingMovieAdapter.setImgClick(null,p);

                Toast.makeText(getActivity(),registerBean.getMessage(),Toast.LENGTH_SHORT).show();
            }else if (registerBean.getMessage().equals("取消关注成功")){
                mLoadingMovieAdapter.setImgCancelClick(null,p);

                Toast.makeText(getActivity(),registerBean.getMessage(),Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(getActivity(),registerBean.getMessage(),Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void fail(String error) {

    }
}
