package com.bw.movie.movie.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.moviebean.BuyCinemaBean;
import com.bw.movie.movie.adapter.BuyCinemaAdapter;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuyCinemaActivity extends BaseActivity {


    @BindView(R.id.buy_movie_name)
    TextView buy_movie_name;
    @BindView(R.id.buy_cinema_recycle)
    RecyclerView buy_cinema_recycle;
    private String mMoviename;
    private int mMovieId;

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mMoviename = intent.getStringExtra("moviename");
        mMovieId = intent.getIntExtra("movieId", 0);
        buy_movie_name.setText(mMoviename);
        Map<String,String> map = new HashMap<>();
        doNetRequestData(String.format(Apis.URL_QUERY_FILMID_CINEMA,mMovieId),map,BuyCinemaBean.class,"get");
    }

    @Override
    public int getContent() {
        return R.layout.activity_buy_cinema;
    }

    @Override
    public void success(Object data) {
        if (data instanceof BuyCinemaBean){
            BuyCinemaBean cinemaBean = (BuyCinemaBean) data;
            BuyCinemaAdapter cinemaAdapter = new BuyCinemaAdapter(this,mMovieId);
            buy_cinema_recycle.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            buy_cinema_recycle.setAdapter(cinemaAdapter);
            cinemaAdapter.setData(cinemaBean.getResult());

        }
    }

    @Override
    public void fail(String error) {

    }
}
