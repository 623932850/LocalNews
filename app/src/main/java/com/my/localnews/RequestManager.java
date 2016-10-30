package com.my.localnews;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.my.localnews.utils.LogUtils;
import com.my.localnews.utils.URLUtils;
import com.my.localnews.utils.Utils;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by xiangjianhua on 16/10/30.
 */
public class RequestManager {

    public static final String TAG = RequestManager.class.getSimpleName();
    public static final int BASE_USER_REQCODE = 0x1000;
    public static final int REQCODE_LOGIN = BASE_USER_REQCODE + 1;

    public static final int BASE_NEWS_REQCODE = 0x2000;
    public static final int REQCODE_NEWS_TAGS = BASE_NEWS_REQCODE + 1;
    public static final int REQCODE_ADD_NEWS_TAGS = BASE_NEWS_REQCODE + 2;
    public static final int REQCODE_MY_NEWS_TAGS = BASE_NEWS_REQCODE + 3;

    private static RequestManager instance;
    private AsyncHttpClient httpClient;
    private List<RequestCallback> listeners = Collections.synchronizedList(new ArrayList<RequestCallback>());

    public void registerListener(RequestCallback callback) {
        listeners.add(callback);
    }

    public void unRegisterListener(RequestCallback callback) {
        listeners.remove(callback);
    }

    private RequestManager() {
        httpClient = new AsyncHttpClient();
        httpClient.addHeader("token", Utils.getSerialNum());
    }

    public static RequestManager getInstance() {
        synchronized (TAG) {
            if (instance == null) {
                synchronized (TAG) {
                    instance = new RequestManager();
                }
            }
        }
        return instance;
    }

    public void request(int reqCode) {
        request(reqCode, null, null, null);
    }

    public void request(int reqCode, String url){
        request(reqCode, null, url, null);
    }

    public void request(int reqCode, Object tag, String url, RequestParams params) {
        switch (reqCode) {
            case REQCODE_LOGIN:
                get(REQCODE_LOGIN, tag, URLUtils.buildUrl(R.string.LOGIN));
                break;
            case REQCODE_NEWS_TAGS:
                get(REQCODE_NEWS_TAGS, tag, URLUtils.buildUrl(R.string.NEWS_TAGS));
                break;
            case REQCODE_ADD_NEWS_TAGS:
                put(REQCODE_ADD_NEWS_TAGS, tag, url);
                break;
            case REQCODE_MY_NEWS_TAGS:
                get(REQCODE_MY_NEWS_TAGS, tag, URLUtils.buildUrl(R.string.MY_NEWS_TAGS));
                break;
            default:
                break;
        }
    }

    class MyHttpResponseHandler extends TextHttpResponseHandler {

        int reqCode;

        MyHttpResponseHandler(int reqCode) {
            this.reqCode = reqCode;
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, String responseStr) {
            LogUtils.logI(TAG, "statusCode=" + statusCode);
            LogUtils.logI(TAG, "headers=" + Arrays.toString(headers));
            LogUtils.logI(TAG, "response=" + responseStr);

            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(responseStr);
//            try {
            int code = 0;
            String errorMsg = "";
            String data = jsonElement.toString();
//                if (response.has("code")) {
//                    code = response.getInt("code");
//                }
//                if (response.has("errorMsg")) {
//                    errorMsg = response.getString("errorMsg");
//                }
//                if (response.has("data")) {
//                    data = response.getJSONObject("data").toString();
//                }
            if (code == 0) {
                for (RequestCallback callback : listeners) {
                    if (callback == null) {
                        continue;
                    }
                    callback.onSuccess(reqCode, data);
                }
            } else {
                for (RequestCallback callback : listeners) {
                    if (callback == null) {
                        continue;
                    }
                    callback.onBusinessFailure(reqCode, code, errorMsg);
                }
            }
//            } catch (JSONException e) {
//                e.printStackTrace();
//                for (RequestCallback callback : listeners) {
//                    if (callback == null) {
//                        continue;
//                    }
//                    callback.onBusinessFailure(reqCode, RequestCallback.ERRORCODE_FORMAT, e.getMessage());
//                }
//            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            LogUtils.logI(TAG, "statusCode=" + statusCode);
            LogUtils.logI(TAG, "headers=" + Arrays.toString(headers));
            LogUtils.logI(TAG, "responseString=" + responseString);
            LogUtils.logI(TAG, "throwable=" + throwable);
            for (RequestCallback callback : listeners) {
                if (callback == null) {
                    continue;
                }
                callback.onNetFailure(reqCode, statusCode, headers, responseString, throwable);
            }
        }
    }

    private void get(int reqCode, Object tag, String url) {
        MyHttpResponseHandler handler = new MyHttpResponseHandler(reqCode);
        if (tag != null) {
            handler.setTag(tag);
        }
        httpClient.get(url, handler);
    }

    private void put(int reqCode, Object tag, String url) {
        MyHttpResponseHandler handler = new MyHttpResponseHandler(reqCode);
        if (tag != null) {
            handler.setTag(tag);
        }
        httpClient.put(url, handler);
    }
}
