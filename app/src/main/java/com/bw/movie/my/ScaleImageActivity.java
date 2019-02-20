package com.bw.movie.my;

import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 元素转场动画
 */

public class ScaleImageActivity extends AppCompatActivity {

    private SimpleDraweeView mMyTouxiang;
    private Rect mSourceBounds;
    private int mSourceWidth;
    private int mSourceHeigth;
    //当前界面的目标图片的位置
    private Rect mTargetRect = new Rect();
    //前后两个图片的收缩比
    private float mScaleWidth, mScaleHeight;
    //前后两个图片的位移距离
    private float mTransitionX, mTransitionY;
    //动画时间
    public static final int DURATION = 300;
    //动画插值器
    private static final AccelerateDecelerateInterpolator DEFAULT_INTERPOLATOR = new AccelerateDecelerateInterpolator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_image);

        //初始化控件
        initView();
        //初始化场景
        initBehavior();
    }


    private void initView() {
        mMyTouxiang = (SimpleDraweeView) findViewById(R.id.my_touxiang);
        SharedPreferences swl = getSharedPreferences("swl", MODE_PRIVATE);
        String headPic = swl.getString("headPic", "");
        Uri uri = Uri.parse(headPic);
        mMyTouxiang.setImageURI(uri);
        //    String headPic = SpUtil.getString("headPic", "");
        //    Uri uri = Uri.parse(headPic);
        //   mMyTouxiang.setImageURI(uri);

        mMyTouxiang.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                chuExitAnim();
            }
        });
    }


    private void initBehavior() {
        //获取上一个界面传过来的Rect
        mSourceBounds = getIntent().getSourceBounds();
        //计算上一个界面图片的宽度和高度
        mSourceWidth = mSourceBounds.right - mSourceBounds.left;
        mSourceHeigth = mSourceBounds.bottom - mSourceBounds.top;

        //当界面的imageView测量完成后,即高度和宽度确定后
        mMyTouxiang.post(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                //获取目标imageView在布局中的位置
                mMyTouxiang.getGlobalVisibleRect(mTargetRect);
                //更改mImageView的位置,使其和上一个界面的图片的位置重合
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mSourceWidth, mSourceHeigth);
                params.setMargins(mSourceBounds.left, mSourceBounds.top - getStatusBarHeight() - getActionBarHeight(),
                        mSourceBounds.right, mSourceBounds.bottom);
                mMyTouxiang.setLayoutParams(params);
                //计算图片缩放比例和位移
                calculateInfo();
                //设置入场动画
                runEnterAnim();
            }
        });
    }

    //获取状态栏高度
    private int getStatusBarHeight() {
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            return getResources().getDimensionPixelSize(resourceId);
        }
        return -1;
    }

    //获取actionBar的高度
    private int getActionBarHeight() {
        //如果有ActionBar
        if (getSupportActionBar() != null) {
            return getSupportActionBar().getHeight();
        }
        return 0;
    }

    private void calculateInfo() {
        // 计算目标imageView的宽高
        int targetWidth = mTargetRect.right - mTargetRect.left;
        int targetHeight = mTargetRect.bottom - mTargetRect.top;
        //获得收缩比
        mScaleWidth = (float) targetWidth / mSourceWidth;
        mScaleHeight = (float) targetHeight / mSourceHeigth;
        //x,y上的位移
        mTransitionX = (mTargetRect.left + (mTargetRect.right - mTargetRect.left) / 2)
                - (mSourceBounds.left + (mSourceBounds.right - mSourceBounds.left) / 2);
        mTransitionY = (mTargetRect.top + (mTargetRect.bottom - mTargetRect.top) / 2)
                - (mSourceBounds.top + (mSourceBounds.bottom - mSourceBounds.top) / 2);
    }


    //入场动画
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void runEnterAnim() {
        mMyTouxiang.animate()
                .setInterpolator(DEFAULT_INTERPOLATOR)
                .setDuration(DURATION)
                .scaleX(mScaleWidth)
                .scaleY(mScaleHeight)
                .translationX(mTransitionX)
                .translationY(mTransitionY)
                //withEndAction要求版本在16以上
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                    }
                })
                .start();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void chuExitAnim() {
        mMyTouxiang.animate()
                .setInterpolator(DEFAULT_INTERPOLATOR)
                .setDuration(DURATION)
                .scaleX(1)
                .scaleY(1)
                .translationX(0)
                .translationY(0)
                //withEndAction要求版本在16以上
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        overridePendingTransition(0, 0);
                    }
                })
                .start();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {
        // 使用退场动画
        chuExitAnim();
    }
}
