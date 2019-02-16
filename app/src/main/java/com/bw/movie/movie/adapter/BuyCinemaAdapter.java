package com.bw.movie.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.moviebean.BuyCinemaBean;
import com.bw.movie.movie.activity.BuyTimeActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: 邵文龙
 * Date: 2019/2/13 14:00
 * Description: ${DESCRIPTION}
 */
public class BuyCinemaAdapter extends RecyclerView.Adapter<BuyCinemaAdapter.ViewHolder> {
    private Context mContext;
    private List<BuyCinemaBean.ResultBean> mList;
    public int mMovieId;

    public BuyCinemaAdapter(Context context, int movieId) {
        mContext = context;
        mMovieId = movieId;
        mList = new ArrayList<>();
    }

    public void setData(List<BuyCinemaBean.ResultBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.buy_cinema_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Uri uri = Uri.parse(mList.get(i).getLogo());
        viewHolder.buy_cinema_image.setImageURI(uri);
        viewHolder.buy_cinema_name.setText(mList.get(i).getName());
        viewHolder.buy_cinema_address.setText(mList.get(i).getAddress());
        viewHolder.buy_cinema_meter.setText(mList.get(i).getDistance()+""+"km");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,BuyTimeActivity.class);
                intent.putExtra("movieId",mMovieId);
                intent.putExtra("cinemaId",mList.get(i).getId());
                intent.putExtra("cinemaName",mList.get(i).getName());
                intent.putExtra("cinemaAddress",mList.get(i).getAddress());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.buy_cinema_image)
        SimpleDraweeView buy_cinema_image;
        @BindView(R.id.buy_cinema_name)
        TextView buy_cinema_name;
        @BindView(R.id.buy_cinema_address)
        TextView buy_cinema_address;
        @BindView(R.id.buy_cinema_meter)
        TextView buy_cinema_meter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
