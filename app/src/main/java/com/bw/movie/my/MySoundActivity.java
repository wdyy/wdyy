package com.bw.movie.my;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 郭佳兴
 **/
public class MySoundActivity extends BaseActivity {

    @BindView(R.id.soundtext)
    TextView soundtext;
    @BindView(R.id.soundrecycle)
    RecyclerView mSoundrecycle;
    @BindView(R.id.soundimage)
    ImageView soundimage;
    @BindView(R.id.sounSwipeRefreshLayout)
    SwipeRefreshLayout mSounSwipeRefreshLayout;
    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public int getContent() {
        return R.layout.activity_sound;
    }

    @Override
    public void success(Object data) {

    }

    @Override
    public void fail(String error) {

    }
}
