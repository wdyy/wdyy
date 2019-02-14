package com.bw.movie.my.userfollow.film;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.my.api.MineUrlConstant;
import com.bw.movie.my.userfollow.film.adapter.MyAtteretionfilmAdapter;
import com.bw.movie.my.userfollow.film.bean.MyAttFilmUser;
import com.bw.movie.my.userfollow.film.bean.ResultBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 郭佳兴
 **/
public class AtterionFilmFragment extends BaseFragment {

    @BindView(R.id.attenrecycle2)
    RecyclerView mAttenrecycle2;
    Unbinder mUnbinder;
    @BindView(R.id.attenSwipeRefreshLayout2)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private Map<String, String> mMap;

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
    }


    @Override
    public void initData(View view) {

        mMap = new HashMap<>();
        doNetRequestData(MineUrlConstant.ATTENTIONFILM, null, MyAttFilmUser.class, "get");

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doNetRequestData(MineUrlConstant.ATTENTIONFILM, null, MyAttFilmUser.class, "get");
            }
        });
    }

    @Override
    public int getContent() {
        return R.layout.fragment_atterion_film;
    }

    @Override
    public void success(Object data) {

        if (data instanceof MyAttFilmUser) {
            MyAttFilmUser attFilmUser = (MyAttFilmUser) data;
            List<ResultBean> mList = attFilmUser.getResult();
            mSwipeRefreshLayout.setRefreshing(false);
            if (mList != null && mList.size() > 0) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                mAttenrecycle2.setLayoutManager(linearLayoutManager);

                MyAtteretionfilmAdapter attFilmAdapter = new MyAtteretionfilmAdapter(getActivity(), mList);
                attFilmAdapter.setHttpClick(new MyAtteretionfilmAdapter.HttpClick() {
                    @Override
                    public void getClick(View view, int position) {
                        //  startActivity(new Intent(getActivity(),WXEntryActivity.class));
                        getActivity().finish();
                    }
                });
                mAttenrecycle2.setAdapter(attFilmAdapter);
            } else {
                Toast.makeText(getActivity(), "sorry,没有更多数据了", Toast.LENGTH_SHORT).show();
            }

            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void fail(String error) {

    }
}
