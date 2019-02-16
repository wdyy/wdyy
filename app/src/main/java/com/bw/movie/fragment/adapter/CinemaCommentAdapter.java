package com.bw.movie.fragment.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.CinemaBannerSaveIdNameBean;
import com.bw.movie.bean.CinemaCommentBean;
import com.bw.movie.bean.CinemaMovieListBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: 范瑞旗
 * Date: 2019/1/26 16:38
 * Description: 详情页评论列表
 */
public class CinemaCommentAdapter extends RecyclerView.Adapter<CinemaCommentAdapter.ViewHolder> {
    private Context mContext;
    private List<CinemaCommentBean.ResultBean> mList;

    public CinemaCommentAdapter(Context context, List<CinemaCommentBean.ResultBean> list) {
        mContext = context;
        mList = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_cinema_popup_comment,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public interface OnImgScrollChangeListener{
        void onImgClick(int movieId);
    }

    private OnImgScrollChangeListener mOnImgScrollChangeListener;

    public void setOnImgClickListener(OnImgScrollChangeListener onImgClickListener){
        mOnImgScrollChangeListener=onImgClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Uri uri = Uri.parse(mList.get(i).getCommentHeadPic());
        viewHolder.comment_img_image.setImageURI(uri);
        viewHolder.comment_text_userName.setText(mList.get(i).getCommentUserName());
        viewHolder.comment_text_content.setText(mList.get(i).getCommentContent());

        Date date = new Date();
        SimpleDateFormat time = new SimpleDateFormat("MM-dd HH:mm");
        String format = time.format(date);

        viewHolder.comment_text_time.setText(format);
        viewHolder.comment_text_num.setText(mList.get(i).getGreatNum()+"");


        if (mList.get(i).getIsGreat()==0){
            viewHolder.comment_img_dz.setImageResource(R.mipmap.com_icon_praise_default);
        }else {
            viewHolder.comment_img_dz.setImageResource(R.mipmap.com_icon_praise_selected);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_cinema_comment_HeadPic)
        SimpleDraweeView comment_img_image;

        @BindView(R.id.item_cinema_comment_UserName)
        TextView comment_text_userName;

        @BindView(R.id.item_cinema_comment_Content)
        TextView comment_text_content;

        @BindView(R.id.item_cinema_comment_Time)
        TextView comment_text_time;

        @BindView(R.id.item_cinema_comment_img_default)
        ImageView comment_img_dz;

        @BindView(R.id.item_cinema_comment_text_num)
        TextView comment_text_num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
