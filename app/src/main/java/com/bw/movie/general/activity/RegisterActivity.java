package com.bw.movie.general.activity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.RegisterBean;
import com.bw.movie.precenter.IPrecenterImpl;
import com.bw.movie.util.EncryptUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.reg_edit_name)   //用户名
    EditText mTextView_name;

    @BindView(R.id.reg_sex_r1)     //性别
    RadioButton mRadioButton_r1;

    @BindView(R.id.reg_sex_r2)     //性别
    RadioButton mRadioButton_r2;

    @BindView(R.id.reg_edit_phone)     //手机号
    EditText mTextView_phone;

    @BindView(R.id.reg_edit_data)     //出生日期
    EditText mTextView_data;

    @BindView(R.id.reg_edit_email)     //邮箱
    EditText mTextView_email;

    @BindView(R.id.reg_edit_pwd)     //密码
    EditText mTextView_pwd;

    @BindView(R.id.reg_btn_login)          //注册按钮
    Button mButton_login;
    private IPrecenterImpl mIPrecenter;
    private SharedPreferences mPreferences;

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

        ButterKnife.bind(this);

        mIPrecenter = new IPrecenterImpl(this);
        mPreferences = getSharedPreferences("swl", MODE_PRIVATE);


    }

    @Override
    public int getContent() {
        return R.layout.activity_register;
    }

    @OnClick(R.id.reg_btn_login)
    public void onLoginButtonClickListener(){  //登录按钮监听

        int sex=1;
        if (mRadioButton_r1.isChecked()){
            sex=1;
        }

        if (mRadioButton_r2.isChecked()){
            sex=2;
        }

        String name = mTextView_name.getText().toString().trim();
        String pwd = mTextView_pwd.getText().toString().trim();
        String date = mTextView_data.getText().toString().trim();
        String email = mTextView_email.getText().toString().trim();
        String phone = mTextView_phone.getText().toString().trim();


        if (phone.isEmpty()||pwd.isEmpty()||name.isEmpty()||date.isEmpty()||email.isEmpty()){

            Toast.makeText(RegisterActivity.this,R.string.login_phone_pwd_isEmpty,Toast.LENGTH_SHORT).show();

        }else {

            String jmPwd = EncryptUtil.encrypt(pwd);

            Map<String, String> map = new HashMap<>();
            map.put("nickName",name);
            map.put("phone",phone);
            map.put("pwd",jmPwd);
            map.put("pwd2",jmPwd);
            map.put("sex",sex+"");
            map.put("birthday",date);
            map.put("email",email);

            doNetRequestData(Apis.URL_REGISTER,map,RegisterBean.class,"post");

        }
    }

    @OnClick(R.id.reg_edit_data)
    public void onEditClickListener() {  //登录按钮监听

        DatePickerDialog datePicker=new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                String date=i+"-"+i1+"-"+i2;
                mTextView_data.setText(date);
            }
        }, 2013, 7, 20);
        datePicker.show();

    }


    @Override
    public void success(Object data) {

        RegisterBean registerBean = (RegisterBean) data;

        String message = registerBean.getMessage();
        if (message.equals("注册成功")){

            SharedPreferences.Editor edit = mPreferences.edit();
            edit.putString("phone",mTextView_phone.getText().toString().trim());
            edit.putString("pwd",null);
            edit.commit();

            Toast.makeText(RegisterActivity.this,R.string.reg_success_toast,Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            finish();
        }else {
            Toast.makeText(RegisterActivity.this,registerBean.getMessage(),Toast.LENGTH_SHORT).show();
            //Toast.makeText(RegisterActivity.this,R.string.reg_fail_toast,Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void fail(String error) {
        Toast.makeText(RegisterActivity.this,R.string.not_NetWork,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIPrecenter.onDetach();
    }
}
