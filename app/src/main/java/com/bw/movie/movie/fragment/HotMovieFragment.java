package com.bw.movie.movie.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.bean.moviebean.HotMovieBean;
import com.bw.movie.movie.adapter.HotMovieAdapter;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: 邵文龙
 * Date: 2019/1/27 8:36
 * Description: ${DESCRIPTION}
 */
public class HotMovieFragment extends BaseFragment {
    @BindView(R.id.recycle_hot)
    RecyclerView recycle_hot;
    @Override
    public void initView(View view) {

    }

    @Override
    public void initData(View view) {
        ButterKnife.bind(this,view);
        Map<String,String> map  = new HashMap<>();
        doNetRequestData(Apis.URL_MOVIE_HOT,map,HotMovieBean.class,"get");
    }

    @Override
    public int getContent() {
        return R.layout.fragment_movie_hot;
    }

    @Override
    public void success(Object data) {
        if (data instanceof HotMovieBean){
            HotMovieBean hotMovieBean = (HotMovieBean) data;
            HotMovieAdapter hotMovieAdapter = new HotMovieAdapter(getActivity());
            recycle_hot.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
            recycle_hot.setAdapter(hotMovieAdapter);
            hotMovieAdapter.setData(hotMovieBean.getResult());

            hotMovieAdapter.setOnImgClickListener(new HotMovieAdapter.OnImgClickListener() {
                @Override
                public void onImgClick(int id) {
                    doNetRequestData(String.format(Apis.URL_FOLLOW_MOVIE,id),null,RegisterBean.class,"get");
                }

                @Override
                public void onImgCancelClick(int id) {
                    doNetRequestData(String.format(Apis.URL_CANCLE_FLLOW_MOVIE,id),null,RegisterBean.class,"get");
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
