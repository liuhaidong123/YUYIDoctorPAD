package com.technology.yuyidoctorpad.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.Net.OkUtils;
import com.technology.yuyidoctorpad.Net.gson;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.RongCloudUtils.RongConnection;
import com.technology.yuyidoctorpad.RongCloudUtils.RongUserInfo;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.activity.MainActivity;
import com.technology.yuyidoctorpad.bean.BeanPriRong;
import com.technology.yuyidoctorpad.lhdUtils.ToastUtils;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * 患者咨询聊天页面
 */
public class ChatFragment extends Fragment {
    private String resStr;
    private Handler han = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    try {
                        BeanPriRong rong = gson.gson.fromJson(resStr, BeanPriRong.class);
                        if (rong != null) {
                            if (rong.getCode() == 0) {
                                if (rong.isPermissionInfo() == true) {
                                    User.hasvRongPri = true;
                                    String name = "医生";
                                    String uri = "http://img5.imgtn.bdimg.com/it/u=1482475142,4125104797&fm=23&gp=0.jpg";
                                    if (rong.getTrueName() != null && !"".equals(rong.getTrueName())) {
                                        name = rong.getTrueName();
                                    }
                                    if (!"".equals(rong.getAvatar()) && !TextUtils.isEmpty(rong.getAvatar())) {
                                        uri = rong.getAvatar();
                                    }
                                    Log.e("name====", name);
//                                    io.rong.imlib.model.UserInfo info=new io.rong.imlib.model.UserInfo(rong.getId()+"",name, Uri.parse(uri));
                                    RongUserInfo.RongToken = rong.getToken();
                                    RongConnection.connRong(getActivity(), RongUserInfo.RongToken);
                                } else {
                                    Log.e("当前医生无法接收到咨询xinxi ", "mainActivity:医院未授予当前医生接收视频到权限");
                                }
                            } else if (rong.getCode() == -1) {
                                Log.e("当前用户信息无法查询到", "mainActivity:当前用户没有在任何医院注册，请通知去注册");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ConversationListFragment fragment = (ConversationListFragment) getChildFragmentManager().findFragmentById(R.id.conversationlist);
        Uri uri = Uri.parse("rong://" + getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")
                .build();
        fragment.setUri(uri);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.myToast(getActivity(), "正在连接服务器，请稍等。。。");
                CheckPri();
            }
        });
        return view;
    }
    //检查当前用户是否有权限接受视频，语音，聊天
    private void CheckPri() {
        Map<String, String> mp = new HashMap<>();
        mp.put("telephone", User.tele);
        OkUtils.getCall(Ip.path + Ip.interface_CheckPri, mp, OkUtils.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                han.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr = response.body().string();
                Log.i("聊天权限检查---", resStr);
                han.sendEmptyMessage(1);
            }
        });
    }
}
