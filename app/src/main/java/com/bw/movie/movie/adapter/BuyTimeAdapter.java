package com.bw.movie.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.CinemaSeatTableDetailBean;
import com.bw.movie.bean.moviebean.BuyTimeBean;
import com.bw.movie.fragment.activity.CinemaSeatTableActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: 邵文龙
 * Date: 2019/2/14 19:55
 * Description: ${DESCRIPTION}
 */
public class BuyTimeAdapter extends RecyclerView.Adapter<BuyTimeAdapter.ViewHolder> {

    private Context mContext;
    private List<BuyTimeBean.ResultBean> mList;

    public BuyTimeAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();
    }

    public void setData(List<BuyTimeBean.ResultBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.buy_time_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final BuyTimeBean.ResultBean resultBean = mList.get(i);
        viewHolder.date_screenHall.setText(resultBean.getScreeningHall());
        viewHolder.date_time_start.setText(resultBean.getBeginTime());
        viewHolder.date_time_end.setText(resultBean.getEndTime());
        viewHolder.date_time_price_shi.setText(resultBean.getPrice()+"");




        int status = resultBean.getStatus();
        if (status==1){
            viewHolder.date_time_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    CinemaSeatTableDetailBean tableDetailBean = new CinemaSeatTableDetailBean(resultBean.getId(), null, resultBean.getBeginTime(), resultBean.getEndTime(), resultBean.getScreeningHall(), resultBean.getPrice());
                    EventBus.getDefault().postSticky(tableDetailBean);
                    mContext.startActivity(new Intent(mContext,CinemaSeatTableActivity.class));

                }
            });
        }else if (status==2){
            viewHolder.date_time_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext,"抱歉，当前场次已过期，请选择其他场次",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.date_screenHall)
        TextView date_screenHall;
        @BindView(R.id.date_time_start)
        TextView date_time_start;
        @BindView(R.id.date_time_end)
        TextView date_time_end;
        @BindView(R.id.date_time_price_shi)
        TextView date_time_price_shi;
        @BindView(R.id.date_time_next)
        ImageView date_time_next;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
