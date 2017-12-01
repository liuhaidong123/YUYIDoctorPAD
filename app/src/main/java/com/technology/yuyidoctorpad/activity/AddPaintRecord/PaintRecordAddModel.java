package com.technology.yuyidoctorpad.activity.AddPaintRecord;

import android.os.Message;
import android.util.Log;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.Net.OkUtils;
import com.technology.yuyidoctorpad.Net.gson;
import com.technology.yuyidoctorpad.lhdUtils.MyDialog;
import com.technology.yuyidoctorpad.lzhUtils.BeanCode;
import com.technology.yuyidoctorpad.lzhUtils.Empty;
import com.technology.yuyidoctorpad.lzhUtils.Model;
import com.technology.yuyidoctorpad.lzhUtils.MyApplication;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/11/21.
 */

public class PaintRecordAddModel extends Model{
    IpaintAdd listener;
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case 0:
                MyDialog.stopDia();
                listener.onAddError((String) msg.obj);
                break;
            case 1:
                MyDialog.stopDia();
                listener.onAddSuccess();
                break;
        }
    }

    //    telethone      String      病人电话
//    name        String      病人姓名
//    age        String      病人年龄
//    gender        String      病人性别（0女1男）
//    medicalrecord      String      病人病历
//    physicianId      String      医生ID
//    返回值：   返回值名称                返回值类型    备注
//    code              0成功-1失败
    public void addRecord(String telethone, String name, String age, String  gender, String medicalrecord, String physicianId, List<File> list, IpaintAdd listener){
        MyDialog.showDialog(MyApplication.activityCurrent);
        this.listener=listener;
        Map<String,String>mp=new HashMap<>();
        mp.put("telethone",telethone);
        mp.put("name",name);
        mp.put("age",age);
        mp.put("gender",gender);
        mp.put("medicalrecord",medicalrecord);
        mp.put("physicianId",physicianId);
        OkUtils.getOkhttpFileCall(mp,list,Ip.path_F+Ip.interface_addRecord).enqueue(new com.squareup.okhttp.Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sendErrorMsg("网络异常！",0);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String res=response.body().string();
                Log.i("添加病历",res);
                try{
                    BeanCode b= gson.gson.fromJson(res,BeanCode.class);
                    if (b!=null){
                        if ("0".equals(b.getCode())){
                            sendSuccessMsg("添加成功！",1);
                        }
                        else {
                            sendErrorMsg(Empty.notEmpty(b.getMessage())?b.getMessage():"添加失败！",0);
                        }
                    }
                    else {
                        sendErrorMsg("添加失败！",0);
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
