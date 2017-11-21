package com.technology.yuyidoctorpad.User;

import android.content.Context;
import android.content.SharedPreferences;

import static com.technology.yuyidoctorpad.User.User.LoginTP.DOC;
import static com.technology.yuyidoctorpad.User.User.LoginTP.HOS;

/**
 * Created by liuhaidong on 2017/11/1.
 */
public class User {
    public static LoginTP lTp;//0医生，1医院(当前登录的类型)
    public static String DocId="";//医生的id
    public static String HospitalId="1";//医院的id（默认值为：-1)
    public static String token = "";//服务器返回的token
    public static String tele = "";//用户名（手机号）
    public static boolean isLogin(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("USER", Context.MODE_APPEND);
        String userToken = preferences.getString("userToken", "0");
        String userTelePhone = preferences.getString("telePhone", "0");
        String tp=preferences.getString("type","-1");
        String docId=preferences.getString("DocId","-1");
        if (!"-1".equals(tp)&&!"0".equals(userToken)){//本地存放了登录的类别
            boolean flag=false;
            switch (tp){
                case "0":
                    lTp=DOC;//医生登录
                    if (!"0".equals(userTelePhone)&&!"0".equals(userToken)&!"-1".equals(docId)){
                        token=userToken;
                        tele=userTelePhone;
                        DocId=docId;
                        flag=true;
                    }
                    break;
                case "1"://医院登录没有保存手机号，所以只要判断token是否正确即可
                    lTp=HOS;//医院登录
                    token=userToken;
                    tele="1";
                    flag=true;
                    String hosId=preferences.getString("HospitalId","-1");
                    if ("-1".equals(hosId)){
                        flag=false;
                    }
                    HospitalId=hosId;
                    break;
            }
            return flag;
        }
        return false;
    }
    //保存医院id
    public static void saveHospitalId(Context con,String hosId){
        SharedPreferences pre = con.getSharedPreferences("USER",Context.MODE_APPEND);
        SharedPreferences.Editor edi = pre.edit();
        edi.putString("HospitalId",hosId);
        User.HospitalId=hosId;
        edi.commit();
    }
    //type：
    public static void saveLogin(Context con,String UsToken, String UsTele,String docId,LoginTP tp){
        SharedPreferences pre = con.getSharedPreferences("USER",Context.MODE_APPEND);
        SharedPreferences.Editor edi = pre.edit();
        switch (tp){
            case DOC:
                edi.putString("type","0");
                edi.putString("DocId",docId);
                break;
            case HOS:
                edi.putString("type","1");
                UsTele="1";//医院的时候不存放电话号，电话好默认为1
                break;
        }
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
        edi.remove("HospitalId");
        edi.remove("type");
        edi.remove("DocId");
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

    public enum LoginTP{
        DOC,HOS;//对应"0"，"1"；
    }
}
