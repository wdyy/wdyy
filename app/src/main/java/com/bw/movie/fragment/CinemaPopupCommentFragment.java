package com.bw.movie.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.CinemaPopupDetailsBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: 范瑞旗
 * Date: 2019/1/27 13:32
 * Description: 影院弹框评论-Fragment
 */
public class CinemaPopupCommentFragment extends BaseFragment {

    @BindView(R.id.fragment_cinema_recommend_popupWindow_comment_recyclerView)
    RecyclerView mRecyclerView;

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


    }

    @Override
    public int getContent() {
        return R.layout.fragment_cinema_detail_popup_comment;
    }

    @Override
    public void success(Object data) {

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
