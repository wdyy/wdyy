package com.bw.movie.fragment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.bean.RecommendCinemaBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: 范瑞旗
 * Date: 2019/1/26 14:55
 * Description: 推荐影院
 */
public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {

    private Context mContext;
    private List<RecommendCinemaBean.ResultBean> list;

    public RecommendAdapter(Context context, List<RecommendCinemaBean.ResultBean> list) {
        mContext = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = View.inflate(parent.getContext(), R.layout.item_cinema_recommend, null);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public interface OnImgClickListener{
        void onImgClick(int id);
        void onImgCancelClick(int id);
        void onItemClick(int id);
    }

    private OnImgClickListener mOnImgClickListener;

    public void setOnImgClickListener(OnImgClickListener onImgClickListener){
        mOnImgClickListener=onImgClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        Glide.with(mContext).load(list.get(position).getLogo()).into(holder.mImageView_logo);
        holder.mTextView_name.setText(list.get(position).getName());
        holder.mTextView_address.setText(list.get(position).getAddress());
        holder.mTextView_distance.setText(list.get(position).getDistance()+" km");
        final int id = list.get(position).getId();

        final int followCinema = list.get(position).getFollowCinema();

        if (followCinema==1){
            holder.mImageView_xin.setImageResource(R.mipmap.com_icon_collection_selected);
        }else {
            holder.mImageView_xin.setImageResource(R.mipmap.com_icon_collection_default);
        }

        holder.mImageView_xin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (followCinema==1){

                    list.get(position).setFollowCinema(2);

                    if (mOnImgClickListener!=null){
                        mOnImgClickListener.onImgCancelClick(id);
                    }
                    holder.mImageView_xin.setImageResource(R.mipmap.com_icon_collection_default);
                    notifyDataSetChanged();
                }else {
                    list.get(position).setFollowCinema(1);

                    if (mOnImgClickListener!=null){
                        mOnImgClickListener.onImgClick(id);
                    }
                    holder.mImageView_xin.setImageResource(R.mipmap.com_icon_collection_selected);
                    notifyDataSetChanged();
                }


            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mOnImgClickListener.onItemClick(id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_cinema_recommend_img)
        ImageView mImageView_logo;

        @BindView(R.id.item_cinema_recommend_img_xin)
        ImageView mImageView_xin;

        @BindView(R.id.item_cinema_recommend_text_name)
        TextView mTextView_name;

        @BindView(R.id.item_cinema_recommend_text_address)
        TextView mTextView_address;

        @BindView(R.id.item_cinema_recommend_text_distance)
        TextView mTextView_distance;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);

        }
    }
}
