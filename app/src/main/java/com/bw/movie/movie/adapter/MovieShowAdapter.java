package com.bw.movie.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.bean.moviebean.HotMovieBean;
import com.bw.movie.bean.moviebean.LoadingMovieBean;
import com.bw.movie.bean.moviebean.WaitMovieBean;
import com.bw.movie.movie.activity.FilmDetailsActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: 邵文龙
 * Date: 2019/1/25 19:58
 * Description: ${DESCRIPTION}
 */
public class MovieShowAdapter extends RecyclerView.Adapter<MovieShowAdapter.ViewHolder> {

    private Context mContext;
    private List<HotMovieBean.ResultBean> mHot;
    private List<LoadingMovieBean.ResultBean> mLoad;
    private List<WaitMovieBean.ResultBean> mWait;
    private int mIndex;

    public MovieShowAdapter(Context mContext,int mindex) {
        this.mContext = mContext;
        this.mIndex=mindex;
        mHot = new ArrayList<>();
        mLoad = new ArrayList<>();
        mWait = new ArrayList<>();
    }

    public void setHotData(List<HotMovieBean.ResultBean> mHot) {
        this.mHot = mHot;
        notifyDataSetChanged();
    }

    public void setLoadData(List<LoadingMovieBean.ResultBean> mLoad) {
        this.mLoad = mLoad;
        notifyDataSetChanged();
    }

    public void setWaitData(List<WaitMovieBean.ResultBean> mWait) {
        this.mWait = mWait;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_item_show,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        if (mIndex == 0){
            viewHolder.movie_item_name.setText(mHot.get(i).getName());

            Uri uri = Uri.parse(mHot.get(i).getImageUrl());
            viewHolder.movie_item_image.setImageURI(uri);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,FilmDetailsActivity.class);
                    intent.putExtra("id",mHot.get(i).getId());
                    mContext.startActivity(intent);
                }
            });

//            //设置图片圆角角度
//            RoundedCorners roundedCorners= new RoundedCorners(15);
//            //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
//            RequestOptions options=RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
//            Glide.with(mContext).load(mHot.get(i).getImageUrl()).apply(options).into(viewHolder.movie_item_image);
//            Glide.with(mContext).load(mHot.get(i).getImageUrl()).into(viewHolder.movie_item_image);
        }else if (mIndex == 1){
            viewHolder.movie_item_name.setText(mLoad.get(i).getName());

            Uri uri = Uri.parse(mLoad.get(i).getImageUrl());
            viewHolder.movie_item_image.setImageURI(uri);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,FilmDetailsActivity.class);
                    intent.putExtra("id",mLoad.get(i).getId());
                    mContext.startActivity(intent);
                }
            });

//            //设置图片圆角角度
//            RoundedCorners roundedCorners= new RoundedCorners(15);
//            //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
//            RequestOptions options=RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
//            Glide.with(mContext).load(mLoad.get(i).getImageUrl()).apply(options).into(viewHolder.movie_item_image);
//            Glide.with(mContext).load(mLoad.get(i).getImageUrl()).into(viewHolder.movie_item_image);
        }else if (mIndex == 2){
            viewHolder.movie_item_name.setText(mWait.get(i).getName());

            Uri uri = Uri.parse(mWait.get(i).getImageUrl());
            viewHolder.movie_item_image.setImageURI(uri);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext,FilmDetailsActivity.class);
                    intent.putExtra("id",mWait.get(i).getId());
                    mContext.startActivity(intent);
                }
            });

//            //设置图片圆角角度
//            RoundedCorners roundedCorners= new RoundedCorners(15);
//            //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
//            RequestOptions options=RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
//            Glide.with(mContext).load(mWait.get(i).getImageUrl()).apply(options).into(viewHolder.movie_item_image);
//            Glide.with(mContext).load(mWait.get(i).getImageUrl()).into(viewHolder.movie_item_image);
        }
    }

    @Override
    public int getItemCount() {
        if (mIndex == 0){
            return mHot.size();
        }else if(mIndex == 1){
            return mLoad.size();
        }else if (mIndex == 2){
            return mWait.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_item_image)
        SimpleDraweeView movie_item_image;
        @BindView(R.id.movie_item_name)
        TextView movie_item_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
