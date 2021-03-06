package com.bw.movie.general.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.LoginBean;
import com.bw.movie.bean.RefreshBean;
import com.bw.movie.my.XinUser;
import com.bw.movie.my.api.MineUrlConstant;
import com.bw.movie.precenter.IPrecenterImpl;
import com.bw.movie.util.EncryptUtil;
import com.bw.movie.util.NotifyUtil;
import com.bw.movie.util.ToastUtil;
import com.bw.movie.util.WeiXinUtil;
import com.bw.movie.view.IView;
/*import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;*/
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

/**
 * Author: 范瑞旗
 * Date: 2019/1/23 16:08
 * Description: 登录页
 */
public class LoginActivity extends BaseActivity{

    @BindView(R.id.login_edit_phone)   //手机号
            TextView mTextView_phone;

    @BindView(R.id.login_edit_pwd)     //密码
            TextView mTextView_pwd;

    @BindView(R.id.login_checkbox_rememberPwd)  //记住密码
            CheckBox mCheckBox_rememberPwd;

    @BindView(R.id.login_checkbox_autoLogin)  //记住密码
            CheckBox mCheckBox_autoLogin;

    @BindView(R.id.login_text_register)    //注册
            TextView mTextView_register;

    @BindView(R.id.login_btn_login)          //登录按钮
            Button mButton_login;

    @BindView(R.id.login_img_dsf)
            ImageView mImageView;
    private boolean b=true;
    private IPrecenterImpl mIPrecenter;
    private SharedPreferences mPreferences;
    private boolean mCheck;
    private int mRequestcode = (int) SystemClock.uptimeMillis();
    private NotifyUtil currentNotify;

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

        ButterKnife.bind(this);

        mIPrecenter = new IPrecenterImpl(this);

        mPreferences = getSharedPreferences("swl", MODE_PRIVATE);


        mCheck = mPreferences.getBoolean("check", false);

