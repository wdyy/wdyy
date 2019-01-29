package com.bw.movie.movie.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.moviebean.HotMovieBean;
import com.bw.movie.bean.moviebean.LoadingMovieBean;
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
            LoadingMovieAdapter loadingMovieAdapter = new LoadingMovieAdapter(getActivity());
            loadingMovieAdapter.setData(loadingMovieBean.getResult());
            recycle_loading.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
            recycle_loading.setAdapter(loadingMovieAdapter);
        }
    }

    @Override
    public void fail(String error) {

    }
}
