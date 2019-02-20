package com.bw.movie.fragment;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.BuyBean;
import com.bw.movie.bean.PayBeanTwo;
import com.bw.movie.bean.PayTranDataBean;
import com.bw.movie.fragment.activity.WXPayEntryActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 范瑞旗
 * Date: 2019/2/13 15:38
 * Description: 支付底部弹框
 */
public class PayFragment extends BaseFragment {


    @BindView(R.id.fragment_pay_img_cancel)
    ImageView PayImgCancel;
    @BindView(R.id.fragment_pay_button_wx)
    RadioButton PayButtonWx;
    @BindView(R.id.fragment_pay_button_zfb)
    RadioButton PayButtonZfb;
    @BindView(R.id.fragment_pay_group)
    RadioGroup PayGroup;
    @BindView(R.id.fragment_pat_text_pay)
    TextView PatTextPay;
    private double mTotalPrices;
    private String mOrderId;

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void initData(View view) {

        EventBus.getDefault().register(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void sjx(PayTranDataBean dataBean) {

         mTotalPrices= dataBean.getTotalPrice();
         mOrderId = dataBean.getOrderId();

        PatTextPay.setText("微信支付"+mTotalPrices+"元");



    }

    @OnClick({R.id.fragment_pay_button_wx,R.id.fragment_pay_button_zfb,R.id.fragment_pat_text_pay})
    public void onButtonClickListener(View view){

        switch (view.getId()){
            case R.id.fragment_pay_button_wx:
                PatTextPay.setText("微信支付"+mTotalPrices+"元");
                break;

            case R.id.fragment_pay_button_zfb:
                PatTextPay.setText("支付宝支付"+mTotalPrices+"元");
                break;

            case R.id.fragment_pat_text_pay:

                //Toast.makeText(getActivity(),"ff",Toast.LENGTH_SHORT).show();
                if (PayButtonWx.isChecked()){

                    Map<String, String> map = new HashMap<>();
                    map.put("payType",1+"");
                    map.put("orderId",mOrderId+"");
                    doNetRequestData(Apis.URL_PAY,map,PayBeanTwo.class,"post");


                }else if (PayButtonZfb.isChecked()){

                }
                break;
        }

    }


    @Override
    public int getContent() {
        return R.layout.fragment_pay;
    }

    @Override
    public void success(Object data) {

        if (data instanceof PayBeanTwo){

            PayBeanTwo payBeanTwo = (PayBeanTwo) data;
            Intent intent = new Intent(getActivity(), WXPayEntryActivity.class);
            intent.putExtra("paybean",payBeanTwo);
            startActivity(intent);

        }

    }

    @Override
    public void fail(String error) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
