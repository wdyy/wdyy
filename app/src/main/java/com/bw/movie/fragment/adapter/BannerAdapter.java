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
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.CinemaBannerSaveIdNameBean;
import com.bw.movie.bean.CinemaMovieListBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: 邵文龙
 * Date: 2019/1/26 16:38
 * Description: 轮播图的-Adapter
 */
public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {
    private Context mContext;
    private List<CinemaMovieListBean.ResultBean> mList;

    public BannerAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();
    }

    public void setData(List<CinemaMovieListBean.ResultBean> list) {
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

    public interface OnImgScrollChangeListener{
        void onImgClick(int movieId);
    }

    private OnImgScrollChangeListener mOnImgScrollChangeListener;

    public void setOnImgClickListener(OnImgScrollChangeListener onImgClickListener){
        mOnImgScrollChangeListener=onImgClickListener;
    }

    public CinemaBannerSaveIdNameBean getMovieId(int position){

        int id = mList.get(position).getId();
        String name = mList.get(position).getName();

        CinemaBannerSaveIdNameBean idNameBean = new CinemaBannerSaveIdNameBean(id, name);

        return idNameBean;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Uri uri = Uri.parse(mList.get(i).getImageUrl());
        viewHolder.movie_banner_image.setImageURI(uri);
        viewHolder.movie_banner_title.setText(mList.get(i).getName());

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
