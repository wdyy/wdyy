package com.bw.movie.my.mysound;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.my.api.MineUrlConstant;
import com.bw.movie.my.mysound.adapter.MySoundAdapter;
import com.bw.movie.my.mysound.bean.MySoundUser;
import com.bw.movie.my.mysound.bean.ResultBean;
import com.bw.movie.my.mysound.bean.UpdateSoundUser;
import com.bw.movie.my.mysound.bean.XiSoundUser;
import com.bw.movie.precenter.IPrecenterImpl;
import com.bw.movie.util.ToastUtil;
import com.bw.movie.view.IView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 系统消息
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
    private List<ResultBean> mList;
    private MySoundAdapter mMySoundAdapter;
    private int page = 1;
    private int mCount;

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        //查询未读系统消息条数
        doNetRequestData(MineUrlConstant.WEIMESSAGE, null, XiSoundUser.class, "get");
        new IPrecenterImpl(new IView() {

            @Override
            public void onSuccess(Object data) {
                if (data instanceof XiSoundUser) {
                    XiSoundUser xiSoundUser = (XiSoundUser) data;
                    mCount = xiSoundUser.getCount();
                    soundtext.setText("系统消息  (" + mCount + "条未读" + ")");
                }
            }

            @Override
            public void onFail(String error) {
                ToastUtil.Toast("暂无未读消息");
            }
        });

        //查询系统消息
        doNetRequestData(MineUrlConstant.XITONGMESSAGE, null, MySoundUser.class, "get");
        mSounSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doNetRequestData(MineUrlConstant.XITONGMESSAGE + page++, null, MySoundUser.class, "get");
            }
        });
    }

    @Override
    public int getContent() {
        return R.layout.activity_sound;
    }

    @Override
    public void success(Object data) {
        mSounSwipeRefreshLayout.setRefreshing(false);
        if (data instanceof MySoundUser) {
            MySoundUser mySoundUser = (MySoundUser) data;
            mList = mySoundUser.getResult();
            if (mList != null && mList.size() > 0) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                mSoundrecycle.setLayoutManager(linearLayoutManager);
                mMySoundAdapter = new MySoundAdapter(getApplicationContext(), mList);
                mMySoundAdapter.setHttpClick(new MySoundAdapter.HttpClick() {
                    @Override
                    public void getClick(View view, final int position) {
                        //点击修改系统消息状态
                        doNetRequestData(MineUrlConstant.MESSAGEUPDATE, null, UpdateSoundUser.class, "get");
                        new IPrecenterImpl(new IView() {
                            @Override
                            public void onSuccess(Object data) {
                                if (data instanceof UpdateSoundUser) {
                                    UpdateSoundUser updateSoundUser = (UpdateSoundUser) data;
                                    String message = updateSoundUser.getMessage();
                                    int status = mList.get(position).getStatus();
                                    if (status == 1) {
                                        ToastUtil.Toast("状态已修改");
                                        return;
                                    } else {
                                        if (message.equals("状态改变成功")) {
                                            if (mCount > 0) {
                                                mCount--;
                                                soundtext.setText("系统消息  (" + mCount + "条未读" + ")");
                                                return;
                                            } else {
                                                soundtext.setText("系统消息  (" + 0 + "条未读" + ")");
                                                return;
                                            }
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFail(String error) {

                            }
                        });
                    }
                });
                mMySoundAdapter.notifyDataSetChanged();
                mSoundrecycle.setAdapter(mMySoundAdapter);
            } else {
                ToastUtil.Toast("没有更多数据了");
            }
            mSounSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void fail(String error) {

    }

    @OnClick({R.id.soundimage})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.soundimage:
                finish();
                break;

        }
    }
}
