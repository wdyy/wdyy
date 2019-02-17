package com.bw.movie.fragment.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.CinemaBannerSaveIdNameBean;
import com.bw.movie.bean.CinemaDetailBean;
import com.bw.movie.bean.CinemaMovieListBean;
import com.bw.movie.bean.CinemaMovieScheduleBean;
import com.bw.movie.bean.CinemaPopupDetailsBean;
import com.bw.movie.bean.CinemaSeatTableDetailBean;
import com.bw.movie.fragment.CinemaPopupCommentFragment;
import com.bw.movie.fragment.CinemaPopupDetailFragment;
import com.bw.movie.fragment.adapter.BannerAdapter;
import com.bw.movie.fragment.adapter.CinemaDetailListAdapter;
import com.bw.movie.util.SpaceItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

/**
 * Author: 范瑞旗
 * Date: 2019/1/26 18:49
 * Description: 影院详情
 */
public class CinemaDetailActivity extends BaseActivity {

    @BindView(R.id.activity_cinema_recommend_detail_recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.activity_cinema_detail_img)
    ImageView mImageView;

    @BindView(R.id.activity_cinema_recommend_detail_text_name)
    TextView mTextView_name;

    @BindView(R.id.activity_cinema_recommend_detail_text_address)
    TextView mTextView_address;

    @BindView(R.id.activity_cinema_detail_ll)
    LinearLayout mLinearLayout;


    @BindView(R.id.success_movie_banner)
    RecyclerCoverFlow mRecyclerCoverFlow;

    private PopupWindow popupWindow;
    CinemaDetailBean.ResultBean result;

    int cinemasId;
    private Boolean bo=true;
    private int mMovieId;
    private String mName;
    private View pop;
    private FragmentManager mManager;

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        ButterKnife.bind(this);

        Intent intent = getIntent();
        cinemasId = intent.getIntExtra("Id", 0);


