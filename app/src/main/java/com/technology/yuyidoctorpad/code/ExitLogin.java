package com.technology.yuyidoctorpad.code;

import android.content.Context;

import com.technology.yuyidoctorpad.lzhUtils.toast;


/**
 * Created by wanyu on 2017/9/27.
 */
//token失效重新登录
public class ExitLogin {
    static  ExitLogin login;
    private ExitLogin(){

    }
    public static ExitLogin getInstance(){
        if (login==null){
            login=new ExitLogin();
        }
        return login;
    }
    //重新登录
    public void showLogin(Context con){
        toast.toast(con,"登录状态失效，请退出后重新登录");
    }
}
