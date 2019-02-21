package com.bw.movie.fragment;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.fragment.activity.CinemaSearchActivity;
import com.bw.movie.fragment.activity.CityPickerActivity;
import com.bw.movie.util.RequestCodeInfo;

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

    @BindView(R.id.cinema_text_position)
    TextView mTextView_position;

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
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private String adCode;
    private String cityCode;
    private String province;
    private String city;
    private double longitude;
    private double latitude;



    @Override
    public void initView(View view) {

        ButterKnife.bind(this,view);
    }

    @Override
    public void initData(View view) {

        //开始定位，这里模拟一下定位
        mlocationClient = new AMapLocationClient(getActivity());
        //设置定位监听
        mlocationClient.setLocationListener(new AMapLocationListener() {


            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if(aMapLocation!=null){
                    if(aMapLocation.getErrorCode() == 0){
                        //获取纬度
                        latitude = aMapLocation.getLatitude();
                        //获取经度
                        longitude = aMapLocation.getLongitude();
                        //城市信息
                        CinemaFragment.this.city = aMapLocation.getCity();
                        //省信息
                        province = aMapLocation.getProvince();
                        //城市编码
                        cityCode = aMapLocation.getCityCode();
                        //地区编码
                        adCode = aMapLocation.getAdCode();
                      }
                }
            }
        });
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();

        mTextView_position.setText(city);


        mRecommendFragment = new RecommendFragment();
         mNearCinemaFragment = new NearCinemaFragment();

        mManager = getActivity().getSupportFragmentManager();
       // mManager.beginTransaction().replace(R.id.cinema_vp,new RecommendFragment()).commit();

        mManager.beginTransaction().add(R.id.cinema_vp,mRecommendFragment,mRecommendFragment.getClass().getName()).commit();

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

    @OnClick({R.id.cinema_search_img,R.id.cinema_search_text,R.id.cinema_img_position})
    public void onTextClickListener(View view){

        switch (view.getId()){
            case R.id.cinema_search_img:
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(linearLayout, "translationX", 0f, -320f);
                 //      设置移动时间
                objectAnimator.setDuration(1000);
                 //      开始动画
                objectAnimator.start();
                break;

            case R.id.cinema_search_text:

                String cinemaName = editText_search.getText().toString().trim();

                if (cinemaName.length()==0){
                    ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(linearLayout, "translationX", -320f, 0f);
                    //      设置移动时间
                    objectAnimator1.setDuration(1000);
                    //      开始动画
                    objectAnimator1.start();
                }else {
                    Intent intent = new Intent(getActivity(), CinemaSearchActivity.class);
                    intent.putExtra("cinemaName",cinemaName);
                    startActivity(intent);

                }


                break;

            case R.id.cinema_img_position:  //定位点击监听

                startActivityForResult(new Intent(getActivity(),CityPickerActivity.class),RequestCodeInfo.GETCITY);

                break;

        }


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

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodeInfo.GETCITY:
                    String city=data.getExtras().getString("city");
                    if(city!= null) {
                        System.out.println("ccccccctttttt" + city);
                        mTextView_position.setText(city);
                    }
                    break;
            }
        }
    }

}
