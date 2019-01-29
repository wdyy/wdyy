package com.bw.movie.my.updateuserinfo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.Constant;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.my.api.MineUrlConstant;
import com.bw.movie.my.updatehead.bean.UpdateHeadEntity;
import com.bw.movie.my.updateuserpwd.MyUpdatePwdActivity;
import com.bw.movie.my.userInfo.MyInfoActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyUpdateInfoActivity extends BaseActivity {

    @BindView(R.id.mtouxiang)
    SimpleDraweeView mMtouxiang;
    @BindView(R.id.mnicheng)
    TextView mMnicheng;
    @BindView(R.id.mxingbie)
    TextView mMxingbie;
    @BindView(R.id.mshoujihao)
    TextView mMshoujihao;
    @BindView(R.id.myouxiang)
    TextView mMyouxiang;
    @BindView(R.id.mriqi)
    TextView mMriqi;
    @BindView(R.id.chongzhimima)
    ImageView mChongzhimima;

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        int sex1 = intent.getIntExtra(Constant.SEX, 0);
        if ("1".equals(sex1)) {
            mMxingbie.setText("男");
        } else if ("2".equals(sex1)) {
            mMxingbie.setText("女");
        }

        String email = intent.getStringExtra("mEmail");
        mMyouxiang.setText(email);
        String headPic = intent.getStringExtra(Constant.HEADPIC);
        if (headPic == null) {
            return;
        } else {
            Uri uri = Uri.parse(headPic);
            mMtouxiang.setImageURI(uri);
        }
        String nickName = intent.getStringExtra(Constant.NICKNAME);
        mMnicheng.setText(nickName);
        String phone1 = intent.getStringExtra(Constant.PHONE);
        mMshoujihao.setText(phone1);
        String s = intent.getStringExtra("s");
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(Long.parseLong(s));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        mMriqi.setText(df.format(gc.getTime()));


    }

    @Override
    public int getContent() {
        return R.layout.activity_my_update_info;
    }

    @Override
    public void success(Object data) {

    }

    @Override
    public void fail(String error) {

    }

    @OnClick({R.id.mtouxiang, R.id.mnicheng, R.id.mxingbie, R.id.mriqi, R.id.mshoujihao, R.id.myouxiang, R.id.update_myinfo, R.id.chongzhimima})
    public void onClick(View v) {
        String nickname = mMnicheng.getText().toString().trim();
        String sex = mMxingbie.getText().toString();
        String email = mMyouxiang.getText().toString();

        switch (v.getId()) {
            case R.id.mtouxiang:
                break;
            case R.id.mnicheng:
                break;
            case R.id.mxingbie:
                break;
            case R.id.mriqi:
                break;
            case R.id.mshoujihao:
                break;
            case R.id.myouxiang:
                break;

            case R.id.update_myinfo:

                Integer q = -1;
                if ("男".equals(sex)) {
                    q = 1;
                } else if ("女".equals(sex)) {
                    q = 2;
                }
                if (TextUtils.isEmpty(nickname) || TextUtils.isEmpty(sex) || TextUtils.isEmpty(email)) {
                    Toast.makeText(this, "请输入修改内容", Toast.LENGTH_SHORT).show();
                } else {
                    mMxingbie.setText(q + "");
                    //请求网络
                    Map<String, String> map = new HashMap<>();
                    map.put(Constant.NICKNAME, nickname);
                    map.put(Constant.SEX, String.valueOf(q));
                    map.put(Constant.EMAIL, email);
                    doNetRequestData(MineUrlConstant.UPDATEUSERINFO, map, UpdateHeadEntity.class, "post");
                    startActivity(new Intent(this, MyInfoActivity.class));
                    finish();
                }

                break;

            case R.id.chongzhimima:
                startActivity(new Intent(this, MyUpdatePwdActivity.class));
                finish();
                break;
        }
    }
}
