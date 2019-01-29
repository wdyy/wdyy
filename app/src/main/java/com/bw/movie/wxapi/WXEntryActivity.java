package com.bw.movie.wxapi;

import android.content.Intent;
import android.service.carrier.CarrierMessagingService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bw.movie.R;
import com.bw.movie.apis.Apis;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.WXLoginBean;
import com.bw.movie.general.activity.SuccessActivity;
import com.bw.movie.util.WeiXinUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import java.util.HashMap;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {


    private String mCode;
    private HashMap<String, String> mMap;

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        WeiXinUtil.reg(WXEntryActivity.this).handleIntent(getIntent(),this);
    }

    @Override
    public int getContent() {
        return R.layout.activity_wxentry;
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(final BaseResp baseResp) {
        switch (baseResp.errCode){
            case BaseResp.ErrCode.ERR_OK:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCode = ((SendAuth.Resp) baseResp).code;
                        Log.d("ssss", "run: "+mCode);
                        mMap = new HashMap<>();
                        mMap.put("code",mCode);
                        doNetRequestData(Apis.URL_WX,mMap,WXLoginBean.class,"post");
                    }
                });
                break;
        }
    }

    @Override
    public void success(Object data) {
        if (data instanceof WXLoginBean){
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


}
