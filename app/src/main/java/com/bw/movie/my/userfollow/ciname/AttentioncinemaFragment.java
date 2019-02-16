package com.bw.movie.my.userfollow.ciname;

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


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 郭佳兴
 **/
public class AttentioncinemaFragment extends BaseFragment {


    @BindView(R.id.attenrecycle1)
    RecyclerView mAttenrecycle1;
    @BindView(R.id.attenSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private List<ResultBean> mResult;

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData(View view) {
        ButterKnife.bind(this, view);

        doNetRequestData(MineUrlConstant.ATTENTIONCINEMA,null,AttCinemaUser.class,"get");
    }

    @Override
    public int getContent() {
        return R.layout.fragment_attentioncinema;
    }

    @Override
    public void success(Object data) {
        if (data instanceof AttCinemaUser) {
            mSwipeRefreshLayout.setRefreshing(false);
            AttCinemaUser attCinemaUser = (AttCinemaUser) data;
            mResult = attCinemaUser.getResult();
            if (mResult != null && mResult.size() > 0) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                mAttenrecycle1.setLayoutManager(linearLayoutManager);
                MyAtterCinemaAdapter attCinemaAdapter = new MyAtterCinemaAdapter(getActivity(),mResult);
                attCinemaAdapter.setHttpClick(new MyAtterCinemaAdapter.HttpClick() {
                    @Override
                    public void getClick(View view, int position) {
                        // startActivity(new Intent(getActivity(), WXEntryActivity.class));
                        getActivity().finish();
                    }
                });
                mAttenrecycle1.setAdapter(attCinemaAdapter);
            } else {
                Toast.makeText(getActivity(),"sorry:没有数据了",Toast.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void fail(String error) {

    }
}
