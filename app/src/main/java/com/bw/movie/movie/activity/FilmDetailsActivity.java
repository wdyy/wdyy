package com.bw.movie.movie.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.moviebean.MovieCommentDetailsBean;
import com.bw.movie.bean.moviebean.MovieDetailsBean;
import com.bw.movie.movie.adapter.FilmCosplayAdapter;
import com.bw.movie.movie.popupwindow.PopuWindowComment;
import com.bw.movie.movie.popupwindow.PopuWindowDetails;
import com.bw.movie.movie.popupwindow.PopuWindowNotice;
import com.bw.movie.movie.popupwindow.PopuWindowStills;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilmDetailsActivity extends BaseActivity {


    @BindView(R.id.film_details_title)
    TextView film_details_title;
    @BindView(R.id.film_details_image)
    SimpleDraweeView film_details_image;
    @BindView(R.id.film_details_image_bg)
    SimpleDraweeView film_details_image_bg;
    @BindView(R.id.film_details_image_gz)
    ImageView film_details_image_gz;
    @BindView(R.id.film_button_details)
    Button film_button_details;
    @BindView(R.id.film_button_notice)
    Button film_button_notice;
    @BindView(R.id.film_button_stills)
    Button film_button_stills;
    @BindView(R.id.film_button_comment)
    Button film_button_comment;
    private int mId;
    private MovieDetailsBean mBean;
    private MovieCommentDetailsBean mCommentDetailsBean;

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mId = intent.getIntExtra("id", 0);
        Map<String,String> map = new HashMap<>();
        doNetRequestData(String.format(Apis.URL_MOVIE_DETAILS,mId),map,MovieDetailsBean.class,"get");


    }

    @OnClick({R.id.film_button_details,R.id.film_button_notice,R.id.film_button_stills,R.id.film_button_comment
            ,R.id.film_button_return,R.id.film_button_buy})
    public void onFilmButton(View view){
        switch (view.getId()){
            case R.id.film_button_details:
                PopuWindowDetails windowDetails = new PopuWindowDetails(this,mBean);
                windowDetails.bottomwindow(film_button_details);
                break;
            case R.id.film_button_notice:
                PopuWindowNotice windowNotice = new PopuWindowNotice(this,mBean);
                windowNotice.bottomwindow(film_button_notice);
                break;
            case R.id.film_button_stills:
                PopuWindowStills windowStills = new PopuWindowStills(this,mBean);
                windowStills.bottomwindow(film_button_stills);
                break;
            case R.id.film_button_comment:

                PopuWindowComment windowComment = new PopuWindowComment(this,mCommentDetailsBean);
                windowComment.bottomwindow(film_button_comment);

                break;
            case R.id.film_button_return:
                finish();
                break;
            case R.id.film_button_buy:
                break;
        }
    }
    @Override
    public int getContent() {
        return R.layout.activity_film_details;
    }

    @Override
    public void success(Object data) {
        if (data instanceof MovieDetailsBean){
            mBean = (MovieDetailsBean) data;
            film_details_title.setText(mBean.getResult().getName());
            film_details_image.setImageURI(mBean.getResult().getImageUrl());
            film_details_image_bg.setImageURI(mBean.getResult().getImageUrl());

            Map<String,String> map1 = new HashMap<>();
            doNetRequestData(String.format(Apis.URL_QUERY_COMMENT,mId),map1,MovieCommentDetailsBean.class,"get");

        }else if (data instanceof MovieCommentDetailsBean){
            mCommentDetailsBean = (MovieCommentDetailsBean) data;
        }
    }

    @Override
    public void fail(String error) {

    }
}
