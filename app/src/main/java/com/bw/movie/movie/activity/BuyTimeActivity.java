package com.bw.movie.movie.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.moviebean.BuyTimeBean;
import com.bw.movie.bean.moviebean.MovieDetailsBean;
import com.bw.movie.movie.adapter.BuyTimeAdapter;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuyTimeActivity extends BaseActivity {


    @BindView(R.id.date_cinema_name)
    TextView date_cinema_name;
    @BindView(R.id.date_cinema_address)
    TextView date_cinema_address;
    @BindView(R.id.date_name)
    TextView date_name;
    @BindView(R.id.date_type)
    TextView date_type;
    @BindView(R.id.date_director)
    TextView date_director;
    @BindView(R.id.date_time)
    TextView date_time;
    @BindView(R.id.date_location)
    TextView date_location;
    @BindView(R.id.date_image)
    SimpleDraweeView date_image;
    @BindView(R.id.date_recycle)
    RecyclerView date_recycle;
    private int mMovieId;
    private int mCinemaId;

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String cinemaName = intent.getStringExtra("cinemaName");
        String cinemaAddress = intent.getStringExtra("cinemaAddress");
        mMovieId = intent.getIntExtra("movieId", 0);
        mCinemaId = intent.getIntExtra("cinemaId", 0);

        date_cinema_name.setText(cinemaName);
        date_cinema_address.setText(cinemaAddress);

        Map<String, String> map = new HashMap<>();
        doNetRequestData(String.format(Apis.URL_MOVIE_DETAILS, mMovieId), map, MovieDetailsBean.class, "get");
    }

    @Override
    public int getContent() {
        return R.layout.activity_buy_time;
    }

    @Override
    public void success(Object data) {
        if (data instanceof MovieDetailsBean){
            MovieDetailsBean detailsBean = (MovieDetailsBean) data;
            String name = detailsBean.getResult().getName();
            String movieTypes = detailsBean.getResult().getMovieTypes();
            String director = detailsBean.getResult().getDirector();
            String duration = detailsBean.getResult().getDuration();
            String placeOrigin = detailsBean.getResult().getPlaceOrigin();
            String imageUrl = detailsBean.getResult().getImageUrl();

            Uri uri = Uri.parse(imageUrl);
            date_image.setImageURI(uri);
            date_name.setText(name);
            date_type.setText("类型："+movieTypes);
            date_director.setText("导演："+director);
            date_time.setText("时长："+duration);
            date_location.setText("产地："+placeOrigin);


            Map<String, String> map = new HashMap<>();
            doNetRequestData(String.format(Apis.URL_QUERY_BYFILMID_BYCINEMAID_MOVIE,mCinemaId, mMovieId), map, BuyTimeBean.class, "get");
        }else if (data instanceof BuyTimeBean){
            BuyTimeBean timeBean = (BuyTimeBean) data;
            BuyTimeAdapter timeAdapter = new BuyTimeAdapter(this);
            date_recycle.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            date_recycle.setAdapter(timeAdapter);
            timeAdapter.setData(timeBean.getResult());
        }
    }

    @Override
    public void fail(String error) {

    }
}
