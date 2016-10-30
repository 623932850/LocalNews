package com.sqlite.helper;

/**
 * Created by xiangjianhua on 16/10/18.
 */
@Table(tableName = "testEntity")
public class TestEntity extends BaseEntity{


    @Column(columnName = "name")
    private String name;
    @Column(columnName = "age")
    public int age;

}
