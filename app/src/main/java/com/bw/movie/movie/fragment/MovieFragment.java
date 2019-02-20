package com.bw.movie.movie.fragment;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.bean.moviebean.HotMovieBean;
import com.bw.movie.bean.moviebean.LoadingMovieBean;
import com.bw.movie.bean.moviebean.WaitMovieBean;
import com.bw.movie.general.activity.LoginActivity;
import com.bw.movie.movie.activity.ImageViewAnimationHelper;
import com.bw.movie.movie.activity.MovieDetailsActivity;
import com.bw.movie.movie.adapter.BannerAdapter;
import com.bw.movie.movie.adapter.MovieShowAdapter;


import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import recycler.coverflow.CoverFlowLayoutManger;
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
    @BindView(R.id.checked_layout)
    LinearLayout checked_layout;
    @BindView(R.id.cinema_linearLayout_search)
    LinearLayout linearLayout;
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
        success_movie_banner.smoothScrollToPosition(4);
        final ImageViewAnimationHelper imageViewAnimationHelper = new ImageViewAnimationHelper(getContext(), checked_layout, 1, 31);
        success_movie_banner.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
                imageViewAnimationHelper.startAnimation(position);
            }
        });

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
    @OnClick(R.id.cinema_search_img)
    public void onImgClickListener(){

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(linearLayout, "translationX", 0f, -320f);
//      设置移动时间
        objectAnimator.setDuration(1000);
//      开始动画
        objectAnimator.start();
    }

    @OnClick(R.id.cinema_search_text)
    public void onTextClickListener(){

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(linearLayout, "translationX", -320f, 0f);
//      设置移动时间
        objectAnimator.setDuration(1000);
//      开始动画
        objectAnimator.start();
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
                movie_recycle_hot.setAdapter(showAdapter);
                showAdapter.setHotData(hotMovieBean.getResult());

                Map<String,String> map = new HashMap<>();
                doNetRequestData(Apis.URL_MOVIE_LOADING,map,LoadingMovieBean.class,"get");
            }else if (data instanceof LoadingMovieBean){
                LoadingMovieBean loadingMovieBean = (LoadingMovieBean) data;
                MovieShowAdapter showAdapter = new MovieShowAdapter(getActivity(),1);
                movie_recycle_loading.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                movie_recycle_loading.setAdapter(showAdapter);
                showAdapter.setLoadData(loadingMovieBean.getResult());
                bannerAdapter.setData(loadingMovieBean.getResult());

                Map<String,String> map = new HashMap<>();
                doNetRequestData(Apis.URL_MOVIE_WAIT,map,WaitMovieBean.class,"get");
            }else if (data instanceof WaitMovieBean){
                WaitMovieBean waitMovieBean = (WaitMovieBean) data;
                MovieShowAdapter showAdapter = new MovieShowAdapter(getActivity(),2);
                movie_recycle_wait.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
                movie_recycle_wait.setAdapter(showAdapter);
                showAdapter.setWaitData(waitMovieBean.getResult());

            }else if (data instanceof RegisterBean){
                RegisterBean registerBean= (RegisterBean) data;
                Toast.makeText(getActivity(),registerBean.getMessage(),Toast.LENGTH_SHORT).show();

            }
    }



    @Override
    public void fail(String error) {

    }

}
