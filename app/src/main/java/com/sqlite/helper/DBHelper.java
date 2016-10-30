package com.sqlite.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.my.localnews.utils.LogUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangjianhua on 16/10/18.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String TAG = DBHelper.class.getSimpleName();
    private static final String DB_NAME = "news_db";
    private static final int DB_VERSION = 1;
    private Class<?>[] tableEntitys = {TestEntity.class};

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        for (int i = 0, len = tableEntitys.length; i < len; i++) {
            Class<?> clazz = tableEntitys[i];
            if (clazz == null) {
                continue;
            }
            //查找表名
            String tableName = null;
            Table table = clazz.getAnnotation(Table.class);
            if (table != null) {
                if (TextUtils.isEmpty(table.tableName())) {
                    tableName = clazz.getSimpleName();
                } else {
                    tableName = table.tableName();
                }
            }
            //查找子段和主键
            Field[] fields = clazz.getDeclaredFields();
            List<DBColumn> dbColumns = new ArrayList<>();
            List<DBColumn> keyColumns = new ArrayList<>();
            if (fields != null) {
                for (Field field : fields) {
                    Column column = field.getAnnotation(Column.class);
                    if (column == null) {
                        continue;
                    }
                    DBColumn dbColumn = new DBColumn();
                    if (TextUtils.isEmpty(column.columnName())) {
                        dbColumn.name = field.getName();
                    } else {
                        dbColumn.name = column.columnName();
                    }
                    dbColumn.isNull = column.isNull();
                    dbColumn.isUnique = column.isUnique();
                    dbColumn.isCompositeKey = column.isCompositeKey();
                    dbColumn.dataType = field.getType();
                    dbColumns.add(dbColumn);
                    if (dbColumn.isCompositeKey) {
                        keyColumns.add(dbColumn);
                    }
                }

            }
            StringBuilder builder = new StringBuilder();
            builder.append("CREATE TABLE ");
            builder.append(tableName);
            builder.append("(");
            for (DBColumn dbColumn : dbColumns) {
                builder.append(dbColumn.name);
                builder.append(" " + convertDataType(dbColumn.dataType));
                if (!dbColumn.isNull) {
                    builder.append(" NOT NULL");
                }
                if (dbColumn.isUnique) {
                    builder.append(" UNIQUE");
                }
                builder.append(",");
            }
            builder.append("PRIMARY KEY (");
            for (int j = 0, keySize = keyColumns.size(); j < keySize; j++) {
                DBColumn key = keyColumns.get(j);
                if (j < keySize - 1) {
                    builder.append(key.name + ",");
                } else {
                    builder.append(key.name);
                }
            }
            builder.append(")");
            builder.append(")");
            LogUtils.logI(TAG, "create table:"+builder.toString());
            sqLiteDatabase.execSQL(builder.toString());
        }
    }

    public String convertDataType(Class<?> dataType) {
        return "";
    }

    public static class DBColumn {

        public String name;
        public boolean isUnique;
        public boolean isNull;
        public boolean isCompositeKey;
        public Class<?> dataType;

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
