package com.bw.movie.my.userfollow;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.my.userfollow.ciname.AttentioncinemaFragment;
import com.bw.movie.my.userfollow.film.AtterionFilmFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用户关注
 * 郭佳兴
 */
public class MyFollowActivity extends BaseActivity {


    @BindView(R.id.attenrb1)
    RadioButton mAttenrb1;
    @BindView(R.id.attenrb2)
    RadioButton mAttenrb2;
    @BindView(R.id.attenrg)
    RadioGroup mAttenrg;
    @BindView(R.id.attenfragment)
    FrameLayout mAttenfragment;
    @BindView(R.id.attenpager)
    ViewPager mAttenpager;
    @BindView(R.id.attenimage3)
    ImageView mAttenimage3;
    private FragmentManager mManager;
    private FragmentTransaction mTransaction;
    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        ButterKnife.bind(this);

        //开启事务
        mManager = getSupportFragmentManager();
        mTransaction = mManager.beginTransaction();

        final AttentioncinemaFragment fragment1 = new AttentioncinemaFragment();
        final AtterionFilmFragment fragment2 = new AtterionFilmFragment();

        mTransaction.add(R.id.attenfragment, fragment1);
        mTransaction.add(R.id.attenfragment, fragment2);

        mTransaction.show(fragment1);
        mTransaction.hide(fragment2);
        //提交事务
        mTransaction.commit();

        mAttenrg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction beginTransaction = mManager.beginTransaction();
                switch (checkedId) {
                    case R.id.attenrb1:
                        beginTransaction.show(fragment1);
                        beginTransaction.hide(fragment2);
                        break;
                    case R.id.attenrb2:
                        beginTransaction.show(fragment2);
                        beginTransaction.hide(fragment1);
                }
                beginTransaction.commit();
            }
        });
    }

    @Override
    public int getContent() {
        return R.layout.activity_my_follow;
    }

    @Override
    public void success(Object data) {

    }

    @Override
    public void fail(String error) {

    }
    @OnClick({R.id.attenrb1, R.id.attenrb2, R.id.attenrg, R.id.attenfragment, R.id.attenpager, R.id.attenimage3})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.attenrb1:
                break;
            case R.id.attenrb2:
                break;
            case R.id.attenrg:
                break;
            case R.id.attenfragment:
                break;
            case R.id.attenpager:
                break;
            case R.id.attenimage3:
                finish();
                break;
        }
    }
}
