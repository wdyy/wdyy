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

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.bean.moviebean.HotMovieBean;
import com.bw.movie.fragment.adapter.RecommendAdapter;
import com.bw.movie.movie.activity.FilmDetailsActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: 邵文龙
 * Date: 2019/1/26 9:28
 * Description: ${DESCRIPTION}
 */
public class HotMovieAdapter extends RecyclerView.Adapter<HotMovieAdapter.ViewHolder> {
    private Context mContext;
    private List<HotMovieBean.ResultBean> mList;

    public HotMovieAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();
    }

    public void setData(List<HotMovieBean.ResultBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_childre_item_show,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.movie_child_item_name.setText(mList.get(i).getName());
        viewHolder.movie_child_item_message.setText(mList.get(i).getSummary());
        viewHolder.movie_child_item_image.setImageURI(mList.get(i).getImageUrl());
        final int id = mList.get(i).getId();
        final int followMovie = mList.get(i).getFollowMovie();
        if (followMovie==1){
            viewHolder.movie_child_item_gz.setImageResource(R.mipmap.com_icon_collection_selected);
        }else if (followMovie==2){
            viewHolder.movie_child_item_gz.setImageResource(R.mipmap.com_icon_collection_default);
        }

        viewHolder.movie_child_item_gz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int followCinema = mList.get(i).getFollowMovie();

                if (followCinema==1){

                    if (mOnImgClickListener!=null){
                        mOnImgClickListener.onImgCancelClick(id,viewHolder,i);
                    }

                }else {

                    if (mOnImgClickListener!=null){
                        mOnImgClickListener.onImgClick(id,viewHolder,i);
                    }

                }
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,FilmDetailsActivity.class);
                intent.putExtra("id",mList.get(i).getId());
                mContext.startActivity(intent);
            }
        });
    }
    public void setImgClick(ViewHolder viewHolder, int position){
        mList.get(position).setFollowMovie(1);
        notifyItemChanged(position);
    }

    public void setImgCancelClick(ViewHolder viewHolder, int position){
        mList.get(position).setFollowMovie(2);
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_child_item_image)
        SimpleDraweeView movie_child_item_image;
        @BindView(R.id.movie_child_item_name)
        TextView movie_child_item_name;
        @BindView(R.id.movie_child_item_message)
        TextView movie_child_item_message;
        @BindView(R.id.movie_child_item_gz)
        ImageView movie_child_item_gz;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    public interface OnImgClickListener{
        void onImgClick(int id,ViewHolder holder, int position);
        void onImgCancelClick(int id,ViewHolder holder, int position);
    }

    private OnImgClickListener mOnImgClickListener;


    public void setOnImgClickListener(OnImgClickListener onImgClickListener) {
        mOnImgClickListener = onImgClickListener;
    }

}
