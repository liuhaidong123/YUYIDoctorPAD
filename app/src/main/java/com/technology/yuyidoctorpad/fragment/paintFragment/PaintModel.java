package com.technology.yuyidoctorpad.fragment.paintFragment;

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

import static android.R.string.ok;


/**
 * Created by wanyu on 2017/11/3.
 */

public class PaintModel {
    Ipaint ipaint;
    String resStr;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    ipaint.onError("网络异常！",Ip.IPaintList);
                    break;
                case 1:
                    try{
                        paintListBean bean= gson.gson.fromJson(resStr,paintListBean.class);
                            if (bean!=null&&bean.getRows()!=null&&bean.getRows().size()>0){
                                IDbUtlis.getInstance().saveOkhttpString(MyApplication.activityCurrent,Ip.IPaintList,resStr);
                                ipaint.onSuccess(bean);
                            }
                            else {
                                ipaint.onError("没有查询到数据！",Ip.IPaintList);
                            }
                        }
                    catch (Exception e){
                        ipaint.onError("数据异常！",Ip.IPaintList);
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
public void getPaintList(Ipaint ipaint,int start,int limit){
    this.ipaint=ipaint;
    Map<String,String> mp=new HashMap<>();
    mp.put("token", User.token);
    mp.put("start",start+"");
    mp.put("limit",limit+"");
    OkUtils.getCall(Ip.path+Ip.IPaintList,mp,OkUtils.OK_GET).enqueue(new Callback() {
        @Override
        public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
        }

        @Override
        public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("获取患者列表",resStr);
                handler.sendEmptyMessage(1);
        }
    });
}
}
