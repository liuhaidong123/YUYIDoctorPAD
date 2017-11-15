package com.technology.yuyidoctorpad.activity.PaintData.Model;

import android.os.Message;
import android.util.Log;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.Net.OkUtils;
import com.technology.yuyidoctorpad.Net.gson;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.activity.PaintData.Bean.Bean_MyPaintList;
import com.technology.yuyidoctorpad.activity.PaintData.IView.IPaintList;
import com.technology.yuyidoctorpad.lzhUtils.Model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanyu on 2017/11/14.
 */

public class PaintDataListModel extends Model{
    IPaintList listener;
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case 0:
                listener.onGetPaintListError((String) msg.obj);
                break;
            case 1:
                listener.onGetPaintListSuccess((Bean_MyPaintList) msg.obj);
                break;
        }
    }

    public void getPaintList(int st, int limit, final IPaintList listener){
        this.listener=listener;
        Map<String, String> mp = new HashMap<>();
        mp.put("token", User.token);
        mp.put("start", st + "");
        mp.put("limit", limit + "");
        OkUtils.getCall(Ip.path + Ip.interface_MyPaintList, mp, OkUtils.OK_GET).enqueue(new com.squareup.okhttp.Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sendErrorMsg("网络异常！",0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String str=response.body().string();
                Log.i("获取患者数据列表",str);
                try{
                    Bean_MyPaintList bean= gson.gson.fromJson(str,Bean_MyPaintList.class);
                    if (bean!=null){
                        sendSuccessMsg(bean,1);
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
    //患者搜索
    public void getPaintListContent(int st, int limit, String content,final IPaintList listener){
        this.listener=listener;
        Map<String, String> mp = new HashMap<>();
        mp.put("trueName",content);
        mp.put("token", User.token);
        mp.put("start",st+"");
        mp.put("limit",limit+"");
        OkUtils.getCall(Ip.path + Ip.interface_MyPaintList, mp, OkUtils.OK_GET).enqueue(new com.squareup.okhttp.Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sendErrorMsg("网络异常！",0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String str=response.body().string();
                Log.i("患者搜索",str);
                try{
                    Bean_MyPaintList bean= gson.gson.fromJson(str,Bean_MyPaintList.class);
                    if (bean!=null){
                        sendSuccessMsg(bean,1);
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
