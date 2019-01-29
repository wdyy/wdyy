package com.bw.movie.general.activity;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.cinema.CinemaFragment;
import com.bw.movie.my.MineFragment;
import com.bw.movie.movie.fragment.MovieFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuccessActivity extends BaseActivity {

    @BindView(R.id.success_button_movie)
    ImageView mSuccess_button_movie;
    @BindView(R.id.success_button_movies)
    ImageView mSuccess_button_movies;
    @BindView(R.id.success_button_cinema)
    ImageView mSuccess_button_cinema;
    @BindView(R.id.success_button_cinemas)
    ImageView mSuccess_button_cinemas;
    @BindView(R.id.success_button_mine)
    ImageView mSuccess_button_mine;
    @BindView(R.id.success_button_mines)
    ImageView mSuccess_button_mines;
    private FragmentManager mManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mManager = getSupportFragmentManager();
        mManager.beginTransaction().replace(R.id.three_frag,new MovieFragment()).commit();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
    }
    @OnClick({R.id.success_button_movie,R.id.success_button_cinema,R.id.success_button_mine})
    public void onButtonClick(View view){
        switch (view.getId()){
            case R.id.success_button_movie:
                mSuccess_button_movie.setVisibility(View.INVISIBLE);
                mSuccess_button_movies.setVisibility(View.VISIBLE);
                mSuccess_button_cinema.setVisibility(View.VISIBLE);
                mSuccess_button_cinemas.setVisibility(View.INVISIBLE);
                mSuccess_button_mine.setVisibility(View.VISIBLE);
                mSuccess_button_mines.setVisibility(View.INVISIBLE);

                mManager = getSupportFragmentManager();
                mManager.beginTransaction().replace(R.id.three_frag,new MovieFragment()).commit();
                break;
            case R.id.success_button_cinema:
                mSuccess_button_movie.setVisibility(View.VISIBLE);
                mSuccess_button_movies.setVisibility(View.INVISIBLE);
                mSuccess_button_cinema.setVisibility(View.INVISIBLE);
                mSuccess_button_cinemas.setVisibility(View.VISIBLE);
                mSuccess_button_mine.setVisibility(View.VISIBLE);
                mSuccess_button_mines.setVisibility(View.INVISIBLE);

                mManager = getSupportFragmentManager();
                mManager.beginTransaction().replace(R.id.three_frag,new CinemaFragment()).commit();
                break;
            case R.id.success_button_mine:
                mSuccess_button_movie.setVisibility(View.VISIBLE);
                mSuccess_button_movies.setVisibility(View.INVISIBLE);
                mSuccess_button_cinema.setVisibility(View.VISIBLE);
                mSuccess_button_cinemas.setVisibility(View.INVISIBLE);
                mSuccess_button_mine.setVisibility(View.INVISIBLE);
                mSuccess_button_mines.setVisibility(View.VISIBLE);

                mManager = getSupportFragmentManager();
                mManager.beginTransaction().replace(R.id.three_frag,new MineFragment()).commit();
                break;
        }
    }

    @Override
    public int getContent() {
        return R.layout.activity_success;
    }

    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public void onFail(String error) {

    }

    @Override
    public void success(Object data) {

    }

    @Override
    public void fail(String error) {

    }
}
