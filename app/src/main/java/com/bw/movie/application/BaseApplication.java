package com.bw.movie.application;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
//import com.tencent.android.tpush.XGPushConfig;


public class BaseApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        context = getApplicationContext();
        //initXG();
    }

    public static Context getApplication() {
        return context;
    }

   /* //初始化信鸽
    private void initXG() {
        XGPushConfig.enableDebug(this, true);
        XGPushConfig.enableOtherPush(getApplicationContext(), true);
        XGPushConfig.setHuaweiDebug(true);
        XGPushConfig.setMiPushAppId(getApplicationContext(), "d71d384497c51");
        XGPushConfig.setMiPushAppKey(getApplicationContext(), "A44FJ9N7N9EY");
        XGPushConfig.setMzPushAppId(this, "d71d384497c51");
        XGPushConfig.setMzPushAppKey(this, "A44FJ9N7N9EY");
    }*/
}
