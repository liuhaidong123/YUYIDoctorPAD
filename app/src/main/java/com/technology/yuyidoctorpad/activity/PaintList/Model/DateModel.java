package com.technology.yuyidoctorpad.activity.PaintList.Model;

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
import com.technology.yuyidoctorpad.activity.PaintList.Bean.BeanTempPress;
import com.technology.yuyidoctorpad.lzhUtils.Empty;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanyu on 2017/11/3.
 */
//病历列表model
public class DateModel {
    IDate iDate;
    String resStr;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    iDate.onError("网络异常！");
                    break;
                case 1:
                    try{
                        BeanTempPress bean= gson.gson.fromJson(resStr,BeanTempPress.class);
                        if (bean!=null){
                            if ("0".equals(bean.getCode())){
                                iDate.onSuccess(bean);
                            }
                            else {
                                iDate.onError(Empty.notEmpty(bean.getMessage())?bean.getMessage():"没有查询到数据！");
                            }
                        }
                        else {
                            iDate.onError("没有查询到数据！");
                        }
                    }
                    catch (Exception e){
                        iDate.onError("数据异常！");
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    public void getDate(String userId,IDate iDate){
        this.iDate=iDate;
        Map<String,String> mp=new HashMap<>();
        mp.put("token", User.token);
        mp.put("humeuserId",userId);
        OkUtils.getCall(Ip.path+Ip.IPaintDate,mp,OkUtils.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                    handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("体温／血压：",resStr);
                handler.sendEmptyMessage(1);
            }
        });
       ;//token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&humeuserId=1患者详情中的患者数据
    }
}
