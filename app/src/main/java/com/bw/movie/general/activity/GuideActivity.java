package com.bw.movie.general.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Author: 范瑞旗
 * Date: 2019/1/23 14:08
 * Description: 引导页
 */
public class GuideActivity extends BaseActivity {

    @BindView(R.id.guide_pager)
    ViewPager mViewPager;

    @BindView(R.id.activity_guide_into)
    Button mButton;

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

        ButterKnife.bind(this);

        final List<Integer> list = new ArrayList<>();
        list.add(R.drawable.i1);
        list.add(R.drawable.i2);
        list.add(R.mipmap.i3);
        list.add(R.drawable.i4);

        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view==object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {

                ImageView imageView = new ImageView(container.getContext());
                imageView.setImageResource(list.get(position));

                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position==3){
                    mButton.setVisibility(View.VISIBLE);
                }else {
                    mButton.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }

    @OnClick(R.id.activity_guide_into)
    public void onBtnClickListener(){
        startActivity(new Intent(GuideActivity.this,LoginActivity.class));

    }
    @Override
    public int getContent() {
        return R.layout.activity_guide;
    }

    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public void onFail(String error) {

    }

    @Override
    public void success(Object data) {

    }

    @Override
    public void fail(String error) {

    }


}
