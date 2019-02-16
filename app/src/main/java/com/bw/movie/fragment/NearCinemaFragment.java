package com.bw.movie.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.RecommendCinemaBean;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.fragment.activity.CinemaDetailActivity;
import com.bw.movie.fragment.adapter.RecommendAdapter;
import com.bw.movie.util.SpaceItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: 范瑞旗
 * Date: 2019/1/26 13:54
 * Description: 附近影院
 */
public class NearCinemaFragment extends BaseFragment {

    @BindView(R.id.fragment_cinema_near_recyclerView)
    RecyclerView recyclerView;

    private Boolean bo=true;

    @Override
    public void initView(View view) {

        ButterKnife.bind(this,view);
    }

    @Override
    public void initData(View view) {

        doNetRequestData(Apis.UEL_FIND_NEARLY_CINEMAS,null,RecommendCinemaBean.class,"get");

    }

    @Override
    public int getContent() {
        return R.layout.fragment_cinema_near;
    }

    @Override
    public void success(Object data) {

        if (data instanceof RecommendCinemaBean){
            RecommendCinemaBean recommendBean = (RecommendCinemaBean) data;
            List<RecommendCinemaBean.ResultBean> result = recommendBean.getResult();

            RecommendAdapter adapter = new RecommendAdapter(getActivity(), result);
            recyclerView.setAdapter(adapter);
            if (bo){
                recyclerView.addItemDecoration(new SpaceItemDecoration(14));
                bo=false;
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

            adapter.setOnImgClickListener(new RecommendAdapter.OnImgClickListener() {
                @Override
                public void onImgClick(int id) {  //关注
                    //Toast.makeText(getActivity(),id+"关注",Toast.LENGTH_SHORT).show();
                    doNetRequestData(String.format(Apis.UEL_FOLLOW_CINEMA,id),null,RegisterBean.class,"get");

                }

                @Override
                public void onImgCancelClick(int id) {  //取消关注
                    //Toast.makeText(getActivity(),id+"取消",Toast.LENGTH_SHORT).show();
                    doNetRequestData(String.format(Apis.UEL_CANCEL_FOLLOW_CINEMA,id),null,RegisterBean.class,"get");

                }

                @Override
                public void onItemClick(int id) {

                    Intent intent = new Intent(getActivity(),CinemaDetailActivity.class);
                    intent.putExtra("Id",id);

                    startActivity(intent);

                }
            });
        }else if (data instanceof RegisterBean){

            RegisterBean registerBean= (RegisterBean) data;
            Toast.makeText(getActivity(),registerBean.getMessage(),Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public void fail(String error) {

    }
}
