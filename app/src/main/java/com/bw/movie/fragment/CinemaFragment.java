package com.bw.movie.fragment;

import android.animation.ObjectAnimator;
import android.app.FragmentManager;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 范瑞旗
 * Date: 2019/1/24 20:50
 * Description: 影院-Fragment
 */
public class CinemaFragment extends BaseFragment {


    @BindView(R.id.cinema_search_edit)
    EditText editText_search;

    @BindView(R.id.cinema_linearLayout_search)
    LinearLayout linearLayout;

    @BindView(R.id.cinema_group)
    RadioGroup radioGroup;

    @BindView(R.id.cinema_recommend)
    RadioButton radioButton_recommend;

    @BindView(R.id.cinema_near)
    RadioButton radioButton_near;

    private android.support.v4.app.FragmentManager mManager;
    private RecommendFragment mRecommendFragment;
    private NearCinemaFragment mNearCinemaFragment;


    @Override
    public void initView(View view) {

        ButterKnife.bind(this,view);
    }

    @Override
    public void initData(View view) {

         mRecommendFragment = new RecommendFragment();
         mNearCinemaFragment = new NearCinemaFragment();

        mManager = getActivity().getSupportFragmentManager();
       // mManager.beginTransaction().replace(R.id.cinema_vp,new RecommendFragment()).commit();

        mManager.beginTransaction().add(R.id.cinema_vp,mRecommendFragment,mRecommendFragment.getClass().getName()).commit();

        /*viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment=null;

                switch (position){
                    case 0:
                        fragment = new RecommendFragment();
                        break;

                    case 1:
                        fragment = new NearCinemaFragment();
                        break;
                }
                return fragment;
            }

            @Override
            public int getCount() {
                return 2;
            }
        });*/

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.cinema_recommend:
                        radioButton_recommend.setTextColor(Color.WHITE);
                        radioButton_near.setTextColor(Color.BLACK);
                        //viewPager.setCurrentItem(0);

                        /*mManager = getActivity().getSupportFragmentManager();
                        mManager.beginTransaction().replace(R.id.cinema_vp,new RecommendFragment()).commit();*/
                        android.support.v4.app.FragmentManager movie = getActivity().getSupportFragmentManager();
                        FragmentTransaction transactionFilm = movie.beginTransaction();
                        transactionFilm.hide(mNearCinemaFragment);
                        transactionFilm.show(mRecommendFragment);
                        transactionFilm.commit();
                        break;

                    case R.id.cinema_near:
                        radioButton_recommend.setTextColor(Color.BLACK);
                        radioButton_near.setTextColor(Color.WHITE);
                        //viewPager.setCurrentItem(1);

                        /*mManager = getActivity().getSupportFragmentManager();
                        mManager.beginTransaction().replace(R.id.cinema_vp,new NearCinemaFragment()).commit();*/

                        android.support.v4.app.FragmentManager cinema = getActivity().getSupportFragmentManager();
                        FragmentTransaction transactionCinema = cinema.beginTransaction();
                        if (cinema.findFragmentByTag(mNearCinemaFragment.getClass().getName()) == null) {
                            transactionCinema.add(R.id.cinema_vp, mNearCinemaFragment, mNearCinemaFragment.getClass().getName());
                        }
                        transactionCinema.hide(mRecommendFragment);
                        transactionCinema.show(mNearCinemaFragment);
                        transactionCinema.commit();
                        break;
                }
            }
        });
    }

    @OnClick(R.id.cinema_search_img)
    public void onImgClickListener(){

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(linearLayout, "translationX", 0f, -320f);
//      设置移动时间
        objectAnimator.setDuration(1000);
//      开始动画
        objectAnimator.start();
    }

    @OnClick(R.id.cinema_search_text)
    public void onTextClickListener(){

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(linearLayout, "translationX", -320f, 0f);
//      设置移动时间
        objectAnimator.setDuration(1000);
//      开始动画
        objectAnimator.start();
    }

    @Override
    public int getContent() {
        return R.layout.fragment_success_cinema;
    }

    @Override
    public void success(Object data) {

    }

    @Override
    public void fail(String error) {

    }


}
