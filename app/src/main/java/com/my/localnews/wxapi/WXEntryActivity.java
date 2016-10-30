package com.my.localnews.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.my.localnews.LocalNewsApplication;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

/**
 * Created by xiangjianhua on 16/10/23.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalNewsApplication.getInstance().wxApi.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LocalNewsApplication.getInstance().wxApi.handleIntent(intent, this);

    }
}
