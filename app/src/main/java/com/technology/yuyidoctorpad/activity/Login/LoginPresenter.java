package com.technology.yuyidoctorpad.activity.Login;

import android.os.Handler;
import android.os.Message;

import com.technology.yuyidoctorpad.lzhUtils.Empty;
import com.technology.yuyidoctorpad.lzhUtils.PhoneUtils;

/**
 * Created by wanyu on 2017/11/13.
 */

public class LoginPresenter {
    LoginModel model;
    int time=60;
    ILogin iLogin;
    Runnable runnable;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what>0){
                iLogin.getTimeOut(msg.what);
            }
            else if (msg.what==0){
                iLogin.getTimeOut(msg.what);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            handler.sendEmptyMessage(-2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            else if(msg.what==-2){
                iLogin.getTimeOut(msg.what);
            }
        }
    };
    public void onLogin(String name,String psd,ILogin listener,LoginType tp){
        if (Empty.notEmpty(name)&& PhoneUtils.isPhone(name)){
          if (Empty.notEmpty(psd)&&psd.length()==6){//验证码6位
                if (model==null){
                    model=new LoginModel();
                }
              model.login(name,psd,listener,tp);
          }
            else {
              listener.onError("验证码不正确！");
          }
        }
        else {
            listener.onError("手机号不正确");
        }
    }


    public void onGetSmsCode(String phoneNum,ILogin listener,LoginType tp){
        if (Empty.notEmpty(phoneNum)&& PhoneUtils.isPhone(phoneNum)){
            if (model==null){
                model=new LoginModel();
            }
            model.getSmS(phoneNum,listener,tp);
        }
        else {
            listener.getSMSCodeError("手机号不正确！");
        }
    }



    //验证码成功后记时
    public void onTimer(ILogin iLogin){
        this.iLogin=iLogin;
        time=60;
        runnable=new Runnable() {
            @Override
            public void run() {
                while (time>0) {
                    try {
                        time--;
                        handler.sendEmptyMessage(time);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(runnable).start();
    }

    public void stopTimer(){
        time=-1;
        handler.sendEmptyMessage(time);
        handler.removeCallbacks(runnable);
    }
}
