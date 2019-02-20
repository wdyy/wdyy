package com.bw.movie.wxapi;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.service.carrier.CarrierMessagingService;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.WXLoginBean;
import com.bw.movie.general.activity.SuccessActivity;
import com.bw.movie.util.WeiXinUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import static com.bw.movie.util.WeiXinUtil.APP_ID;
import static com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneSession;
import static com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req.WXSceneTimeline;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {


    private String mCode;
    private HashMap<String, String> mMap;
    private IWXAPI iwxapi;

    enum SHARE_TYPE {Type_WXSceneSession, Type_WXSceneTimeline}

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        WeiXinUtil.reg(WXEntryActivity.this).handleIntent(getIntent(), this);
        iwxapi = WXAPIFactory.createWXAPI(this, APP_ID, false);
        iwxapi.handleIntent(getIntent(), this);
        iwxapi.registerApp(APP_ID);
    }

    @Override
    public int getContent() {
        return R.layout.activity_wxentry;
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    public void shareWXSceneSession(View view) {
        share(SHARE_TYPE.Type_WXSceneSession);
    }
    public void shareWXSceneTimeline(View view) {
        share(SHARE_TYPE.Type_WXSceneTimeline);
    }

    @Override
    public void onResp(final BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCode = ((SendAuth.Resp) baseResp).code;
                        Log.d("ssss", "run: " + mCode);
                        mMap = new HashMap<>();
                        mMap.put("code", mCode);
                        doNetRequestData(Apis.URL_WX, mMap, WXLoginBean.class, "post");
                    }
                });
                finish();
                break;
        }
    }

    @Override
    public void success(Object data) {
        if (data instanceof WXLoginBean) {
            WXLoginBean wxLoginBean = (WXLoginBean) data;
            if (wxLoginBean.getStatus().equals("0000")) {
                Intent intent = new Intent(this, SuccessActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void fail(String error) {

    }

    private void share(SHARE_TYPE type) {

        //6.0以上主动请求权限
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }

        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = "http://www.dangdang.com/?_utm_sem_id=16202047&_ddclickunion=422-kw-1-%C6%B7%C5%C6%B4%CA_%C6%B7%C5%C6%B4%CA-%BA%CB%D0%C4_%B5%B1%B5%B1%CD%F8|ad_type=0|sys_id=1";
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = "Sunny Cinema";
        msg.description = "This is a movie about the software";
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.ali);
        msg.thumbData = bmpToByteArray(thumb, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("Req");
        req.message = msg;
        switch (type) {
            case Type_WXSceneSession:
                req.scene = WXSceneSession;
                break;
            case Type_WXSceneTimeline:
                req.scene = WXSceneTimeline;
                break;
        }
        iwxapi.sendReq(req);
        finish();
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
