package com.bw.movie.my;

import android.content.Intent;
import android.graphics.Rect;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.general.activity.LoginActivity;
import com.bw.movie.my.feedback.MyFeedbackActivity;
import com.bw.movie.my.latest.MyLatestVersionActivity;
import com.bw.movie.my.userInfo.MyInfoActivity;
import com.bw.movie.my.userfollow.MyFollowActivity;
import com.bw.movie.my.userticket.MyTicketActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author: 郭佳兴
 * Date: 2019/1/24 20:51
 * Description: ${DESCRIPTION}
 */
public class MineFragment extends BaseFragment {
    @BindView(R.id.laba)
    ImageView mLaba;
    @BindView(R.id.myicon)
    ImageView mMyicon;
    @BindView(R.id.nickname)
    TextView mNickname;
    @BindView(R.id.qian)
    Button mQian;
    @BindView(R.id.liner)
    LinearLayout mLiner;
    @BindView(R.id.my_message)
    ImageView mMyMessage;
    @BindView(R.id.my_follow)
    ImageView mMyFollow;
    @BindView(R.id.my_tickets)
    ImageView mMyTickets;
    @BindView(R.id.liner2)
    LinearLayout mLiner2;
    @BindView(R.id.my_fankui)
    ImageView mMyFankui;
    @BindView(R.id.my_geng)
    ImageView mMyGeng;
    @BindView(R.id.login_edit)
    ImageView mLoginEdit;
    private View view;
    private Unbinder unbinder;

    @Override
    public void initView(View view) {

    }

    @Override
    public void initData(View view) {

        ButterKnife.bind(this, view);

    }

    @Override
    public int getContent() {
        return R.layout.fragment_success_mine;
    }

    @Override
    public void success(Object data) {


    }

    @Override
    public void fail(String error) {


    }

    @OnClick({R.id.laba, R.id.myicon, R.id.nickname, R.id.qian, R.id.liner, R.id.my_message, R.id.my_follow, R.id.my_tickets, R.id.liner2, R.id.my_fankui, R.id.my_geng, R.id.login_edit})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.laba:
                startActivity(new Intent(getActivity(), MySoundActivity.class));
                break;
            case R.id.myicon:
                Intent intent = new Intent(getContext(), ScaleImageActivity.class);
                //创建一个Rect,报错当前imageview的位置信息
                Rect rect = new Rect();
                //将位置信息赋给rect
                mMyicon.getGlobalVisibleRect(rect);
                intent.setSourceBounds(rect);
                //跳转
                startActivity(intent);
                //屏蔽activity跳转的默认专场效果
                getActivity().overridePendingTransition(0, 0);


                break;
            case R.id.nickname:
                break;
            case R.id.qian:
                break;
            case R.id.liner:
                break;
            case R.id.my_message:
                startActivity(new Intent(getActivity(), MyInfoActivity.class));
                break;
            case R.id.my_follow:
                startActivity(new Intent(getActivity(), MyFollowActivity.class));
                break;
            case R.id.my_tickets:
                startActivity(new Intent(getActivity(), MyTicketActivity.class));
                break;
            case R.id.liner2:
                break;
            case R.id.my_fankui:
                startActivity(new Intent(getActivity(), MyFeedbackActivity.class));
                break;
            case R.id.my_geng:
                startActivity(new Intent(getActivity(), MyLatestVersionActivity.class));
                break;
            case R.id.login_edit:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }
}
