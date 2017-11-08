package com.technology.yuyidoctorpad.activity.MyPost;

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
import com.technology.yuyidoctorpad.activity.MyPost.Bean.Bean_MyPostData;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanyu on 2017/11/7.
 */

public class MyPostModel {
    IListener listener;
    String resStr;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    listener.onGetPostListError("网络异常！");
                    break;
                case 1:
                    try{
                        Bean_MyPostData bean= gson.gson.fromJson(resStr,Bean_MyPostData.class);
                        if (bean!=null){
                            listener.onGetPostListSuccess(bean);
                        }
                        else {
                            listener.onGetPostListError("没有查询到数据！");
                        }
                    }
                    catch (Exception e){
                        listener.onGetPostListError("数据异常！");
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    //获取我的帖子
    public void getMyPost(int st,int limit,IListener listener){
        this.listener=listener;
        Map<String, String> mp = new HashMap<>();
        mp.put("token", User.token);
        mp.put("start", st + "");
        mp.put("limit", "" + limit);
        OkUtils.getCall(Ip.path + Ip.interface_MyPostData, mp, OkUtils.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr = response.body().string();
                Log.i("我的帖子---", resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }
}
