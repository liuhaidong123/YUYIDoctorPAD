package com.technology.yuyidoctorpad.fragment.myFragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyidoctorpad.DbUtils.IDbUtlis;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.Net.OkUtils;
import com.technology.yuyidoctorpad.Net.gson;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.lzhUtils.MyApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanyu on 2017/11/7.
 */

public class myModel {
    IListener listener;
    String resStr1,resStr2;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case -1:
                    resStr1=IDbUtlis.getInstance().getOkhttpString(MyApplication.activityCurrent,Ip.interface_UserInfo);
                    UserBean infos= gson.gson.fromJson(resStr1,UserBean.class);
                    listener.onError("网络异常！",infos);
                    break;
                case 1:
                    try{
                       UserBean info= gson.gson.fromJson(resStr1,UserBean.class);
                        if (info!=null&&"0".equals(info.getCode())){
                            IDbUtlis.getInstance().saveOkhttpString(MyApplication.activityCurrent,Ip.interface_UserInfo,resStr1);
                            listener.onSuccess(info);
                        }
                        else {
                            listener.onError("没有查询到数据！",null);
                        }
                    }
                    catch (Exception e){
                        listener.onError("数据异常！",null);
                        e.printStackTrace();
                    }
                    break;

                case -2://有无未读消息（不处理网络异常状态）

                    break;
                case 2:
                    try{
                        Msg ms=gson.gson.fromJson(resStr2,Msg.class);
                        if (ms!=null){
                            if ("0".equals(ms.getCode())){
                                listener.HaveUnReadMsg(ms.hasMessage);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    //获取用户信息
    public void getUserInfo(IListener iListener){
        this.listener=iListener;
        Map<String,String> mp=new HashMap<>();
        mp.put("token", User.token);
        OkUtils.getCall(Ip.path+Ip.interface_UserInfo,mp,OkUtils.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(-1);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr1=response.body().string();
                Log.i("获取个人信息---",resStr1);
                handler.sendEmptyMessage(1);
            }
        });
    }

    //获取有无未读消息
    public void getUnReadMsg(IListener iListener){
        this.listener=iListener;
        Map<String,String> mp=new HashMap<>();
        mp.put("token", User.token);
        OkUtils.getCall(Ip.path+Ip.interface_HasUnReadMsg,mp,OkUtils.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(-2);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr2=response.body().string();
                Log.i("有无未读消息---",resStr2);
                handler.sendEmptyMessage(2);
            }
        });
    }

   public class Msg{

        /**
         * result : null
         * code : 0
         * hasMessage : false
         */

        private Object result;
        private String code;
        private boolean hasMessage;

        public Object getResult() {
            return result;
        }

        public void setResult(Object result) {
            this.result = result;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public boolean isHasMessage() {
            return hasMessage;
        }

        public void setHasMessage(boolean hasMessage) {
            this.hasMessage = hasMessage;
        }
    }
}
