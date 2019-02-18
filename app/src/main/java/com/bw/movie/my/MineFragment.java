package com.bw.movie.my;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.general.activity.LoginActivity;
import com.bw.movie.my.api.MineUrlConstant;
import com.bw.movie.my.feedback.MyFeedbackActivity;
import com.bw.movie.my.latest.MyLatestVersionActivity;
import com.bw.movie.my.mysound.MySoundActivity;
import com.bw.movie.my.userInfo.MyInfoActivity;
import com.bw.movie.my.userfollow.MyFollowActivity;
import com.bw.movie.my.userticket.MyTicketActivity;
import com.bw.movie.util.ToastUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

/**
 * 郭佳兴
 */
public class MineFragment extends BaseFragment {
    @BindView(R.id.laba)
    ImageView mLaba;
    @BindView(R.id.myicon)
    SimpleDraweeView mMyicon;
    @BindView(R.id.nickname)
    TextView mNickname;
    @BindView(R.id.qian)
    Button mQian;
    @BindView(R.id.liner)
    LinearLayout mLiner;
    @BindView(R.id.my_message)
    LinearLayout mMyMessage;
    @BindView(R.id.my_follow)
    LinearLayout mMyFollow;
    @BindView(R.id.my_tickets)
    LinearLayout mMyTickets;
    @BindView(R.id.liner2)
    LinearLayout mLiner2;
    @BindView(R.id.my_fankui)
    LinearLayout mMyFankui;
    @BindView(R.id.my_geng)
    LinearLayout mMyGeng;
    @BindView(R.id.login_edit)
    LinearLayout mLoginEdit;
    private SharedPreferences mSwl;
    private SharedPreferences.Editor mEdit;

    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    public void initData(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mSwl = getActivity().getSharedPreferences("swl", MODE_PRIVATE);
        mEdit = mSwl.edit();
        String headPic = mSwl.getString("headPic", "");
        mMyicon.setImageURI(Uri.parse(headPic));
        String nickName = mSwl.getString("nickName", "");
        mNickname.setText(nickName);

    }

    @Override
    public int getContent() {
        return R.layout.fragment_success_mine;
    }

    @Override
    public void success(Object data) {
        if (data instanceof RegisterBean) {
            RegisterBean registerBean = (RegisterBean) data;
            String status = registerBean.getStatus();
            if (status.equals("0000")) {
                ToastUtil.Toast("签到成功");
            }
        }
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
                String sessionId = mSwl.getString("sessionId", "");
                if (sessionId.equals("")) {
                    ToastUtil.Toast("系统检测到您未登录,请先登录");
                } else {
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
                }
                break;
            case R.id.nickname:
                break;
            case R.id.qian:
                doNetRequestData(MineUrlConstant.QIAAAAN, null, RegisterBean.class, "get");
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
                mEdit.clear();
                mEdit.commit();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }
}
