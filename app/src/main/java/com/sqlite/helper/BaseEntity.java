package com.sqlite.helper;

/**
 * Created by xiangjianhua on 16/10/23.
 */
public class BaseEntity extends BaseBaseEntity{

    @Column(isCompositeKey = true)
    public int id;
}
