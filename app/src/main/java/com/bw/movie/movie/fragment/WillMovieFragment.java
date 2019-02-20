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
import com.bw.movie.bean.moviebean.WaitMovieBean;
import com.bw.movie.general.activity.LoginActivity;
import com.bw.movie.movie.adapter.HotMovieAdapter;
import com.bw.movie.movie.adapter.WaitMovieAdapter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: 邵文龙
 * Date: 2019/1/27 8:37
 * Description: ${DESCRIPTION}
 */
public class WillMovieFragment extends BaseFragment {
    @BindView(R.id.recycle_will)
    RecyclerView recycle_will;
    private int p=0;
    private WaitMovieAdapter mWaitMovieAdapter;

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData(View view) {
        ButterKnife.bind(this,view);
        Map<String,String> map  = new HashMap<>();
        doNetRequestData(Apis.URL_MOVIE_WAIT,map,WaitMovieBean.class,"get");
    }

    @Override
    public int getContent() {
        return R.layout.fragment_movie_will;
    }

    @Override
    public void success(Object data) {
        if (data instanceof WaitMovieBean){
            WaitMovieBean waitMovieBean = (WaitMovieBean) data;
            mWaitMovieAdapter = new WaitMovieAdapter(getActivity());
            recycle_will.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
            recycle_will.setAdapter(mWaitMovieAdapter);
            mWaitMovieAdapter.setData(waitMovieBean.getResult());

            mWaitMovieAdapter.setOnImgClickListener(new WaitMovieAdapter.OnImgClickListener() {
                @Override
                public void onImgClick(int id, WaitMovieAdapter.ViewHolder holder, int position) {
                    doNetRequestData(String.format(Apis.URL_FOLLOW_MOVIE,id),null,RegisterBean.class,"get");

                    p=position;
                }

                @Override
                public void onImgCancelClick(int id, WaitMovieAdapter.ViewHolder holder, int position) {
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

                mWaitMovieAdapter.setImgClick(null,p);

                Toast.makeText(getActivity(),registerBean.getMessage(),Toast.LENGTH_SHORT).show();
            }else if (registerBean.getMessage().equals("取消关注成功")){
                mWaitMovieAdapter.setImgCancelClick(null,p);

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