        boolean auto = mPreferences.getBoolean("auto", false);
        String String_phone = mPreferences.getString("phone", null);
        String String_pwd = mPreferences.getString("pwd", null);
        if (mCheck){
            mTextView_phone.setText(String_phone);
            mTextView_pwd.setText(String_pwd);
            mCheckBox_rememberPwd.setChecked(true);
        }
        if (auto){
            startActivity(new Intent(LoginActivity.this,SuccessActivity.class));
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mCheck){
            String String_phone = mPreferences.getString("phone", null);
            String String_pwd = mPreferences.getString("pwd", null);
            mTextView_phone.setText(String_phone);
            mTextView_pwd.setText(String_pwd);
        }
    }

    @Override
    public int getContent() {
        return R.layout.activity_login;
    }

    @OnClick({R.id.login_btn_login, R.id.login_img_dsf, R.id.login_text_over, R.id.login_text_register})
    public void onLoginButtonClickListener(View view) {
        switch (view.getId()) {
            case R.id.login_btn_login:
                //登录按钮监听

                String phone = mTextView_phone.getText().toString().trim();
                String pwd = mTextView_pwd.getText().toString().trim();

                String regex = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|16[1|6]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
                boolean matches = Pattern.matches(regex, phone);

                if (phone.isEmpty()||pwd.isEmpty()){

                    Toast.makeText(LoginActivity.this,R.string.login_phone_pwd_isEmpty,Toast.LENGTH_SHORT).show();

                }else if (!matches){
                    Toast.makeText(LoginActivity.this,R.string.edit_phone_error,Toast.LENGTH_SHORT).show();

                }else{

                    String jmPwd = EncryptUtil.encrypt(pwd);

                    Map<String, String> map = new HashMap<>();
                    map.put("phone",phone);
                    map.put("pwd",jmPwd);

                    doNetRequestData(Apis.URL_LOGIN,map,LoginBean.class,"post");
                    map.put("phone", phone);
                    map.put("pwd", jmPwd);
                    doNetRequestData(Apis.URL_LOGIN, map, LoginBean.class, "post");
                }
                /*XGPushManager.registerPush(this, new XGIOperateCallback() {
                    @Override
                    public void onSuccess(Object o, int i) {
                        final String da = String.valueOf(o);
                     //   Log.d("TPush", da);
                        new IPrecenterImpl(new IView() {
                            @Override
                            public void onSuccess(Object data) {
                                if (data instanceof XinUser) {
                                    XinUser xinUser = (XinUser) data;
                                    String message = xinUser.getMessage();
                                    ToastUtil.Toast(message);
                                    Map<String, String> map = new HashMap<>();
                                    map.put("token", da);
                                    map.put("os", String.valueOf(1));
                                    doNetRequestData(MineUrlConstant.XINGELINGPAI, map, XinUser.class, "post");
                                }
                            }

                            @Override
                            public void onFail(String error) {

                            }
                        });
                    }

                    @Override
                    public void onFail(Object o, int i, String s) {
                        Log.d("TPush", "注册失败，错误码：" + i + ",错误信息：" + s);
                    }
                });*/
                break;
            case R.id.login_img_dsf:
                //微信登录
                if (!WeiXinUtil.success(this)) {
                    Toast.makeText(this, "请先安装应用", Toast.LENGTH_SHORT).show();
                } else {
                    //  验证
                    SendAuth.Req req = new SendAuth.Req();
                    req.scope = "snsapi_userinfo";
                    req.state = "wechat_sdk_demo_test";
                    WeiXinUtil.reg(LoginActivity.this).sendReq(req);
                }
                break;

            case R.id.login_text_over:
                startActivity(new Intent(LoginActivity.this,SuccessActivity.class));
                break;
            case R.id.login_text_register:
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                break;
            default:
                break;
        }
    }

    @OnTouch(R.id.login_img_show)            //明文密文
    public boolean onTouch(View v, MotionEvent event){

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mTextView_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                break;
            case MotionEvent.ACTION_UP:
                mTextView_pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                break;

        }
        return true;
    }


    @Override
    public void success(Object data) {

        LoginBean loginBean = (LoginBean) data;

        String message = loginBean.getMessage();
        if (message.equals("登陆成功")) {
            if (mCheckBox_rememberPwd.isChecked()) {  //保存账号密码
                SharedPreferences.Editor edit = mPreferences.edit();
                edit.putString("phone", mTextView_phone.getText().toString().trim());
                edit.putString("pwd", mTextView_pwd.getText().toString().trim());
                edit.putBoolean("check", true);
                edit.commit();

            } else {

            }

            if (mCheckBox_autoLogin.isChecked()) {  //自动登录
                SharedPreferences.Editor edit = mPreferences.edit();
                edit.putBoolean("auto", true);
                edit.commit();

            } else {

            }
            LoginBean.ResultBean beanResult = loginBean.getResult();
            String sessionId = beanResult.getSessionId();
            int userId = beanResult.getUserId();
            String headPic = beanResult.getUserInfo().getHeadPic();
            String nickName = beanResult.getUserInfo().getNickName();

            SharedPreferences.Editor edit = mPreferences.edit();  //保存用户的sessionId

            edit.putString("sessionId", sessionId);
            edit.putString("userId", userId + "");
            edit.putString("headPic", headPic);
            edit.putString("nickName", nickName);
            edit.putString("ak", "0110010010000");
            edit.commit();


            Intent intent = new Intent(this, SuccessActivity.class);
            startActivity(intent);
            PendingIntent pIntent = PendingIntent.getActivity(this,
                    mRequestcode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            int smallIcon = R.mipmap.ic_launcher;
            int largeIcon = R.mipmap.ffanhui;
            String ticker = "您有一条新通知";
            String title = "登录";
            String content = "用户" + nickName + "登陆成功";
            ArrayList<String> messageList = new ArrayList<String>();
            messageList.add("");
            String content1 = "[" + messageList.size() + "条]" + title + ": " + messageList.get(0);

            NotifyUtil notify1 = new NotifyUtil(this, 1);
            notify1.notify_mailbox(pIntent, smallIcon, largeIcon, messageList, ticker,
                    title, content, true, true, false);
            currentNotify = notify1;
        } else {
            Toast.makeText(LoginActivity.this, R.string.login_fail_toast, Toast.LENGTH_SHORT).show();
            String ff = "ff";
            EventBus.getDefault().postSticky(new RefreshBean(ff));
            finish();
        }

    }

    @Override
    public void fail(String error) {
    //    Toast.makeText(LoginActivity.this,"fa",Toast.LENGTH_SHORT).show();
        //showShortToast(R.string.not_NetWork+"");
        Toast.makeText(LoginActivity.this,error,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIPrecenter.onDetach();
    }
}
