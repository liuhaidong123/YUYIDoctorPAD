package com.technology.yuyidoctorpad.DbUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wanyu on 2017/9/27.
 */
//接口数据库
public class IOpenHelper extends SQLiteOpenHelper{
    public IOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "network.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //name 接口名，value接口的数据
        String sql="create table inter(id Integer primary key,name varchar unique,value varchar)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
