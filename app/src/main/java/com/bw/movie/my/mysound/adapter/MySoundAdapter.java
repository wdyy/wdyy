package com.bw.movie.my.mysound.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.my.mysound.bean.ResultBean;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 郭佳兴
 **/
public class MySoundAdapter extends RecyclerView.Adapter<MySoundAdapter.MyViewHolder> {
    private Context mContext;
    private List<ResultBean> mList;

    public MySoundAdapter(Context context, List<ResultBean> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.mysound, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view, mHttpClick);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        ResultBean bean = mList.get(i);
        myViewHolder.text1.setText(bean.getTitle());
        myViewHolder.text2.setText(bean.getContent());
        long browseTime = bean.getPushTime();
        GregorianCalendar gc = new GregorianCalendar();
        String s = String.valueOf(browseTime);
        gc.setTimeInMillis(Long.parseLong(s));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        myViewHolder.text3.setText(df.format(gc.getTime()));
        if (bean.getStatus() == 0) {
            myViewHolder.text4.setVisibility(View.VISIBLE);
            myViewHolder.text4.setText("1");
        } else {
            myViewHolder.text4.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView text1, text2, text3;
        RadioButton text4;

        public MyViewHolder(@NonNull View itemView, final HttpClick httpClick) {
            super(itemView);
            text1 = itemView.findViewById(R.id.soundtext1);
            text2 = itemView.findViewById(R.id.soundtext2);
            text3 = itemView.findViewById(R.id.soundtext3);
            text4 = itemView.findViewById(R.id.soundtext4);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    httpClick.getClick(v, getAdapterPosition());
                    text4.setVisibility(View.GONE);
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
