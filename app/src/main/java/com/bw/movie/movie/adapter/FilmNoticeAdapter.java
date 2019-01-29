package com.bw.movie.movie.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.bean.moviebean.MovieDetailsBean;
import com.bw.movie.movie.frescotobitmap.FrescoBitmapCallback;
import com.bw.movie.movie.frescotobitmap.FrescoLoadUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Author: 邵文龙
 * Date: 2019/1/28 16:03
 * Description: ${DESCRIPTION}
 */
public class FilmNoticeAdapter extends RecyclerView.Adapter<FilmNoticeAdapter.ViewHolder> {
    private Context mContext;
    private List<MovieDetailsBean.ResultBean.ShortFilmListBean> mList;

    public FilmNoticeAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();
    }

    public void setData(List<MovieDetailsBean.ResultBean.ShortFilmListBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.film_notice_child_item,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        boolean setUp = viewHolder.film_notice_button_video.setUp(mList.get(i).getVideoUrl(), JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
        if (setUp){
          //  Glide.with(mContext).load(mList.get(i).getImageUrl()).into(viewHolder.film_notice_button_video.thumbImageView);

            FrescoLoadUtil.getInstance().loadImageBitmap(mList.get(i).getImageUrl(), new FrescoBitmapCallback<Bitmap>() {
                @Override
                public void onSuccess(Uri uri, Bitmap result) {
                    viewHolder.film_notice_button_video.coverImageView.setImageBitmap(result);
                }

                @Override
                public void onFailure(Uri uri, Throwable throwable) {

                }

                @Override
                public void onCancel(Uri uri) {

                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.film_notice_button_video)
        JCVideoPlayerStandard film_notice_button_video;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
