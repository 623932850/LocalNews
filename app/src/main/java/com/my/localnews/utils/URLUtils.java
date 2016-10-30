package com.my.localnews.utils;

import com.my.localnews.LocalNewsApplication;

/**
 * Created by xiangjianhua on 16/10/27.
 */
public class URLUtils {

    public static final String PROTOCAL = "http";
    public static final String IP = "120.76.194.233";
//    public static final String LOGIN = "/v1/mobile/login";
//    public static final String NEWS_TAGS ="/v1/news_tags";
//    public static final String MY_NEWS_TAGS = "/v1/mobile/me/news_tags";
//    public static final String ADD_NEWS_TAGS = "/v1/mobile/me/news_tags/{news_tag_id}";


    public static String buildUrl(int resId, Object... formatArgs) {
        StringBuilder builder = new StringBuilder();
        builder.append(PROTOCAL);
        builder.append("://");
        builder.append(IP);
        String path = LocalNewsApplication.getInstance().getString(resId, formatArgs);
        builder.append(path);


        return builder.toString();
    }
}
