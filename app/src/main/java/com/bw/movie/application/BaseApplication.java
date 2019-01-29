package com.bw.movie.application;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;


public class BaseApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        context=getApplicationContext();
    }

    public static Context getApplication(){
        return context;
    }
}
