package com.bw.movie.my.userticket;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.my.userticket.paid.PaidFragment;
import com.bw.movie.my.userticket.unpaid.UnpaidFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTicketActivity extends BaseActivity {

    @BindView(R.id.ticket_rb1)
    RadioButton ticketRb1;
    @BindView(R.id.ticket_rb2)
    RadioButton ticketRb2;
    @BindView(R.id.ticket_rg)
    RadioGroup ticketRg;
    @BindView(R.id.ticket_fragment)
    FrameLayout ticketFragment;
    @BindView(R.id.ticket_pager)
    ViewPager ticketPager;
    @BindView(R.id.ticket_image)
    ImageView ticketImage;
    private FragmentManager mManager;
    private FragmentTransaction mTransaction;

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        mManager = getSupportFragmentManager();
        mTransaction = mManager.beginTransaction();

        final PaidFragment paidFragment = new PaidFragment();
        final UnpaidFragment unpaidFragment = new UnpaidFragment();

        mTransaction.add(R.id.ticket_fragment, unpaidFragment);
        mTransaction.add(R.id.ticket_fragment, paidFragment);

        mTransaction.show(unpaidFragment);
        mTransaction.hide(paidFragment);

        mTransaction.commit();

        ticketRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction fragmentTransaction = mManager.beginTransaction();
                switch (checkedId) {
                    case R.id.ticket_rb1:
                        fragmentTransaction.show(unpaidFragment);
                        fragmentTransaction.hide(paidFragment);
                        break;
                    case R.id.ticket_rb2:
                        fragmentTransaction.show(paidFragment);
                        fragmentTransaction.hide(unpaidFragment);
                        break;
                }
                fragmentTransaction.commit();
            }
        });

        ticketImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public int getContent() {
        return R.layout.activity_my_ticket;
    }

    @Override
    public void success(Object data) {

    }

    @Override
    public void fail(String error) {

    }
}
