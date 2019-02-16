package com.bw.movie.my.userfollow.film.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.my.userfollow.film.bean.ResultBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 郭佳兴
 **/
public class MyAtteretionfilmAdapter extends RecyclerView.Adapter<MyAtteretionfilmAdapter.MyViewHolder> {

    private Context mContext;
    private List<ResultBean> mList;

    public MyAtteretionfilmAdapter(Context context, List<ResultBean> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.attcinema, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view, mHttpClick);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        ResultBean bean = mList.get(i);
        String imageUrl = bean.getImageUrl();
        if (imageUrl == null) {
            int launcher = R.mipmap.ic_launcher;
            String s = String.valueOf(launcher);
            myViewHolder.simple.setImageURI(s);
        } else {
            Uri uri = Uri.parse(bean.getImageUrl());
            myViewHolder.simple.setImageURI(uri);
        }
        myViewHolder.text1.setText(bean.getName());
        myViewHolder.text2.setText(bean.getSummary());
        long browseTime = bean.getReleaseTime();
        GregorianCalendar gc = new GregorianCalendar();
        String s = String.valueOf(browseTime);
        gc.setTimeInMillis(Long.parseLong(s));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        myViewHolder.text3.setText(df.format(gc.getTime()));
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView simple;
        TextView text1, text2, text3;
        ImageView attCinemaImage;

        public MyViewHolder(@NonNull View itemView, final HttpClick httpClick) {
            super(itemView);
            simple = itemView.findViewById(R.id.attcinemasimple);
            text1 = itemView.findViewById(R.id.attcinematext1);
            text2 = itemView.findViewById(R.id.attcinematext2);
            text3 = itemView.findViewById(R.id.attcinematext3);
            attCinemaImage = itemView.findViewById(R.id.attCinemaImage);
            attCinemaImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    httpClick.getClick(v, getAdapterPosition());
                }
            });
        }
    }

    private HttpClick mHttpClick;

    public void setHttpClick(HttpClick httpClick) {
        mHttpClick = httpClick;
    }

    public interface HttpClick {
        void getClick(View view, int position);
    }
}
