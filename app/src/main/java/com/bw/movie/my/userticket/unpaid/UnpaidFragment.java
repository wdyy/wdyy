package com.bw.movie.my.userticket.unpaid;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.my.api.MineUrlConstant;
import com.bw.movie.my.userticket.adapter.TicketInforAdapter;
import com.bw.movie.my.userticket.bean.ResultBean;
import com.bw.movie.my.userticket.bean.TicketFoemationEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 待付款
 * 郭佳兴
 */
public class UnpaidFragment extends BaseFragment {

    @BindView(R.id.ticket_oneRecycler)
    RecyclerView ticketOneRecycler;
    Unbinder unbinder;
    @BindView(R.id.ticketSwipeRefreshLayout)
    SwipeRefreshLayout ticketSwipeRefreshLayout;

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void initData(View view) {
        doNetRequestData(MineUrlConstant.QUERYPENDING1, null, TicketFoemationEntity.class, "get");

        ticketSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doNetRequestData(MineUrlConstant.QUERYPENDING1, null, TicketFoemationEntity.class, "get");
            }
        });

    }

    @Override
    public int getContent() {
        return R.layout.fragment_unpaid;
    }

    @Override
    public void success(Object data) {

        if (data instanceof TicketFoemationEntity) {
            TicketFoemationEntity ticketFoemationEntity = (TicketFoemationEntity) data;
            List<ResultBean> result = ticketFoemationEntity.getResult();
            ticketSwipeRefreshLayout.setRefreshing(false);
            if (result != null && result.size() > 0) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                ticketOneRecycler.setLayoutManager(linearLayoutManager);

                TicketInforAdapter ticketInforAdapter = new TicketInforAdapter(getActivity(), result);
                ticketInforAdapter.setHttpClick(new TicketInforAdapter.HttpClick() {
                    @Override
                    public void click(View view, int position) {
                        //调用支付

                    }
                });
                ticketOneRecycler.setAdapter(ticketInforAdapter);
            } else {
                Toast.makeText(getActivity(), "sorry,暂无数据", Toast.LENGTH_SHORT).show();
            }

            ticketSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void fail(String error) {

    }
}
