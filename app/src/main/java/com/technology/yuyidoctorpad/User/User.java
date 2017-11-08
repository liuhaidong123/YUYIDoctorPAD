package com.technology.yuyidoctorpad.User;

import android.content.Context;
import android.content.SharedPreferences;

import com.technology.yuyidoctorpad.lzhUtils.Empty;

/**
 * Created by liuhaidong on 2017/11/1.
 */
public class User {
    public static String token = "2BA9A707D7191E777CB5834D44AC1943";//服务器返回的token
    public static String tele = "17743516301";//用户名（手机号）
    public static boolean isLogin(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("USER", Context.MODE_APPEND);
        String userToken = preferences.getString("userToken", "0");
        String userTelePhone = preferences.getString("telePhone", "0");
        if (Empty.notEmpty(userToken) &&Empty.notEmpty(userTelePhone)
                && !"0".equals(userToken) && !"0".equals(userTelePhone)) {
            token = userToken;//存储到类中
            tele = userTelePhone;//存储到类中
            return true;
        }
        return false;
    }
    public static void saveLogin(Context con,String UsToken, String UsTele){
        SharedPreferences pre = con.getSharedPreferences("USER",Context.MODE_APPEND);
        SharedPreferences.Editor edi = pre.edit();
        edi.putString("userToken",UsToken);
        edi.putString("telePhone",UsTele);
        User.token=UsToken;
        User.tele=UsTele;
        edi.commit();
    }
    public static void clearLogin(Context con){
        SharedPreferences pre = con.getSharedPreferences("USER",Context.MODE_APPEND);
        SharedPreferences.Editor edi = pre.edit();
        edi.remove("userToken");
        edi.remove("telePhone");
        edi.remove("JPSH");//是否注册过激光的别名
        edi.commit();
        token="";
        tele="";
    }
    //清除注册的jpsh
    public static void removeJPSH(Context con){
        SharedPreferences pre = con.getSharedPreferences("USER",Context.MODE_APPEND);
        SharedPreferences.Editor edi = pre.edit();
        edi.remove("JPSH");
        edi.commit();
    }
    //激光别名是否注册成功
    public static void initJPSH(Context con,boolean flag){
        SharedPreferences pre = con.getSharedPreferences("USER",Context.MODE_APPEND);
        SharedPreferences.Editor edi = pre.edit();
        edi.putBoolean("JPSH",flag);
        edi.commit();
    }
}
