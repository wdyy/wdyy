package com.bw.movie.my.latest;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.my.api.MineUrlConstant;
import com.bw.movie.my.latest.bean.MyLatestUser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 版本更新
 * 郭佳兴
 */
public class MyLatestVersionActivity extends BaseActivity {


    private String mDownloadUrl;

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        doNetRequestData(MineUrlConstant.DETECTIONUPDATE, null, MyLatestUser.class, "get");
    }

    @Override
    public int getContent() {
        return R.layout.activity_my_latest_version;
    }

    @Override
    public void success(Object data) {
        if (data instanceof MyLatestUser) {
            MyLatestUser myLatestUser = (MyLatestUser) data;
            String message = myLatestUser.getMessage();
            int flag = myLatestUser.getFlag();

            Toast.makeText(this, message + flag, Toast.LENGTH_SHORT).show();

            mDownloadUrl = myLatestUser.getDownloadUrl();

            if (mDownloadUrl == null) {
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(R.mipmap.ic_launcher);
            } else {
                showUpdataDialog();
            }
        }
    }

    @Override
    public void fail(String error) {

    }

    protected void showUpdataDialog() {
        AlertDialog.Builder builer = new AlertDialog.Builder(this);
        builer.setTitle("版本升级");
        builer.setMessage("软件更新");
        //当点确定按钮时从服务器上下载 新的apk 然后安装
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downLoadApk();
            }
        });
        //当点取消按钮时不做任何举动
        builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builer.create();
        dialog.show();
    }


    protected void downLoadApk() {
        //进度条
        final ProgressDialog pd;
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = getFileFromServer(mDownloadUrl, pd);
                    //安装APK
                    installApk(file);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                }
            }
        }.start();
    }

    public static File getFileFromServer(String path, ProgressDialog pd) throws Exception {
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            File file = new File(Environment.getExternalStorageDirectory(), "updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                //获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }

    protected void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }
}
