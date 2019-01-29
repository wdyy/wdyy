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
import com.bw.movie.bean.moviebean.LoadingMovieBean;
import com.bw.movie.movie.activity.FilmDetailsActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: 邵文龙
 * Date: 2019/1/26 16:38
 * Description: ${DESCRIPTION}
 */
public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {
    private Context mContext;
    private List<LoadingMovieBean.ResultBean> mList;

    public BannerAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();
    }

    public void setData(List<LoadingMovieBean.ResultBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_banner_show,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Uri uri = Uri.parse(mList.get(i).getImageUrl());
        viewHolder.movie_banner_image.setImageURI(uri);
        viewHolder.movie_banner_title.setText(mList.get(i).getName());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,FilmDetailsActivity.class);
                intent.putExtra("id",mList.get(i).getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_banner_image)
        SimpleDraweeView movie_banner_image;
        @BindView(R.id.movie_banner_title)
        TextView movie_banner_title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
