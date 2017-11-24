package com.technology.yuyidoctorpad.RongCloudUtils;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.Net.OkUtils;
import com.technology.yuyidoctorpad.User.User;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

/**
 * Created by wanyu on 2017/11/2.
 */

public class RongProvider implements RongIM.UserInfoProvider{
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Log.e("刷新容云","用户信息刷新失败！");
                    break;
                case 1:
                    UserInfo info= (UserInfo) msg.obj;
                    RongIM.getInstance().refreshUserInfoCache(info);
                    break;
            }
        }
    };
   static RongProvider provider;
    private RongProvider(){}
    public static RongProvider getInstance(){
        if (provider==null){
            provider=new RongProvider();
        }
        return provider;
    }

//    http://a3.qpic.cn/psb?/V10dl1Mt1s0RoL/izaWTfqGo7IQIADpb6Q5RtyYlSUKH2gS1OcqQI.hOo0!/b/dPIAAAAAAAAA&bo=lACUAAAAAAADByI!&rf=viewer_4
    @Override
    public io.rong.imlib.model.UserInfo getUserInfo(String s) {
        getUser(s);
        return new io.rong.imlib.model.UserInfo(s,"患者", Uri.parse("http://a3.qpic.cn/psb?/V10dl1Mt1s0RoL/izaWTfqGo7IQIADpb6Q5RtyYlSUKH2gS1OcqQI.hOo0!/b/dPIAAAAAAAAA&bo=lACUAAAAAAADByI!&rf=viewer_4"));
    }
//    http://192.168.1.168:8082/yuyi/homeuser/findMyUserInfo.do?token=293AB53EF708A14175A44DE3378D8BFA&personalId=18301264693
//    token=医生的token
//    personalId=患者的id，手机号；
    private void getUser(final String userId){
        Map<String,String> mp=new HashMap<>();
        mp.put("token", User.token);
        mp.put("personalId", userId);
        OkUtils.getCall(Ip.path_F+Ip.interface_getRongInfo,mp,OkUtils.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("获取容云用户信息失败","网络异常");
            }
            @Override
            public void onResponse(Response response) throws IOException {
                String str=response.body().string();
                Log.e("获取容云信息",str);
//                {"gender":1,"telephone":17743516301,"avatar":"/static/image/20171019/2d245103dd7e4d478bd0b0521bb2da73.jpg","trueName":"刘文","id":252,"age":27}
                try {
                    JSONObject job=new JSONObject(str);
                    Message ms=new Message();
                    UserInfo info= new io.rong.imlib.model.UserInfo(userId,job.getString("trueName"), Uri.parse(Ip.imagePath+job.getString("avatar")));
                    ms.obj=info;
                    ms.what=1;
                    handler.sendMessage(ms);
                    }
                catch (Exception e){
                    handler.sendEmptyMessage(0);
                    e.printStackTrace();
                    Log.e("获取容云信息","数据异常");
                }
            }
        });
    }
}
