package com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Registe;

import android.os.Message;
import android.util.Log;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.Net.OkUtils;
import com.technology.yuyidoctorpad.Net.gson;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Depart.Bean.DepartmentBean;
import com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Depart.IDepart;
import com.technology.yuyidoctorpad.lzhUtils.BeanCode;
import com.technology.yuyidoctorpad.lzhUtils.Empty;
import com.technology.yuyidoctorpad.lzhUtils.Model;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/11/18.
 */

public class RegisterModel extends Model{
    IDepart listener;
    IRegister iRegister;
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case 0://获取科室信息
                listener.onGetDrpartError((String) msg.obj);
                break;
            case 1://获取科室信息
                listener.onGetDrpartSuccess((List<DepartmentBean.ResultBean>) msg.obj);
                break;

            case -2://获取科室中的医生信息
                iRegister.onGetDocError((String) msg.obj);
                break;
            case 2://获取科室中的医生信息
                iRegister.onGetDocSuccess((DoctorListBean) msg.obj);
                break;

            case -3://挂号设置失败
                iRegister.onRegisterError((String) msg.obj);
                break;
            case 3:
                iRegister.onRegisterSuccess();
                break;
        }
    }

    //    http://192.168.1.44:8080/yuyi/department/getdepartment.do?hospitalId=2
    public void getDepartmentData( IDepart listener){
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

//    http://localhost:8080/yuyi/datenumber/findnumber.do?hospitalId=医院ID&departmentId=科室ID&date=查询日期
    public void getDoctorList(String departId,IRegister register,int start,int limit ,String data){
        this.iRegister=register;
        Map<String,String>mp=new HashMap<>();
        mp.put("hospitalId",User.HospitalId);
        mp.put("departmentId",departId);
        mp.put("date",data);
//        mp.put("start",start+"");
//        mp.put("limit",limit+"");
        OkUtils.getCall(Ip.path_F+Ip.interface_getDepartRecord,mp, OkUtils.OK_POST).enqueue(new com.squareup.okhttp.Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sendErrorMsg("网络异常！",-2);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String resStr=response.body().string();
                Log.i("获取科室医生信息",resStr);
                try{
                    DoctorListBean bean=gson.gson.fromJson(resStr,DoctorListBean.class);
                    if (bean!=null&&"0".equals(bean.getCode())&&bean.getResult()!=null&&bean.getResult().size()>0){
                        sendSuccessMsg(bean,2);
                    }
                    else {
                        sendErrorMsg("没有查询到数据！",-2);
                    }
                }
                catch (Exception e){
                    sendErrorMsg("数据异常！",-2);
                    e.printStackTrace();
                }
            }
        });
    }
    //提交挂号信息管理员：添加挂号数量
//    http://localhost:8080/yuyi/datenumber/saveNumber.do?DatenumberJson=Json字符串
    public void submit(ListDoctorResult bean,  IRegister lis){
        this.iRegister=lis;
        Map<String,String>map=new HashMap<>();
        map.put("DatenumberJson",gson.gson.toJson(bean));
        OkUtils.getCall(Ip.path_F+Ip.interface_getDepart,map,OkUtils.OK_POST).enqueue(new com.squareup.okhttp.Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.i("挂号设置","shibai");
                sendErrorMsg("网络异常！",-3);
                e.printStackTrace();
            }
            @Override
            public void onResponse(Response response) throws IOException {
                String resSt=response.body().string();
                Log.i("挂号设置",resSt);
                try{
                    BeanCode b=gson.gson.fromJson(resSt,BeanCode.class);
                    if (b!=null){
                        if ("0".equals(b.getCode())){
                            sendSuccessMsg("挂号成功!",3);
                        }
                        else {
                            sendErrorMsg(Empty.notEmpty(b.getMessage())?b.getMessage():"设置挂号失败！",-3);
                        }
                    }
                    else {
                        sendErrorMsg("数据异常！",-3);
                    }
                }
                catch (Exception e){
                    sendErrorMsg("数据异常！",-3);
                    e.printStackTrace();
                }
            }
        });
    }
}
