package com.technology.yuyidoctorpad.activity.Login;

/**
 * Created by wanyu on 2017/11/13.
 */

public enum LoginType {
    DOCTOR,HOSPITAL;//医生登录，医院登录
    public static LoginType getOppositeType(LoginType tp){
        switch (tp){
            case DOCTOR:
                tp=HOSPITAL;
                break;
            case HOSPITAL:
                tp=DOCTOR;
                break;
        }
        return tp;
    }
    public static String getLoginTypeString(LoginType tp){
        String str="";
        switch (tp){
            case DOCTOR:
                str="医生登录";
                break;
            case HOSPITAL:
                str="医院登录";
                break;
        }
        return str;
    }
}
