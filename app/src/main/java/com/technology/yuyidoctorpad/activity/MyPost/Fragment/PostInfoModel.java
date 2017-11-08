package com.technology.yuyidoctorpad.activity.MyPost.Fragment;

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
import com.technology.yuyidoctorpad.activity.MyPost.Bean.BeanComment;
import com.technology.yuyidoctorpad.activity.MyPost.Bean.BeanPostInfo;
import com.technology.yuyidoctorpad.activity.MyPost.Bean.Bean_MyPostDataPriase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanyu on 2017/11/7.
 */

public class PostInfoModel {
    IPostInfo listener;
    String resStr;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    listener.onGetPostInfoError("网络异常！");
                    break;
                case 1:
                    try{
                        BeanPostInfo info= gson.gson.fromJson(resStr,BeanPostInfo.class);
                        if (info!=null){
                            if ("0".equals(info.getCode())){
                                listener.onGetPostInfoSuccess(info);
                            }
                            else {
                                listener.onGetPostInfoError("没有查询到数据！");
                            }
                        }
                        else {
                            listener.onGetPostInfoError("没有查询到数据！");
                        }
                    }
                    catch (Exception e){
                        listener.onGetPostInfoError("数据异常！");
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    listener.onPriseError("网络异常！");
                    break;
                case 3:
                    try{
                        Bean_MyPostDataPriase bPraise=gson.gson.fromJson(resStr,Bean_MyPostDataPriase.class);
                        if (bPraise!=null&&"0".equals(bPraise.getCode())){
                            listener.onPriseSuccess();
                        }
                        else {
                            listener.onPriseError("操作失败！");
                        }
                    }
                    catch (Exception e){
                        listener.onPriseError("数据异常！");
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    listener.onCommentError("网络异常！");
                    break;
                case 5:
                    try{
                        BeanComment be=gson.gson.fromJson(resStr,BeanComment.class);
                        if (be!=null&&"0".equals(be.getCode())){
                            listener.onCommentSuccess();
                        }
                        else {
                            listener.onCommentError("失败："+be.getResult());
                        }
                    }
                    catch (Exception e){
                        listener.onCommentError("数据异常！");
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    public void getPostInfo(int st,int limit,String ids,IPostInfo listener){
        this.listener=listener;
        Map<String ,String>mp=new HashMap<>();
        mp.put("token", User.token);
        mp.put("start",st+"");
        mp.put("limit",limit+"");
        mp.put("id",ids);
        OkUtils.getCall(Ip.path+Ip.interface_MyPostMsg,mp,OkUtils.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                    handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                    resStr=response.body().string();
                    Log.i("获取帖子详情",resStr);
                    handler.sendEmptyMessage(1);
            }
        });
    }

    //点赞
    public void makePraise(String ids,IPostInfo lis){
        this.listener=lis;
        Map<String ,String>mp=new HashMap<>();
        mp.put("token", User.token);
        mp.put("id",ids);
        OkUtils.getCall(Ip.path+Ip.interface_MyPostDataPraise,mp,OkUtils.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(2);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("帖子点赞／取消点赞",resStr);
                handler.sendEmptyMessage(3);
            }
        });
    }

    //评论
    public void makeComment(String ids,String content,IPostInfo listener){
        this.listener=listener;
        Map<String ,String>mp=new HashMap<>();
        mp.put("telephone", User.tele);
        mp.put("content_id",ids);
        mp.put("Content",content);
        OkUtils.getCall(Ip.path+Ip.interface_MyPostComment,mp,OkUtils.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(4);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                    resStr=response.body().string();
                    Log.i("帖子评论",resStr);
                    handler.sendEmptyMessage(5);
            }
        });
    }
}
