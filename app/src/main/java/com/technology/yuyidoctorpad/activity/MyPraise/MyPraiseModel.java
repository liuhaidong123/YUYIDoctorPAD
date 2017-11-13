package com.technology.yuyidoctorpad.activity.MyPraise;

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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanyu on 2017/11/13.
 */

public class MyPraiseModel {
    IPraise listener;
    String resStr;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    listener.onError("网络异常！");
                    break;
                case 1:
                    try{
                        Bean_MyPraise bean= gson.gson.fromJson(resStr,Bean_MyPraise.class);
                        if (bean!=null&&bean.getRows()!=null){
                            listener.onSuccess(bean);
                        }
                        else {
                            listener.onError("没有查询到数据！");
                        }
                    }
                    catch (Exception e){
                        listener.onError("数据异常！");
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    public void getPraiseData(int star,int limit,IPraise lis) {
        this.listener=lis;
        Map<String,String> m=new HashMap<>();
        m.put("token", User.token);
        m.put("start",star+"");
        m.put("limit",limit+"");
        OkUtils.getCall(Ip.path+Ip.interface_MyPraise,m,OkUtils.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("获取我的点赞---",resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }
}
