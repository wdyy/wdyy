package com.bw.movie.my.updateuserpwd;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.general.activity.LoginActivity;
import com.bw.movie.my.api.MineUrlConstant;
import com.bw.movie.my.updateuserpwd.bean.UpdatePwdEntity;
import com.bw.movie.util.EncryptUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyUpdatePwdActivity extends BaseActivity {

    @BindView(R.id.update_oldpwd)
    EditText mUpdateOldpwd;
    @BindView(R.id.update_newPwd)
    EditText mUpdateNewPwd;
    @BindView(R.id.update_recoverpwd)
    EditText mUpdateRecoverpwd;
    @BindView(R.id.updatePwdback)
    Button mUpdatePwdback;
    @BindView(R.id.myinfo_back)
    ImageView mMyinfoBack;

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {

        Map<String, String> map = new HashMap<>();

        doNetRequestData(MineUrlConstant.UPDATEPWD, map, UpdatePwdEntity.class, "post");
    }

    @Override
    public int getContent() {
        return R.layout.activity_my_update_pwd;
    }

    @Override
    public void success(Object data) {

    }

    @Override
    public void fail(String error) {

    }

    @OnClick({R.id.updatePwdback, R.id.myinfo_back})
    public void onClick(View v) {

        String oldPwd = mUpdateOldpwd.getText().toString();
        String newPwd = mUpdateNewPwd.getText().toString();
        String copyPwd = mUpdateRecoverpwd.getText().toString();

        String oldPwd1 = EncryptUtil.encrypt(oldPwd);
        String newPwd1 = EncryptUtil.encrypt(newPwd);
        String copyPwd1 = EncryptUtil.encrypt(copyPwd);
        switch (v.getId()) {
            default:
                break;
            case R.id.updatePwdback:

                if (TextUtils.isEmpty(newPwd) && TextUtils.isEmpty(copyPwd)){
                    Toast.makeText(this,"新密码为空",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Map<String,String> map  = new HashMap<>();
                    map.put("oldpwd",oldPwd1);
                    map.put("newpwd",newPwd1);
                    map.put("copypwd",copyPwd1);
                    doNetRequestData(MineUrlConstant.UPDATEPWD,map,UpdatePwdEntity.class,"post");
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }
                break;
            case R.id.myinfo_back:
                finish();
                break;
        }
    }
}
