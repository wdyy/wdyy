package com.bw.movie.movie.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.bean.moviebean.MovieCommentDetailsBean;
import com.bw.movie.bean.moviebean.MovieDetailsBean;
import com.bw.movie.general.activity.LoginActivity;
import com.bw.movie.movie.popupwindow.PopuWindowComment;
import com.bw.movie.movie.popupwindow.PopuWindowDetails;
import com.bw.movie.movie.popupwindow.PopuWindowNotice;
import com.bw.movie.movie.popupwindow.PopuWindowStills;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilmDetailsActivity extends BaseActivity {


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
    @BindView(R.id.film_details_title)
    TextView film_details_title;
    @BindView(R.id.film_button_return)
    ImageView film_button_return;
    @BindView(R.id.film_button_buy)
    Button film_button_buy;
    private int mId;
    private MovieDetailsBean mBean;
    private MovieCommentDetailsBean mCommentDetailsBean;
    private int mFollowMovie;

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mId = intent.getIntExtra("id", 0);
        Map<String, String> map = new HashMap<>();
        doNetRequestData(String.format(Apis.URL_MOVIE_DETAILS, mId), map, MovieDetailsBean.class, "get");


    }

    @OnClick({R.id.film_button_details, R.id.film_button_notice, R.id.film_button_stills, R.id.film_button_comment
            , R.id.film_button_return, R.id.film_button_buy, R.id.film_details_image_gz})
    public void onFilmButton(View view) {
        switch (view.getId()) {
            case R.id.film_button_details:
                PopuWindowDetails windowDetails = new PopuWindowDetails(this, mBean);
                windowDetails.bottomwindow(film_button_details);
                break;
            case R.id.film_button_notice:
                PopuWindowNotice windowNotice = new PopuWindowNotice(this, mBean);
                windowNotice.bottomwindow(film_button_notice);
                break;
            case R.id.film_button_stills:
                PopuWindowStills windowStills = new PopuWindowStills(this, mBean);
                windowStills.bottomwindow(film_button_stills);
                break;
            case R.id.film_button_comment:
                PopuWindowComment windowComment = new PopuWindowComment(this, mCommentDetailsBean,mId);
                windowComment.bottomwindow(film_button_comment);
                break;
            case R.id.film_button_return:
                finish();
                break;
            case R.id.film_button_buy:
                Intent intent = new Intent(FilmDetailsActivity.this,BuyCinemaActivity.class);
                intent.putExtra("movieId",mId);
                intent.putExtra("moviename",mBean.getResult().getName());
                startActivity(intent);
                break;
            case R.id.film_details_image_gz:
                if (mFollowMovie == 2) {
                    doNetRequestData(String.format(Apis.URL_FOLLOW_MOVIE, mId),null,RegisterBean.class,"get");

                } else if (mFollowMovie==1){
                    doNetRequestData(String.format(Apis.URL_CANCLE_FLLOW_MOVIE, mId),null,RegisterBean.class,"get");
                }
                break;
        }
    }


    @Override
    public int getContent() {
        return R.layout.activity_film_details;
    }

    @Override
    public void success(Object data) {
        if (data instanceof MovieDetailsBean) {

            mBean = (MovieDetailsBean) data;
            film_details_title.setText(mBean.getResult().getName());
            film_details_image.setImageURI(mBean.getResult().getImageUrl());
            film_details_image_bg.setImageURI(mBean.getResult().getImageUrl());
            mFollowMovie = mBean.getResult().getFollowMovie();

            if (mFollowMovie==1){
                film_details_image_gz.setImageResource(R.mipmap.com_icon_collection_selected);
            }else if (mFollowMovie==2){
                film_details_image_gz.setImageResource(R.mipmap.com_icon_collection_default);
            }

            Map<String, String> map1 = new HashMap<>();
            doNetRequestData(String.format(Apis.URL_QUERY_COMMENT, mId), map1, MovieCommentDetailsBean.class, "get");

        } else if (data instanceof MovieCommentDetailsBean) {
            mCommentDetailsBean = (MovieCommentDetailsBean) data;

        } else if (data instanceof RegisterBean) {

            RegisterBean registerBean = (RegisterBean) data;

            if (registerBean.getMessage().equals("请先登陆")){

                /*LoginAlertDialog dialog = new LoginAlertDialog(getActivity());
                dialog.alert();*/
                //LoginAlertDialog.alert(getActivity());
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(FilmDetailsActivity.this);
                builder.setTitle("提示：");
                builder.setMessage("请先登录！");
                builder.setIcon(R.mipmap.ic_launcher_round);
                //点击对话框以外的区域是否让对话框消失
                builder.setCancelable(true);
                //设置正面按钮
                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                //设置反面按钮
                builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(new Intent(FilmDetailsActivity.this,LoginActivity.class));
                        //startActivityS();
                    }
                });
                android.support.v7.app.AlertDialog dialog = builder.create();
                //显示对话框
                dialog.show();

            }else if (registerBean.getMessage().equals("关注成功")){

                mFollowMovie=1;
                film_details_image_gz.setImageResource(R.mipmap.com_icon_collection_selected);

                Toast.makeText(FilmDetailsActivity.this,registerBean.getMessage(),Toast.LENGTH_SHORT).show();
            }else if (registerBean.getMessage().equals("取消关注成功")){

                mFollowMovie=2;
                film_details_image_gz.setImageResource(R.mipmap.com_icon_collection_default);

                Toast.makeText(FilmDetailsActivity.this,registerBean.getMessage(),Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(FilmDetailsActivity.this,registerBean.getMessage(),Toast.LENGTH_SHORT).show();
            }


        }
    }

    @Override
    public void fail(String error) {

    }


}
