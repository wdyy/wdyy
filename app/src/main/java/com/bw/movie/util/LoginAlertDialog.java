package com.bw.movie.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.bw.movie.R;
import com.bw.movie.general.activity.LoginActivity;
import com.bw.movie.general.activity.MainActivity;

/**
 * Author: 范瑞旗
 * Date: 2019/2/15 21:12
 * Description: ${DESCRIPTION}
 */
public class LoginAlertDialog extends AppCompatActivity {
    private Context mContext;

    public LoginAlertDialog(Context context) {
        mContext = context;
    }

    public void alert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("提示：");
        builder.setMessage("请先登录！");
        builder.setIcon(R.mipmap.ic_launcher_round);
        //点击对话框以外的区域是否让对话框消失
        builder.setCancelable(true);
        //设置正面按钮
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //设置反面按钮
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                startActivity(new Intent(mContext,LoginActivity.class));
                //startActivityS();
            }
        });
        AlertDialog dialog = builder.create();
        //显示对话框
        dialog.show();
    }

    public void startActivityS(){
        startActivity(new Intent(mContext,LoginActivity.class));
    }

}
