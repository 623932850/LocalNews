package com.my.localnews.utils;

import android.util.Log;

/**
 * Created by xiangjianhua on 16/10/23.
 */
public class LogUtils {

    public static void logI(String tag, String message){
        Log.i(tag, message);
    }

    public static void logE(String tag, String message){
        Log.e(tag, message);
    }
}
