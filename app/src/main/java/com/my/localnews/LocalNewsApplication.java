package com.my.localnews;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.my.localnews.utils.BitmapUtils;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by xiangjianhua on 16/10/23.
 */
public class LocalNewsApplication extends Application {

    public static final String WEIXIN_APP_ID = "wx9cfe89c4c0ac3bf8";
    public IWXAPI wxApi;
    private static LocalNewsApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        regWX();
    }

    public static LocalNewsApplication getInstance(){
        return instance;
    }

    public void regWX() {
        wxApi = WXAPIFactory.createWXAPI(this, WEIXIN_APP_ID, true);
        wxApi.registerApp(WEIXIN_APP_ID);
    }

    public void shareToFriendCircle(){
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "http://www.qq.com";
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "网页标题";
        msg.description = "网页描述";
        Bitmap thump = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        msg.thumbData = BitmapUtils.bmpToByteArray(thump, true);

        SendMessageToWX .Req req = new SendMessageToWX.Req();
        req.transaction = "";
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        wxApi.sendReq(req);
    }
}
