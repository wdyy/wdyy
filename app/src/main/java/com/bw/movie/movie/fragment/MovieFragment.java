package com.bw.movie.movie.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.moviebean.HotMovieBean;
import com.bw.movie.bean.moviebean.LoadingMovieBean;
import com.bw.movie.bean.moviebean.WaitMovieBean;
import com.bw.movie.movie.activity.MovieDetailsActivity;
import com.bw.movie.movie.adapter.BannerAdapter;
import com.bw.movie.movie.adapter.MovieShowAdapter;


import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import recycler.coverflow.RecyclerCoverFlow;

/**
 * Author: 邵文龙
 * Date: 2019/1/24 20:50
 * Description: 影片Fragment
 */
public class MovieFragment extends BaseFragment {

    @BindView(R.id.success_movie_banner)
    RecyclerCoverFlow success_movie_banner;
    @BindView(R.id.movie_recycle_hot)
    RecyclerView movie_recycle_hot;
    @BindView(R.id.movie_recycle_loading)
    RecyclerView movie_recycle_loading;
    @BindView(R.id.movie_recycle_wait)
    RecyclerView movie_recycle_wait;
    private BannerAdapter bannerAdapter;
    private Intent mIntent;
    @Override
    public void initView(View view) {

    }

    @Override
    public void initData(View view) {
        ButterKnife.bind(this,view);
        mIntent= new Intent(getActivity(),MovieDetailsActivity.class);
        bannerAdapter = new BannerAdapter(getActivity());
        success_movie_banner.setAdapter(bannerAdapter);
        Map<String,String> map = new HashMap<>();
        doNetRequestData(Apis.URL_MOVIE_HOT,map,HotMovieBean.class,"get");
    }

    @OnClick({R.id.movie_image_next_hot,R.id.movie_image_next_loading,R.id.movie_image_next_wait})
    public void onImageClick(View view){
        switch (view.getId()){

            case R.id.movie_image_next_hot:

                mIntent.putExtra("hot",0);
                startActivity(mIntent);
                break;
            case R.id.movie_image_next_loading:

                mIntent.putExtra("hot",1);
                startActivity(mIntent);
                break;
            case R.id.movie_image_next_wait:
                mIntent.putExtra("hot",2);
                startActivity(mIntent);
                break;
        }
    }
    @Override
    public int getContent() {
        return R.layout.fragment_success_movie;
    }

    @Override
    public void success(Object data) {
            if (data instanceof HotMovieBean){
                HotMovieBean hotMovieBean = (HotMovieBean) data;
                MovieShowAdapter showAdapter = new MovieShowAdapter(getActivity(),0);
                movie_recycle_hot.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                showAdapter.setHotData(hotMovieBean.getResult());
                movie_recycle_hot.setAdapter(showAdapter);
                Map<String,String> map = new HashMap<>();
                doNetRequestData(Apis.URL_MOVIE_LOADING,map,LoadingMovieBean.class,"get");
            }else if (data instanceof LoadingMovieBean){
                LoadingMovieBean loadingMovieBean = (LoadingMovieBean) data;
                MovieShowAdapter showAdapter = new MovieShowAdapter(getActivity(),1);
                movie_recycle_loading.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                showAdapter.setLoadData(loadingMovieBean.getResult());
                bannerAdapter.setData(loadingMovieBean.getResult());
                movie_recycle_loading.setAdapter(showAdapter);
                Map<String,String> map = new HashMap<>();
                doNetRequestData(Apis.URL_MOVIE_WAIT,map,WaitMovieBean.class,"get");
            }else if (data instanceof WaitMovieBean){
                WaitMovieBean waitMovieBean = (WaitMovieBean) data;
                MovieShowAdapter showAdapter = new MovieShowAdapter(getActivity(),2);
                movie_recycle_wait.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                showAdapter.setWaitData(waitMovieBean.getResult());
                movie_recycle_wait.setAdapter(showAdapter);
            }
    }



    @Override
    public void fail(String error) {

    }

}
