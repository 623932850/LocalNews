package com.my.localnews;

import android.content.Intent;
import android.os.Bundle;

import com.my.localnews.domain.UserInfo;

import org.apache.http.Header;


/**
 * Created by xiangjianhua on 16/10/24.
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        RequestManager.getInstance().registerListener(handler);
        RequestManager.getInstance().request(RequestManager.REQCODE_LOGIN);
    }

    public void enterMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    BeanRequestCallback<UserInfo> handler = new BeanRequestCallback<UserInfo>(UserInfo.class) {
        @Override
        public void onSuccess(int reqCode, UserInfo bean) {
            if (reqCode == RequestManager.REQCODE_LOGIN) {
                enterMainActivity();
            }
        }

        @Override
        public void onBusinessFailure(int reqCode, int errorCode, String errorMsg) {

        }

        @Override
        public void onNetFailure(int reqCode, int statusCode, Header[] headers, String responseString, Throwable throwable) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestManager.getInstance().unRegisterListener(handler);
    }
}