            doNetRequestData(String.format(Apis.URL_FIND_CINEMA_INFO,cinemasId),null,CinemaDetailBean.class,"get");



    }

    @Override
    public <T extends View> T findViewById(int id) {
        if (id == R.id.cinema_detail_viewPager && pop !=null){
            return pop.findViewById(id);
        }
        return super.findViewById(id);
    }

    @OnClick(R.id.activity_cinema_detail_ll)
    public void onLinearClickListener(){  //弹框点击事件

        CinemaPopupDetailsBean detailsBean = new CinemaPopupDetailsBean(result.getAddress(), result.getId(), result.getPhone(), result.getVehicleRoute());
        EventBus.getDefault().postSticky(detailsBean);



        View inflate= View.inflate(CinemaDetailActivity.this, R.layout.filmactivity_item_details, null);

        TextView textView_detail = inflate.findViewById(R.id.cinema_pw_text_detail);
        final TextView textView_comment = inflate.findViewById(R.id.cinema_pw_text_comment);
        final TextView textView_detail_line = inflate.findViewById(R.id.cinema_pw_text_detail_line);
        final TextView textView_comment_line = inflate.findViewById(R.id.cinema_pw_text_comment_line);
        final ImageView imageView = inflate.findViewById(R.id.cinema_detail_img_dismiss);


        popupWindow = new PopupWindow(inflate,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //添加弹出、弹入的动画
        popupWindow.setAnimationStyle(R.style.Popupwindow);
        int[] location = new int[2];
        mLinearLayout.getLocationOnScreen(location);
        popupWindow.showAtLocation(mLinearLayout, Gravity.LEFT | Gravity.BOTTOM, 0, -location[1]);


       pop=inflate;


        mManager = getSupportFragmentManager();
        mManager.beginTransaction().replace(R.id.cinema_detail_viewPager,new CinemaPopupDetailFragment()).commit();


        textView_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textView_detail_line.setVisibility(View.VISIBLE);
                textView_comment_line.setVisibility(View.INVISIBLE);

                mManager.beginTransaction().replace(R.id.cinema_detail_viewPager,new CinemaPopupDetailFragment()).commit();


            }
        });

        textView_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView_comment_line.setVisibility(View.VISIBLE);
                textView_detail_line.setVisibility(View.INVISIBLE);

                mManager.beginTransaction().replace(R.id.cinema_detail_viewPager,new CinemaPopupCommentFragment()).commit();

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

    }

    @OnClick(R.id.activity_cinema_detail_img_back)
    public void onImgClickListener(){

        finish();
    }

    @Override
    public int getContent() {
        return R.layout.activity_cinema_detail;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void success(Object data) {

        if (data instanceof CinemaDetailBean){   //详情数据
            CinemaDetailBean cinemaDetailBean = (CinemaDetailBean) data;
            result = cinemaDetailBean.getResult();


                Glide.with(CinemaDetailActivity.this).load(result.getLogo()).into(mImageView);

                mTextView_name.setText(result.getName());
                mTextView_address.setText(result.getAddress());

                doNetRequestData(String.format(Apis.URL_FIND_MOVIE_LIST,cinemasId),null,CinemaMovieListBean.class,"get");




        }else if (data instanceof CinemaMovieListBean){  //轮播数据
            CinemaMovieListBean cinemaMovieListBean = (CinemaMovieListBean) data;
            List<CinemaMovieListBean.ResultBean> result = cinemaMovieListBean.getResult();

            if (result.size()!=0){
                final BannerAdapter bannerAdapter = new BannerAdapter(this);
                bannerAdapter.setData(result);
                mRecyclerCoverFlow.setAdapter(bannerAdapter);

                CinemaBannerSaveIdNameBean movieId1 = bannerAdapter.getMovieId(0);
                mMovieId = movieId1.getId();
                mName = movieId1.getName();

                //根据电影ID和影院ID查询电影排期列表
                doNetRequestData(String.format(Apis.URL_FIND_MOVIE_SCHEDULE_LIST,cinemasId,mMovieId),null,CinemaMovieScheduleBean.class,"get");

                mRecyclerCoverFlow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
                    @Override
                    public void onItemSelected(int position) {

                        CinemaBannerSaveIdNameBean movieId1 = bannerAdapter.getMovieId(position);
                        mMovieId = movieId1.getId();
                        mName = movieId1.getName();
                        Toast.makeText(CinemaDetailActivity.this,mName,Toast.LENGTH_SHORT).show();

                        doNetRequestData(String.format(Apis.URL_FIND_MOVIE_SCHEDULE_LIST,cinemasId,mMovieId),null,CinemaMovieScheduleBean.class,"get");

                    }
                });
            }


        }else if (data instanceof CinemaMovieScheduleBean){   //List数据
            CinemaMovieScheduleBean cinemaDetailBean = (CinemaMovieScheduleBean) data;

            List<CinemaMovieScheduleBean.ResultBean> result = cinemaDetailBean.getResult();

            CinemaDetailListAdapter listAdapter = new CinemaDetailListAdapter(CinemaDetailActivity.this, result);

            mRecyclerView.setAdapter(listAdapter);
            if (bo){
                mRecyclerView.addItemDecoration(new SpaceItemDecoration(15));
                bo=false;
            }
            mRecyclerView.setLayoutManager(new LinearLayoutManager(CinemaDetailActivity.this,LinearLayoutManager.VERTICAL,false));

            listAdapter.setOnImgClickListener(new CinemaDetailListAdapter.OnImgClickListener() {//条目点击
                @Override
                public void onItemClick(int id, String beginTime, String endTime, String hall, double price) {


                    CinemaSeatTableDetailBean detailBean = new CinemaSeatTableDetailBean(id, mName, beginTime, endTime, hall,seatsTotal);

                    CinemaSeatTableDetailBean detailBean = new CinemaSeatTableDetailBean(cinemasId, mName, beginTime, endTime, hall,price);

                    EventBus.getDefault().postSticky(detailBean);

                    startActivity(new Intent(CinemaDetailActivity.this,CinemaSeatTableActivity.class));

                }

            });
        }


    }

    @Override
    public void fail(String error) {

    }


}
