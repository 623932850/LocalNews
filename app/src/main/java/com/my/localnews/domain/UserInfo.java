package com.my.localnews.domain;

import java.util.List;

/**
 * Created by xiangjianhua on 16/10/30.
 */
public class UserInfo {

    public long id;
    public String username;
    public boolean locked;
    public String avatar;
    public List<NewsTags> news_tag;
    public List<TopicTags> topic_tag;
}

