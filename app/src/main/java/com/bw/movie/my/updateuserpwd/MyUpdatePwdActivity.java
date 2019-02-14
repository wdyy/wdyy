package com.bw.movie.my.updateuserpwd;

import android.content.Intent;
import android.content.SharedPreferences;
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
    }

    @Override
    public int getContent() {
        return R.layout.activity_my_update_pwd;
    }

    @Override
    public void success(Object data) {
        UpdatePwdEntity updatePwdEntity = (UpdatePwdEntity) data;
        String message = updatePwdEntity.getMessage();
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void fail(String error) {
        Toast.makeText(getApplicationContext(), "修改失败!", Toast.LENGTH_LONG).show();
    }

    @OnClick({R.id.updatePwdback, R.id.myinfo_back})
    public void onClick(View v) {
        SharedPreferences swl = getSharedPreferences("swl", MODE_PRIVATE);
        swl.getString("pwd", "");
        //   mUpdateOldpwd.setText("");
        String oldPwd = mUpdateOldpwd.getText().toString().trim();
        String newPwd = mUpdateNewPwd.getText().toString().trim();
        String copyPwd = mUpdateRecoverpwd.getText().toString().trim();

        String oldPwd1 = EncryptUtil.encrypt(oldPwd);
        String newPwd1 = EncryptUtil.encrypt(newPwd);
        String copyPwd1 = EncryptUtil.encrypt(copyPwd);
        switch (v.getId()) {
            default:
                break;
            case R.id.updatePwdback:

                if (TextUtils.isEmpty(newPwd) && TextUtils.isEmpty(copyPwd)) {
                    Toast.makeText(this, "新密码为空", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Map<String, String> map = new HashMap<>();
                    map.put("oldPwd", oldPwd1);
                    map.put("newPwd", newPwd1);
                    map.put("newPwd2", copyPwd1);
                    doNetRequestData(MineUrlConstant.UPDATEPWD, map, UpdatePwdEntity.class, "post");
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
