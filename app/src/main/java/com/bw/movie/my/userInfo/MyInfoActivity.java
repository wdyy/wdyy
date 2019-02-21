package com.bw.movie.my.userInfo;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.Constant;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.general.activity.LoginActivity;
import com.bw.movie.my.api.MineUrlConstant;
import com.bw.movie.my.updateuserinfo.MyUpdateInfoActivity;
import com.bw.movie.my.userInfo.bean.MyMessageEntity;
import com.bw.movie.my.userInfo.bean.Portrait;
import com.bw.movie.my.userInfo.bean.ResultBean;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用户信息
 * 郭佳兴
 */
public class MyInfoActivity extends BaseActivity {
    @BindView(R.id.my_headimage)
    SimpleDraweeView mMyHeadimage;
    @BindView(R.id.txt_myinfo_nikename)
    TextView mTxtMyinfoNikename;
    @BindView(R.id.txt_myinfo_sex)
    TextView mTxtMyinfoSex;
    @BindView(R.id.txt_myinfo_birthday)
    TextView mTxtMyinfoBirthday;
    @BindView(R.id.txt_myinfo_phone)
    TextView mTxtMyinfoPhone;
    @BindView(R.id.txt_myinfo_mail)
    TextView mTxtMyinfoMail;
    @BindView(R.id.my_pwd)
    ImageView mMyPwd;
    @BindView(R.id.my_updateInfo)
    Button mMyUpdateInfo;
    @BindView(R.id.my_myback)
    Button mMyMyback;
    @BindView(R.id.myinfo_back)
    ImageView mMyinfoBack;
    private ResultBean mResult;
    private String mEmail;
    private String mHeadPic;
    private int mId;
    private String mNickName;
    private String mPhone;
    private int mSex;
    private long mBrowseTime;
    private String s;

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        Map<String, String> map = new HashMap<>();
        doNetRequestData(MineUrlConstant.USERINFO, map, MyMessageEntity.class, "get");
    }

    @Override
    public int getContent() {
        return R.layout.activity_my_info;
    }

    @Override
    public void success(Object data) {
        if (data instanceof MyMessageEntity) {
            MyMessageEntity myMessageEntity = (MyMessageEntity) data;
            mResult = myMessageEntity.getResult();
            mEmail = this.mResult.getEmail();
            mHeadPic = this.mResult.getHeadPic();
            mId = this.mResult.getId();
            mNickName = this.mResult.getNickName();
            mPhone = this.mResult.getPhone();
            mSex = this.mResult.getSex();
            if (mSex == 1) {
                mTxtMyinfoSex.setText("男");
            } else if (mSex == 2) {
                mTxtMyinfoSex.setText("女");
            } else {
                mTxtMyinfoSex.setText("获取性别失败");
            }

            mTxtMyinfoMail.setText(mEmail);
            mTxtMyinfoPhone.setText(mPhone);
            mTxtMyinfoNikename.setText(mNickName);

            mBrowseTime = this.mResult.getBirthday();
            GregorianCalendar gc = new GregorianCalendar();
            s = String.valueOf(mBrowseTime);
            gc.setTimeInMillis(Long.parseLong(s));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            mTxtMyinfoBirthday.setText(df.format(gc.getTime()));
            Uri uri = Uri.parse(mHeadPic);
            mMyHeadimage.setImageURI(uri);
            EventBus.getDefault().post(new Portrait(mHeadPic));
        }


    }

    @Override
    public void fail(String error) {

    }

    @OnClick({R.id.my_headimage, R.id.txt_myinfo_nikename, R.id.txt_myinfo_sex, R.id.txt_myinfo_birthday, R.id.txt_myinfo_phone, R.id.txt_myinfo_mail, R.id.my_pwd, R.id.my_updateInfo, R.id.my_myback, R.id.myinfo_back})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.my_headimage:
                break;
            case R.id.txt_myinfo_nikename:
                break;
            case R.id.txt_myinfo_sex:
                break;
            case R.id.txt_myinfo_birthday:
                break;
            case R.id.txt_myinfo_phone:
                break;
            case R.id.txt_myinfo_mail:
                break;
            case R.id.my_pwd:
                break;
            case R.id.my_updateInfo:


                Intent intent = new Intent(this, MyUpdateInfoActivity.class);
                intent.putExtra(Constant.SEX, mSex);
                intent.putExtra("mEmail", mEmail);
                intent.putExtra(Constant.HEADPIC, mHeadPic);
                intent.putExtra(Constant.NICKNAME, mNickName);
                intent.putExtra(Constant.PHONE, mPhone);
                intent.putExtra("s", s);
                startActivity(intent);
                finish();
                break;
            case R.id.my_myback:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.myinfo_back:
                finish();
                break;
        }
    }
}
