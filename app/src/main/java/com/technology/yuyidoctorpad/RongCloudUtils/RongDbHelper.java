package com.technology.yuyidoctorpad.RongCloudUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wanyu on 2017/11/2.
 */

public class RongDbHelper  extends SQLiteOpenHelper {
    public RongDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "rong.db", null, 1);
    }
    /**
     * UserId：融云id
     * name:昵称
     *image：头像url（含前缀）
     * */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //name 接口名，value接口的数据
        String sql="create table rong(id Integer primary key,UserId varchar unique,name varchar,image varchar)";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
