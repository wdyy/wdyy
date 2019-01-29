package com.bw.movie.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.CinemaCommentBean;
import com.bw.movie.bean.CinemaPopupDetailsBean;
import com.bw.movie.fragment.adapter.CinemaCommentAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 范瑞旗
 * Date: 2019/1/27 13:32
 * Description: 影院弹框评论-Fragment
 */
public class CinemaPopupCommentFragment extends BaseFragment {

    @BindView(R.id.fragment_cinema_recommend_popupWindow_comment_recyclerView)
    RecyclerView mRecyclerView;
    private int mCinemaId;

    @Override
    public void initView(View view) {


    }

    @Override
    public void initData(View view) {
        ButterKnife.bind(this,view);
        EventBus.getDefault().register(this);


    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void sjx(CinemaPopupDetailsBean detailsBean){

         mCinemaId = detailsBean.getId();
         doNetRequestData(String.format(Apis.URL_CINEMA_All_COMMENT,mCinemaId),null,CinemaCommentBean.class,"get");

    }

    @OnClick(R.id.fragment_cinema_recommend_popupWindow_comment_img_comment)
    public void onImgCommentClickListener(){


    }



    @Override
    public int getContent() {
        return R.layout.fragment_cinema_detail_popup_comment;
    }

    @Override
    public void success(Object data) {

        if (data instanceof CinemaCommentBean){
            CinemaCommentBean cinemaCommentBean = (CinemaCommentBean) data;
            List<CinemaCommentBean.ResultBean> result = cinemaCommentBean.getResult();

            CinemaCommentAdapter adapter = new CinemaCommentAdapter(getActivity(), result);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

            mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        }
    }

    @Override
    public void fail(String error) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
