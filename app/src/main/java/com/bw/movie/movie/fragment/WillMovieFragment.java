package com.bw.movie.movie.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.moviebean.HotMovieBean;
import com.bw.movie.bean.moviebean.WaitMovieBean;
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
            WaitMovieAdapter waitMovieAdapter = new WaitMovieAdapter(getActivity());
            waitMovieAdapter.setData(waitMovieBean.getResult());
            recycle_will.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
            recycle_will.setAdapter(waitMovieAdapter);
        }
    }

    @Override
    public void fail(String error) {

    }
}
