package com.bw.movie.fragment.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.RecommendCinemaBean;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.fragment.adapter.RecommendAdapter;
import com.bw.movie.general.activity.LoginActivity;
import com.bw.movie.util.FileUtils;
import com.bw.movie.util.SpaceItemDecoration;
import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: 范瑞旗
 * Date: 2019/2/19 19:20
 * Description: 搜索展示页
 */
public class CinemaSearchActivity extends BaseActivity {

    @BindView(R.id.fragment_cinema_search_recyclerView)
    RecyclerView recyclerView;

    RecommendAdapter adapter;
    private Boolean bo=true;
    private int p=0;

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {

        Intent intent = getIntent();
        String cinemaName = intent.getStringExtra("cinemaName");
        doNetRequestData(String.format(Apis.URL_FIND_ALL_CINEMA,cinemaName),null,RecommendCinemaBean.class,"get");

    }

    @Override
    public int getContent() {
        return R.layout.fragment_cinema_search;
    }

    @Override
    public void success(Object data) {

        if (data instanceof RecommendCinemaBean){
            RecommendCinema(data);
        }else if (data instanceof RegisterBean){

            RegisterBean registerBean= (RegisterBean) data;
            if (registerBean.getMessage().equals("请先登陆")){

                /*LoginAlertDialog dialog = new LoginAlertDialog(getActivity());
                dialog.alert();*/
                //LoginAlertDialog.alert(getActivity());
                AlertDialog.Builder builder = new AlertDialog.Builder(CinemaSearchActivity.this);
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

                        startActivity(new Intent(CinemaSearchActivity.this,LoginActivity.class));
                        //startActivityS();
                    }
                });
                AlertDialog dialog = builder.create();
                //显示对话框
                dialog.show();

            }else if (registerBean.getMessage().equals("关注成功")){

                adapter.setImgClick(null,p);

                Toast.makeText(CinemaSearchActivity.this,registerBean.getMessage(),Toast.LENGTH_SHORT).show();
            }else if (registerBean.getMessage().equals("取消关注成功")){

                adapter.setImgCancelClick(null,p);

                Toast.makeText(CinemaSearchActivity.this,registerBean.getMessage(),Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(CinemaSearchActivity.this,registerBean.getMessage(),Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void fail(String error) {

    }

    private void RecommendCinema(Object data) {

        RecommendCinemaBean recommendBean = (RecommendCinemaBean) data;
        List<RecommendCinemaBean.ResultBean> result = recommendBean.getResult();

        adapter = new RecommendAdapter(CinemaSearchActivity.this, result);
        recyclerView.setAdapter(adapter);
        if (bo){
            recyclerView.addItemDecoration(new SpaceItemDecoration(14));
            bo=false;
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(CinemaSearchActivity.this,LinearLayoutManager.VERTICAL,false));

        adapter.setOnImgClickListener(new RecommendAdapter.OnImgClickListener() {
            @Override
            public void onImgClick(int id, RecommendAdapter.ViewHolder holder ,int position) {  //关注
                //Toast.makeText(getActivity(),id+"关注",Toast.LENGTH_SHORT).show();
                doNetRequestData(String.format(Apis.UEL_FOLLOW_CINEMA,id),null,RegisterBean.class,"get");

                p=position;
            }

            @Override
            public void onImgCancelClick(int id, RecommendAdapter.ViewHolder holder ,int position) {  //取消关注
                //Toast.makeText(getActivity(),id+"取消",Toast.LENGTH_SHORT).show();
                doNetRequestData(String.format(Apis.UEL_CANCEL_FOLLOW_CINEMA,id),null,RegisterBean.class,"get");

                p=position;
            }

            @Override
            public void onItemClick(int id) {

                Intent intent = new Intent(CinemaSearchActivity.this,CinemaDetailActivity.class);
                intent.putExtra("Id",id);

                startActivity(intent);

            }
        });

    }
}
