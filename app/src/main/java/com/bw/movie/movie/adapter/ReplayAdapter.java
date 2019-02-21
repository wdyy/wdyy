package com.bw.movie.movie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.moviebean.MovieCommentDetailsBean;

import com.facebook.drawee.view.SimpleDraweeView;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: 邵文龙
 * Date: 2019/2/20 19:47
 * Description: ${DESCRIPTION}
 */
public class ReplayAdapter extends RecyclerView.Adapter<ReplayAdapter.ViewHolder> {
    private Context mContext;
    private List<MovieCommentDetailsBean.ReplayBean.ResultBean> mList;


    public ReplayAdapter(Context context, List<MovieCommentDetailsBean.ReplayBean.ResultBean> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.replay_show_item_show,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
//        viewHolder.replay_image.setImageURI(Uri.parse(mList.get(i).getReplyHeadPic()));
//        viewHolder.replay_name.setText(mList.get(i).getReplyUserName());
//        viewHolder.replay_message.setText(mList.get(i).getReplyContent());
//
//        Date date = new Date();
//        SimpleDateFormat time = new SimpleDateFormat("MM-dd HH:mm");
//        String format = time.format(date);
//        viewHolder.replay_time.setText(format);


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.replay_image)
        SimpleDraweeView replay_image;
        @BindView(R.id.replay_name)
        TextView replay_name;
        @BindView(R.id.replay_message)
        TextView replay_message;
        @BindView(R.id.replay_time)
        TextView replay_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
