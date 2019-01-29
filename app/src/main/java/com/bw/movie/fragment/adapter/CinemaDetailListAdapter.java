package com.bw.movie.fragment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.CinemaMovieScheduleBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: 范瑞旗
 * Date: 2019/1/26 14:55
 * Description: 根据电影ID和影院ID查询电影排期列表
 */
public class CinemaDetailListAdapter extends RecyclerView.Adapter<CinemaDetailListAdapter.ViewHolder> {

    private Context mContext;
    private List<CinemaMovieScheduleBean.ResultBean> list;

    public CinemaDetailListAdapter(Context context, List<CinemaMovieScheduleBean.ResultBean> list) {
        mContext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = View.inflate(parent.getContext(), R.layout.item_cinema_detail_list, null);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public interface OnImgClickListener{
        void onItemClick(int id, String beginTime, String endTime, String hall, int seatsTotal);
    }

    private OnImgClickListener mOnImgClickListener;

    public void setOnImgClickListener(OnImgClickListener onImgClickListener){
        mOnImgClickListener=onImgClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.mTextView_name.setText(list.get(position).getScreeningHall());
        holder.mTextView_startName.setText(list.get(position).getBeginTime());
        holder.mTextView_endTime.setText(list.get(position).getEndTime()+" end");
        holder.mTextView_price.setText(list.get(position).getSeatsTotal()+"");
        final int id = list.get(position).getId();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String beginTime = list.get(position).getBeginTime();
                String endTime = list.get(position).getEndTime();
                String hall = list.get(position).getScreeningHall();
                int seatsTotal = list.get(position).getSeatsTotal();

                mOnImgClickListener.onItemClick(id,beginTime,endTime,hall,seatsTotal);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_cinema_detail_text_name)
        TextView mTextView_name;

        @BindView(R.id.item_cinema_detail_text_startTime)
        TextView mTextView_startName;

        @BindView(R.id.item_cinema_detail_text_endTime)
        TextView mTextView_endTime;

        @BindView(R.id.item_cinema_detail_text_price)
        TextView mTextView_price;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);

        }
    }
}
