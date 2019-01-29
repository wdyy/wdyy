package com.bw.movie.general.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bw.movie.R;

public class MainActivity extends AppCompatActivity {

    private int t=3;
    boolean start;
    private SharedPreferences mPreferences;

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            t=0;
            if (t==0){
                handler.removeMessages(1);
                if (start){
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }else {
                    startActivity(new Intent(MainActivity.this,GuideActivity.class));
                }
            }
            handler.sendEmptyMessageDelayed(1,2000);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPreferences = getSharedPreferences("swl", MODE_PRIVATE);

        start = mPreferences.getBoolean("start", false);

        handler.sendEmptyMessageDelayed(1,2000);


        SharedPreferences.Editor edit = mPreferences.edit();
        edit.putBoolean("start",true);
        edit.commit();
    }
    @Override
    protected void onStop() {
        super.onStop();
        handler.removeMessages(1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(1);
    }
}
