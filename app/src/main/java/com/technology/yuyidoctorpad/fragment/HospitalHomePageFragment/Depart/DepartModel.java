package com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Depart;

import android.os.Message;
import android.util.Log;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.Net.OkUtils;
import com.technology.yuyidoctorpad.Net.gson;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Depart.Bean.DepartmentBean;
import com.technology.yuyidoctorpad.lzhUtils.Empty;
import com.technology.yuyidoctorpad.lzhUtils.Model;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/11/15.
 */

public class DepartModel extends Model{
    IDepart listener;

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case 0:
                listener.onGetDrpartError((String) msg.obj);
                break;
            case 1:
                listener.onGetDrpartSuccess((List<DepartmentBean.ResultBean>) msg.obj);
                break;
        }
    }

    //    http://192.168.1.44:8080/yuyi/department/getdepartment.do?hospitalId=2
    public void getDepartmentData(final IDepart listener){
        this.listener=listener;
        Map<String,String> mp=new HashMap<>();
        mp.put("hospitalId", User.HospitalId);
        OkUtils.getCall(Ip.path_F+Ip.interface_getDepartment,mp,OkUtils.OK_POST).enqueue(new com.squareup.okhttp.Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sendErrorMsg("网络异常！",0);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String str=response.body().string();
                Log.i("获取科室列表",str);
                try{
                    DepartmentBean bean= gson.gson.fromJson(str,DepartmentBean.class);
                    if (bean!=null){
                        if ("0".equals(bean.getCode())){
                            sendSuccessMsg(bean.getResult(),1);
                        }
                        else {
                            sendErrorMsg(Empty.notEmpty(bean.getMessage())?bean.getMessage():"获取科室信息失败！",0);
                        }
                    }
                    else {
                        sendErrorMsg("没有查询到数据！",0);
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
