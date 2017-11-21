package com.technology.yuyidoctorpad.activity.Hospital.Model;

import android.os.Message;
import android.util.Log;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.Net.OkUtils;
import com.technology.yuyidoctorpad.Net.gson;
import com.technology.yuyidoctorpad.activity.Hospital.Listener.Iedit;
import com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Depart.Bean.DepartDeleteBean;
import com.technology.yuyidoctorpad.lzhUtils.Empty;
import com.technology.yuyidoctorpad.lzhUtils.Model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanyu on 2017/11/17.
 */

public class HospitalEditModel extends Model{
    Iedit listener;
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
//    http://192.168.1.44:8080/yuyi/department/ClinicUpdateAndDelete.do?DeleteClinicId=22;23;24;&AddClinicName=%E6%97%A0%E7%A7%91%E5%AE%A4;&DepartmentId=5&departmentName=%E4%B8%AD%E5%8C%BB%E7%A7%91
//    参数：       参数名        参数类型    备注
//    DeleteClinicId      String      需要删除的门诊ID分号隔开
//    AddClinicName      String      需要添加的门诊名称分号隔开
//    DepartmentId      Long      科室ID
//    departmentName      String      科室名称（有修改就传，没有修改就不传）
//    返回值：   返回值名称                返回值类型    备注
//    code              0成功-1失败
    public void submit(String DeleteClinicId,String AddClinicName,String DepartmentId,String departmentName, Iedit listener){
        this.listener=listener;
        Map<String,String>mp=new HashMap<>();
        mp.put("DeleteClinicId",DeleteClinicId);
        mp.put("AddClinicName",AddClinicName);
        mp.put("DepartmentId",DepartmentId);
        mp.put("departmentName",departmentName);
        OkUtils.getCall(Ip.path_F+Ip.interface_eidtDepart,mp,OkUtils.OK_POST).enqueue(new com.squareup.okhttp.Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sendErrorMsg("网络异常！",0);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String resStr=response.body().string();
                Log.i("科室修改",resStr);
                try{
                    DepartDeleteBean bean= gson.gson.fromJson(resStr,DepartDeleteBean.class);
                    if (bean!=null&&"0".equals(bean.getCode())){
                        sendSuccessMsg("修改成功！",1);
                    }
                    else {
                        sendErrorMsg(Empty.notEmpty(bean.getMessage())?bean.getMessage():"修改失败！",0);
                    }
                }
                catch (Exception e){
                    sendErrorMsg("网络异常！",0);
                    e.printStackTrace();
                }
            }
        });
    }
}
