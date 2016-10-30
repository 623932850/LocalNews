package com.my.localnews;

import org.apache.http.Header;

/**
 * Created by xiangjianhua on 16/10/30.
 */
public interface RequestCallback {

    public static final int BASE_LOCAL_ERROR = 0x80000000;
    public static final int ERRORCODE_FORMAT = BASE_LOCAL_ERROR + 1;

    public void onSuccess(int reqCode, String response);

    public void onBusinessFailure(int reqCode, int errorCode, String errorMsg);

    public void onNetFailure(int reqCode, int statusCode, Header[] headers, String responseString, Throwable throwable);
}
