package com.bw.movie.fragment.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.BuyBean;
import com.bw.movie.bean.CinemaSeatTableDetailBean;
import com.bw.movie.bean.PayTranDataBean;
import com.bw.movie.custom.SeatTable;
import com.bw.movie.fragment.PayFragment;
import com.bw.movie.util.EncryptUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: 范瑞旗
 * Date: 2019/1/28 10:33
 * Description: 选座-Activity
 */
public class CinemaSeatTableActivity extends BaseActivity {

    @BindView(R.id.cinema_seat_table_text_beginTime)
    TextView mTextView_beginTime;

    @BindView(R.id.cinema_seat_table_text_endTime)
    TextView mTextView_endTime;

    @BindView(R.id.cinema_seat_table_text_hall)
    TextView mTextView_hall;

    @BindView(R.id.item_cinema_seat_text_price)
    TextView mTextView_price;

    @BindView(R.id.item_cinema_detail_img_v)
    ImageView mImageView_v;

    public SeatTable seatTableView;
    @BindView(R.id.item_cinema_detail_img_x)
    ImageView mImageView_x;
    double totalPrice=0;
    private String mHall;
    private double mPrice;
    int num;
    int mScheduleId;


    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        seatTableView = (SeatTable) findViewById(R.id.seatView);
        seatTableView.setScreenName(mHall);//设置屏幕名称
        seatTableView.setMaxSelected(3);//设置最多选中

        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                if (column == 2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if (row == 6 && column == 6) {
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {

                num++;
                totalPrice+=mPrice;


                mTextView_price.setText(totalPrice + "");

            }

            @Override
            public void unCheck(int row, int column) {

                num--;
                totalPrice-=mPrice;


                mTextView_price.setText(totalPrice + "");
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatTableView.setData(10, 15);

    }

    @OnClick(R.id.item_cinema_detail_img_x)
    public void onImgXClickListener() {
        finish();
    }

    @OnClick(R.id.item_cinema_detail_img_v)
    public void onImgVClickListener() {

        if (num==0){
            Toast.makeText(CinemaSeatTableActivity.this,"请选择座位！",Toast.LENGTH_SHORT).show();
        }else {
            SharedPreferences swl = getSharedPreferences("swl",MODE_PRIVATE);
            String userId = swl.getString("userId", "");
            String sign=userId+mScheduleId+num+"movie";
            //String jmSign = EncryptUtil.encrypt(sign);
            String jmSign = MD5(sign);

            Map<String, String> map = new HashMap<>();
            map.put("scheduleId",mScheduleId+"");
            map.put("amount",num+"");
            map.put("sign",jmSign);

            doNetRequestData(Apis.URL_BUY,map,BuyBean.class,"post");


        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void sjx(CinemaSeatTableDetailBean detailsBean) {

        mTextView_beginTime.setText(detailsBean.getBeginTime());
        mTextView_endTime.setText(detailsBean.getEndTime());

        mHall = detailsBean.getHall();
        mTextView_hall.setText(mHall);
        mPrice = detailsBean.getPrice();

        mScheduleId = detailsBean.getScheduleId();


    }

    @Override
    public int getContent() {
        return R.layout.activity_cinema_seat_table;
    }

    @Override
    public void success(Object data) {

        if (data instanceof BuyBean){
            BuyBean buyBean = (BuyBean) data;
            String message = buyBean.getMessage();
            Toast.makeText(CinemaSeatTableActivity.this,message,Toast.LENGTH_SHORT).show();

            if (message.equals("下单成功")) {  //下单成功则弹出支付

                String orderId = buyBean.getOrderId();
                PayTranDataBean dataBean = new PayTranDataBean(orderId,totalPrice);

                EventBus.getDefault().postSticky(dataBean);

                PayFragment payFragment = new PayFragment();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.seat_fragment, payFragment);
                transaction.commit();


            }
        }

    }

    @Override
    public void fail(String error) {

    }

    /**
     *  MD5加密
     * @param sourceStr
     * @return
     */
    public  String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
