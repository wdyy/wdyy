package com.bw.movie.movie.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.moviebean.MovieCommentDetailsBean;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: 邵文龙
 * Date: 2019/1/28 16:04
 * Description: ${DESCRIPTION}
 */
public class FilmCommentAdapter extends XRecyclerView.Adapter<FilmCommentAdapter.ViewHolder> {

    private Context mContext;
    private List<MovieCommentDetailsBean.ResultBean> mList;
    private int mIndex;
    private int movieid;

    public FilmCommentAdapter(Context context, List<MovieCommentDetailsBean.ResultBean> list, int movieid) {
        mContext = context;
        mList = list;
        this.movieid = movieid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.film_comment_child_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        mIndex = i;

        viewHolder.film_comment_button_image.setImageURI(mList.get(i).getCommentHeadPic());
        viewHolder.film_comment_button_name.setText(mList.get(i).getCommentUserName());
        viewHolder.film_comment_button_message.setText(mList.get(i).getCommentContent());



        Date date = new Date();
        SimpleDateFormat time = new SimpleDateFormat("MM-dd HH:mm");
        String format = time.format(date);
        viewHolder.film_comment_button_time.setText(format);
        final int commentId = mList.get(i).getCommentId();


        viewHolder.film_comment_button_comment_num.setText(mList.get(i).getReplyNum()+"");
        viewHolder.film_comment_button_prise_num.setText(mList.get(i).getGreatNum()+"");

        final int isGreat = mList.get(i).getIsGreat();
        if (isGreat==1){
            viewHolder.film_comment_button_prise.setImageResource(R.mipmap.com_icon_praise_selected);

        }else if (isGreat==0){
            viewHolder.film_comment_button_prise.setImageResource(R.mipmap.com_icon_praise_default);
        }

        viewHolder.film_comment_button_prise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int isGreat1 = mList.get(i).getIsGreat();

                if (isGreat1==1){
                    if (mOnImageClickListener!=null){
                        mOnImageClickListener.onImgClick(commentId,viewHolder,i);
                    }

                }else if(isGreat1==0) {
                    mList.get(i).setIsGreat(1);


                    if (mOnImageClickListener!=null){
                        mOnImageClickListener.onImgClick(commentId,viewHolder,i);
                    }
//                    mList.get(i).setGreatNum(mList.get(i).getGreatNum()+1);
//                    viewHolder.film_comment_button_prise_num.setText(mList.get(i).getGreatNum()+"");
//                    viewHolder.film_comment_button_prise.setImageResource(R.mipmap.com_icon_praise_selected);

                }

            }
        });
        viewHolder.film_comment_button_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnImageClickListener != null){
                    mOnImageClickListener.onImgCommentClick(mList.get(i).getCommentId());
                }
            }
        });

    }
    public void setImgClick(ViewHolder viewHolder, int position){
        mList.get(position).setIsGreat(1);
        notifyItemChanged(position);
    }

    public void setImgCancelClick(ViewHolder viewHolder, int position){

        notifyItemChanged(position);
    }

    private onImageClickListener mOnImageClickListener;

    public void setImageClick(onImageClickListener onImageClickListener) {
        mOnImageClickListener = onImageClickListener;
    }

    public interface onImageClickListener{
        void onImgClick(int commentId,ViewHolder holder, int position);
        void onImgCancelClick(int commentId,ViewHolder holder, int position);
        void onImgCommentClick(int movied);
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.film_comment_button_image)
        SimpleDraweeView film_comment_button_image;
        @BindView(R.id.film_comment_button_name)
        TextView film_comment_button_name;
        @BindView(R.id.film_comment_button_message)
        TextView film_comment_button_message;
        @BindView(R.id.film_comment_button_time)
        TextView film_comment_button_time;
        @BindView(R.id.film_comment_button_prise_num)
        TextView film_comment_button_prise_num;
        @BindView(R.id.film_comment_button_comment_num)
        TextView film_comment_button_comment_num;
        @BindView(R.id.film_comment_button_prise)
        ImageView film_comment_button_prise;
        @BindView(R.id.film_comment_button_comment)
        ImageView film_comment_button_comment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
