package com.bw.movie.my.userticket.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.my.userticket.bean.ResultBean;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;


public class TicketInforAdapter extends RecyclerView.Adapter<TicketInforAdapter.MyTicketViewHolder> {

    private Context mContext;
    private List<ResultBean> result;

    public TicketInforAdapter(Context context, List<ResultBean> result) {
        mContext = context;
        this.result = result;
    }

    @NonNull
    @Override
    public MyTicketViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_ticket, viewGroup, false);
        MyTicketViewHolder ticketViewHolder = new MyTicketViewHolder(view, mHttpClick);
        return ticketViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyTicketViewHolder myTicketViewHolder, int i) {
        if (result.get(i).getStatus() != 2) {
            //填充布局
            myTicketViewHolder.addresstext.setText(result.get(i).getCinemaName());
            myTicketViewHolder.datetext.setText(result.get(i).getBeginTime() + "");
            myTicketViewHolder.dingdantext.setText(result.get(i).getOrderId() + "");
            myTicketViewHolder.moneytext.setText(result.get(i).getPrice() + "");
            myTicketViewHolder.numtext.setText(result.get(i).getAmount() + "");
            myTicketViewHolder.nametext.setText(result.get(i).getCinemaName());
            //时间转换
            String createTime = result.get(i).getCreateTime();
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTimeInMillis(Long.parseLong(createTime));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            myTicketViewHolder.longText.setText(df.format(gc.getTime()));
            if (result.get(i).getStatus() == 1) {
                myTicketViewHolder.nopay.setText("去付款");
            } else if (result.get(i).getStatus() == 2) {
                myTicketViewHolder.nopay.setText("已完成");
            }
        }

    }


    @Override
    public int getItemCount() {
        return result == null ? 0 : result.size();
    }


    class MyTicketViewHolder extends RecyclerView.ViewHolder {

        private final TextView dingdantext;
        private final TextView addresstext;
        private final TextView nametext;
        private final TextView moneytext;
        private final TextView datetext;
        private final TextView numtext, longText;
        private Button nopay;

        public MyTicketViewHolder(@NonNull View itemView, final HttpClick httpClick) {
            super(itemView);

            //寻找布局
            dingdantext = itemView.findViewById(R.id.user_shop_token_adapter_dingdan_text);
            addresstext = itemView.findViewById(R.id.user_shop_token_adapter_address_text);
            nametext = itemView.findViewById(R.id.user_shop_token_adapter_cimera_name_text);
            datetext = itemView.findViewById(R.id.user_shop_token_adapter_date_text);
            moneytext = itemView.findViewById(R.id.user_shop_token_adapter_money_text);
            numtext = itemView.findViewById(R.id.user_shop_token_adapter_num_text);
            longText = itemView.findViewById(R.id.longText);
            nopay = itemView.findViewById(R.id.user_shop_token_adapter_nopay);
            nopay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mHttpClick.click(v, getAdapterPosition());
                }
            });

        }
    }

    private HttpClick mHttpClick;

    public void setHttpClick(HttpClick httpClick) {
        mHttpClick = httpClick;
    }

    public interface HttpClick {
        void click(View view, int position);
    }
}
