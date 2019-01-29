package com.bw.movie.fragment.popuwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;

/**
 * Author: 范瑞旗
 * Date: 2019/1/26 20:10
 * Description: 影院-PopupWindow
 */
public class PopuWindowDetails extends BaseActivity {
    private PopupWindow popupWindow;

    private Context context;
    private FragmentManager mManager;
    private ViewPager mViewPager;
    // private DetailsMovieBean.ResultBean resultBean;

    public PopuWindowDetails(Context context ) {
        this.context = context;
    }

    public void bottomwindow(View view) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        if (popupWindow != null && popupWindow.isShowing()) {
            return;
        }
        //ConstraintLayout inflate = (ConstraintLayout)getLayoutInflater().inflate(R.layout.filmactivity_item_details, null);
        ConstraintLayout inflate= (ConstraintLayout) View.inflate(context, R.layout.filmactivity_item_details, null);

        TextView textView_detail = inflate.findViewById(R.id.cinema_pw_text_detail);
        final TextView textView_comment = inflate.findViewById(R.id.cinema_pw_text_comment);
        final TextView textView_detail_line = inflate.findViewById(R.id.cinema_pw_text_detail_line);
        final TextView textView_comment_line = inflate.findViewById(R.id.cinema_pw_text_comment_line);
        /*final ViewPager viewPager = inflate.findViewById(R.id.cinema_frameLayout);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
        });*/

        textView_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textView_detail_line.setVisibility(View.VISIBLE);
                textView_comment_line.setVisibility(View.INVISIBLE);
                mViewPager.setCurrentItem(0);
                //mManager = getSupportFragmentManager();
                //mManager.beginTransaction().replace(R.id.cinema_frameLayout,new CinemaPopupDetailFragment()).commit();
            }
        });

        textView_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView_comment_line.setVisibility(View.VISIBLE);
                textView_detail_line.setVisibility(View.INVISIBLE);
                mViewPager.setCurrentItem(1);
                //mManager = getSupportFragmentManager();
                //mManager.beginTransaction().replace(R.id.cinema_frameLayout,new CinemaPopupCommentFragment()).commit();
            }
        });

        popupWindow = new PopupWindow(inflate,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //添加弹出、弹入的动画
        popupWindow.setAnimationStyle(R.style.Popupwindow);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        popupWindow.showAtLocation(view, Gravity.LEFT | Gravity.BOTTOM, 0, -location[1]);
        //添加按键事件监听
        setButtonListeners(inflate);


    }

    private void setButtonListeners(ConstraintLayout inflate) {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        //mManager = getSupportFragmentManager();
        //mManager.beginTransaction().replace(R.id.cinema_frameLayout,new CinemaPopupDetailFragment()).commit();
        //mViewPager = findViewById(R.id.cinema_frameLayout);
        /*mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
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
        });*/
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
