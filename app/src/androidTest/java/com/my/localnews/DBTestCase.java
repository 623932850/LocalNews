package com.my.localnews;

import android.test.AndroidTestCase;

import com.sqlite.helper.Column;
import com.sqlite.helper.TestEntity;

import java.lang.reflect.Field;

/**
 * Created by xiangjianhua on 16/10/23.
 */
public class DBTestCase extends AndroidTestCase{

    public void testAnotation(){

        Class<TestEntity> clazz = TestEntity.class;
        try {
            Field field = clazz.getDeclaredField("name");
            Column column = field.getAnnotation(Column.class);
            String columnName = column.columnName();
            System.out.println("ssssssssssssssssssssss==>"+columnName);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


    }
}
