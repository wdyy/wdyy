package com.bw.movie.my.userfollow.ciname;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.my.api.MineUrlConstant;
import com.bw.movie.my.userfollow.ciname.adapter.MyAtterCinemaAdapter;
import com.bw.movie.my.userfollow.ciname.bean.AttCinemaUser;
import com.bw.movie.my.userfollow.ciname.bean.ResultBean;
import com.bw.movie.wxapi.WXEntryActivity;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 关注的影院
 * 郭佳兴
 **/
public class AttentioncinemaFragment extends BaseFragment {


    @BindView(R.id.attenrecycle1)
    RecyclerView mAttenrecycle1;
    @BindView(R.id.attenSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<ResultBean> mResult;
    private int page = 1;

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData(View view) {
        ButterKnife.bind(this, view);

        doNetRequestData(MineUrlConstant.ATTENTIONCINEMA, null, AttCinemaUser.class, "get");

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doNetRequestData(MineUrlConstant.ATTENTIONCINEMA+page++, null, AttCinemaUser.class, "get");
            }
        });
    }

    @Override
    public int getContent() {
        return R.layout.fragment_attentioncinema;
    }

    @Override
    public void success(Object data) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (data instanceof AttCinemaUser) {
            AttCinemaUser attCinemaUser = (AttCinemaUser) data;
            mResult = attCinemaUser.getResult();
            if (mResult != null && mResult.size() > 0) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                mAttenrecycle1.setLayoutManager(linearLayoutManager);
                MyAtterCinemaAdapter attCinemaAdapter = new MyAtterCinemaAdapter(getActivity(), mResult);
                attCinemaAdapter.setHttpClick(new MyAtterCinemaAdapter.HttpClick() {
                    @Override
                    public void getClick(View view, int position) {
                        startActivity(new Intent(getActivity(), WXEntryActivity.class));
                        getActivity().finish();
                    }
                });
                mAttenrecycle1.setAdapter(attCinemaAdapter);
            } else {
                Toast.makeText(getActivity(), "sorry:没有数据了", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void fail(String error) {

    }
}
