package com.my.localnews.domain;

/**
 * Created by xiangjianhua on 16/10/30.
 */
public class NewsTags {

    public Long id;
    public String create_time;
    public String name;

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof NewsTags)) {
            return false;
        }
        NewsTags newsTags = (NewsTags) o;
        return newsTags.id != null && newsTags.id.equals(id);
    }
}
