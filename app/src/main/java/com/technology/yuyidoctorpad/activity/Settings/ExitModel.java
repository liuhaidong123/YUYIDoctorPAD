package com.technology.yuyidoctorpad.activity.Settings;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.Net.OkUtils;
import com.technology.yuyidoctorpad.Net.gson;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.lzhUtils.BeanCode;
import com.technology.yuyidoctorpad.lzhUtils.Empty;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanyu on 2017/11/21.
 */

public class ExitModel {
    ExitListener listener;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    listener.onError((String) msg.obj);
                    break;
                case 1:
                    listener.onSuccess();
                    break;
            }
        }
    };
//    http://192.168.1.44:8080/yuyi/physician/logout.do?token=066485961925E58A07FA47E4DFBE3942
    public void exit(ExitListener listener){
        this.listener=listener;
        Map<String,String>mp=new HashMap<>();
        mp.put("token", User.token);
        OkUtils.getCall(Ip.path+"/physician/logout.do?",mp,OkUtils.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Message msg=Message.obtain();
                msg.what=0;
                msg.obj="网络异常！";
                handler.sendMessage(msg);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String res=response.body().string();
                Log.i("退出登录",res);
                Message msg=Message.obtain();
                try{
                    BeanCode b= gson.gson.fromJson(res,BeanCode.class);
                    if (b!=null){
                        if ("0".equals(b.getCode())){
                            handler.sendEmptyMessage(1);
                        }
                        else {
                            msg.what=0;
                            msg.obj= Empty.notEmpty(b.getMessage())?b.getMessage():"退出失败！";
                            handler.sendMessage(msg);
                        }
                    }
                    else {
                        msg.what=0;
                        msg.obj="退出失败！";
                        handler.sendMessage(msg);
                    }
                }
                catch (Exception e){
                    msg.what=0;
                    msg.obj="数据异常！";
                    handler.sendMessage(msg);
                    e.printStackTrace();
                }
            }
        });
    }
    public interface ExitListener{
        void onSuccess();
        void onError(String obj);
    }
}
