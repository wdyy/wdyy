package com.bw.movie.movie.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bw.movie.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: 邵文龙
 * Date: 2019/1/28 16:04
 * Description: ${DESCRIPTION}
 */
public class FilmStillsAdapter extends RecyclerView.Adapter<FilmStillsAdapter.ViewHolder> {
    Context mContext;
    List<Bitmap> imgs = new ArrayList<>();

    public FilmStillsAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public FilmStillsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = View.inflate(mContext, R.layout.film_stills_child_item, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmStillsAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mStillsitemimgs.setImageBitmap(imgs.get(i));
    }

    @Override
    public int getItemCount() {
        return imgs.size();
    }

    public void setData(List<Bitmap> mlist) {
        imgs = mlist;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mStillsitemimgs;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mStillsitemimgs = itemView.findViewById(R.id.stills_item_img);
        }
    }
}
