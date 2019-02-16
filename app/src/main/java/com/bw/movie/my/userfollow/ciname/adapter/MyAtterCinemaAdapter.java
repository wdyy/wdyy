package com.bw.movie.my.userfollow.ciname.adapter;

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
import com.bw.movie.my.userfollow.ciname.bean.ResultBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * 郭佳兴
 **/
public class MyAtterCinemaAdapter extends RecyclerView.Adapter<MyAtterCinemaAdapter.MyViewHolder> {
    private Context mContext;
    private List<ResultBean> mResult;

    public MyAtterCinemaAdapter(Context context, List<ResultBean> result) {
        mContext = context;
        mResult = result;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.attcinema, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ResultBean resultBean = mResult.get(position);

        String logo = resultBean.getLogo();
        if (logo == null) {
            int launcher = R.mipmap.ic_launcher;
            String s = String.valueOf(launcher);
            holder.simple.setImageURI(s);
        } else {
            Uri uri = Uri.parse(logo);
            holder.simple.setImageURI(uri);
        }

        holder.text1.setText(resultBean.getName());
        holder.text2.setText(resultBean.getAddress());
    }


    @Override
    public int getItemCount() {
        return mResult == null ? 0 : mResult.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView simple;
        TextView text1, text2, text3;
        ImageView mAttCinemaImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            simple = itemView.findViewById(R.id.attcinemasimple);
            text1 = itemView.findViewById(R.id.attcinematext1);
            text2 = itemView.findViewById(R.id.attcinematext2);
            text3 = itemView.findViewById(R.id.attcinematext3);
            mAttCinemaImage = itemView.findViewById(R.id.attCinemaImage);
            mAttCinemaImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHttpClick.getClick(v, getAdapterPosition());
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
