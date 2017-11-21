package com.technology.yuyidoctorpad.activity.Hospital.Model;

import android.os.Message;
import android.util.Log;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.Net.OkUtils;
import com.technology.yuyidoctorpad.Net.gson;
import com.technology.yuyidoctorpad.activity.Hospital.Bean.DepartAddBean;
import com.technology.yuyidoctorpad.activity.Hospital.Listener.Iadd;
import com.technology.yuyidoctorpad.lzhUtils.Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/11/17.
 */

public class HospitalAddDepartModel extends Model{
    Iadd listener;
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
            case 2://部分成功
                List<String>lis= (List<String>) msg.obj;
                listener.onPartError(lis);
                break;
        }
    }
//    192.168.1.44:8080/yuyi/department/saveBatch.do?jsonStr=
    public void submit(String json, Iadd iadd){
            this.listener=iadd;
        Map<String,String>mp=new HashMap<>();
        mp.put("jsonStr",json);
        OkUtils.getCall(Ip.path_F+Ip.interface_addDepart,mp,OkUtils.OK_POST).enqueue(new com.squareup.okhttp.Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sendErrorMsg("网络异常！",0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String resStr=response.body().string();
                Log.i("添加科室门诊",resStr);
                try{
                    DepartAddBean bean= gson.gson.fromJson(resStr,DepartAddBean.class);
                    if ("0".equals(bean.getCode())){
                        if (bean.getResult()!=null&&bean.getResult().size()>0){
                            List<String> list=new ArrayList<String>();
                            for (int i=0;i<bean.getResult().size();i++){
                                list.add(bean.getResult().get(i).getDepartmentName());
                            }
                            sendSuccessMsg(list,2);
                        }
                        else {
                            sendSuccessMsg("添加成功",1);
                        }
                    }

                }
                catch (Exception e){
                    sendErrorMsg("数据异常！",0);
                    e.printStackTrace();
                }
            }
        });
    }
}
