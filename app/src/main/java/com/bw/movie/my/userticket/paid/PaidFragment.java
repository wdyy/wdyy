package com.bw.movie.my.userticket.paid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.my.api.MineUrlConstant;
import com.bw.movie.my.userticket.adapter.TicketInforAdapter;
import com.bw.movie.my.userticket.adapter.TicketInforAdaptertwo;
import com.bw.movie.my.userticket.bean.ResultBean;
import com.bw.movie.my.userticket.bean.TicketFoemationEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 已付款
 */
public class PaidFragment extends BaseFragment {

    @BindView(R.id.ticket_twoRecycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.ticketSwipeRefreshLayout2)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void initData(View view) {
        doNetRequestData(MineUrlConstant.QUERYPENDING2, null, TicketFoemationEntity.class, "get");

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doNetRequestData(MineUrlConstant.QUERYPENDING2, null, TicketFoemationEntity.class, "get");
            }
        });
    }

    @Override
    public int getContent() {
        return R.layout.fragment_paid;
    }

    @Override
    public void success(Object data) {
        if (data instanceof TicketFoemationEntity) {
            TicketFoemationEntity ticketFoemationEntity = (TicketFoemationEntity) data;
            List<ResultBean> result = ticketFoemationEntity.getResult();
            mSwipeRefreshLayout.setRefreshing(false);
            if (result != null && result.size() > 0) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                mRecyclerView.setLayoutManager(linearLayoutManager);
                TicketInforAdaptertwo ticketInforAdapter = new TicketInforAdaptertwo(getActivity(), result);
                mRecyclerView.setAdapter(ticketInforAdapter);
            } else {
                Toast.makeText(getActivity(), "sorry,暂无数据", Toast.LENGTH_LONG).show();
            }

            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void fail(String error) {

    }
}
