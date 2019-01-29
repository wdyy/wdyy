package com.bw.movie.fragment.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.fragment.CinemaPopupCommentFragment;
import com.bw.movie.fragment.CinemaPopupDetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: 范瑞旗
 * Date: 2019/1/28 16:17
 * Description: ${DESCRIPTION}
 */
public class CinemaPopupActivity extends BaseActivity {

    @BindView(R.id.cinema_detail_viewPager)
    ViewPager mViewPager;

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

        ButterKnife.bind(this);

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment=null;

                switch (position){
                    case 0:
                        fragment = new CinemaPopupDetailFragment();
                        break;

                    case 1:
                        fragment = new CinemaPopupCommentFragment();
                        break;
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
    }

    @Override
    public int getContent() {
        return R.layout.filmactivity_item_details;
    }

    @Override
    public void success(Object data) {

    }

    @Override
    public void fail(String error) {

    }
}
