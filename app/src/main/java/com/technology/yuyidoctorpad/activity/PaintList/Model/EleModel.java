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
import com.technology.yuyidoctorpad.activity.PaintList.Bean.bean_MedicalRecordList;
import com.technology.yuyidoctorpad.lzhUtils.Empty;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/11/3.
 */

public class EleModel {
    IEle iEle;
    String resStr;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    iEle.onError("网络异常！");
                    break;
                case 1:
                    try{
                        bean_MedicalRecordList bean= gson.gson.fromJson(resStr,bean_MedicalRecordList.class);
                        if (bean!=null){
                            if ("0".equals(bean.getCode())){
                                List<bean_MedicalRecordList.ResultBean> li=bean.getResult();
                                if (li!=null&&li.size()>0){
                                    iEle.onSuccess(li);
                                }
                                else {
                                    iEle.onError("没有查询到数据！");
                                }
                            }
                            else {
                                iEle.onError(Empty.notEmpty(bean.getMessage())?bean.getMessage():"没有查询到数据！");
                            }
                        }
                        else {
                            iEle.onError("没有查询到数据！");
                        }
                    }
                    catch (Exception e){
                        iEle.onError("数据异常！");
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    public void getEleDate(IEle iEle,String id){
        this.iEle=iEle;
        Map<String,String> m=new HashMap<>();
        m.put("id",id);
        OkUtils.getCall(Ip.path+Ip.interface_PaintEleList,m,OkUtils.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("获取患者病例列表",resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }
}
