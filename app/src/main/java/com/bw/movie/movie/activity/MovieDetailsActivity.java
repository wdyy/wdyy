package com.bw.movie.movie.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.moviebean.HotMovieBean;
import com.bw.movie.bean.moviebean.LoadingMovieBean;
import com.bw.movie.bean.moviebean.WaitMovieBean;
import com.bw.movie.cinema.CinemaFragment;
import com.bw.movie.movie.fragment.HotMovieFragment;
import com.bw.movie.movie.fragment.LoadingMovieFragment;
import com.bw.movie.movie.fragment.MovieFragment;
import com.bw.movie.movie.fragment.WillMovieFragment;
import com.bw.movie.my.MineFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieDetailsActivity extends BaseActivity {

    @BindView(R.id.movie_details_button_hot)
    RadioButton movie_details_button_hot;
    @BindView(R.id.movie_details_button_loading)
    RadioButton movie_details_button_loading;
    @BindView(R.id.movie_details_button_will)
    RadioButton movie_details_button_will;
    @BindView(R.id.movie_image_return)
    ImageView movie_image_return;
    private FragmentManager mManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        Intent intent = getIntent();
        int hot = intent.getIntExtra("hot", 0);
        switch (hot){
            case 0:
                movie_details_button_hot.setChecked(true);
                movie_details_button_hot.setTextColor(Color.WHITE);
                movie_details_button_loading.setTextColor(Color.BLACK);
                movie_details_button_will.setTextColor(Color.BLACK);
                mManager = getSupportFragmentManager();
                mManager.beginTransaction().replace(R.id.movie_details,new HotMovieFragment()).commit();
                break;
            case 1:
                movie_details_button_loading.setChecked(true);
                movie_details_button_hot.setTextColor(Color.BLACK);
                movie_details_button_loading.setTextColor(Color.WHITE);
                movie_details_button_will.setTextColor(Color.BLACK);
                mManager = getSupportFragmentManager();
                mManager.beginTransaction().replace(R.id.movie_details,new LoadingMovieFragment()).commit();
                break;
            case 2:
                movie_details_button_will.setChecked(true);
                movie_details_button_hot.setTextColor(Color.BLACK);
                movie_details_button_loading.setTextColor(Color.BLACK);
                movie_details_button_will.setTextColor(Color.WHITE);
                mManager = getSupportFragmentManager();
                mManager.beginTransaction().replace(R.id.movie_details,new WillMovieFragment()).commit();
                break;
        }

    }
    @OnClick({R.id.movie_details_button_hot,R.id.movie_details_button_loading,R.id.movie_details_button_will,R.id.movie_image_return})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.movie_details_button_hot:
                movie_details_button_hot.setTextColor(Color.WHITE);
                movie_details_button_loading.setTextColor(Color.BLACK);
                movie_details_button_will.setTextColor(Color.BLACK);
                mManager = getSupportFragmentManager();
                mManager.beginTransaction().replace(R.id.movie_details,new HotMovieFragment()).commit();
                break;
            case R.id.movie_details_button_loading:
                movie_details_button_hot.setTextColor(Color.BLACK);
                movie_details_button_loading.setTextColor(Color.WHITE);
                movie_details_button_will.setTextColor(Color.BLACK);
                mManager = getSupportFragmentManager();
                mManager.beginTransaction().replace(R.id.movie_details,new LoadingMovieFragment()).commit();
                break;
            case R.id.movie_details_button_will:
                movie_details_button_hot.setTextColor(Color.BLACK);
                movie_details_button_loading.setTextColor(Color.BLACK);
                movie_details_button_will.setTextColor(Color.WHITE);
                mManager = getSupportFragmentManager();
                mManager.beginTransaction().replace(R.id.movie_details,new WillMovieFragment()).commit();
                break;
            case R.id.movie_image_return:
                finish();
                break;
        }
    }
    @Override
    public int getContent() {
        return R .layout.activity_movie_details;
    }

    @Override
    public void success(Object data) {

    }

    @Override
    public void fail(String error) {

    }
}
