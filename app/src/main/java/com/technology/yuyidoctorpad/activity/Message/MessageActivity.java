package com.technology.yuyidoctorpad.activity.Message;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.Net.OkUtils;
import com.technology.yuyidoctorpad.Net.gson;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.lzhUtils.MyListView;
import com.technology.yuyidoctorpad.lzhViews.MyActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MessageActivity extends MyActivity implements MyListView.IonScrollBottomListener{
        @BindView(R.id.titleinclude_text)TextView titleinclude_text;

        @BindView(R.id.message_notifi)RelativeLayout message_notifi;
        @BindView(R.id.message_title)TextView message_title;//标题
        @BindView(R.id.message_time)TextView message_time;//时间
        @BindView(R.id.message_content)TextView message_content;//内容

        @BindView(R.id.messageListView)MyListView messageListView;

    private My_message_listViewAdapter adapter;
    private int unReadId = -1;
    private final int limit = 10;
    private int start = 0;
    private String resStr;
    private List<Bean_MyMessage.RowsBean> list;
    Bean_MyMessage.RowsBean rowsBean;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:

                    break;
                case 1:
                    try {
                        Bean_MyMessage myMessage = gson.gson.fromJson(resStr, Bean_MyMessage.class);
                        if (myMessage != null) {
                            myMessage.getRows();
                            if (myMessage.getRows() != null && myMessage.getRows().size() > 0) {
                                start += myMessage.getRows().size();
                                if (myMessage.getRows().size() != limit) {//分页数据不足，数据已经请求完毕
                                    messageListView.setLoadingComplete();
                                } else {//服务器还有数据
                                    messageListView.setScroll(true);
                                }
                                List<Bean_MyMessage.RowsBean> lis=new ArrayList<>();
                                for (int i=0;i<myMessage.getRows().size();i++){
                                    if (myMessage.getRows().get(i).getMsgType()==1){//公告
                                        if (rowsBean==null){
                                            rowsBean=myMessage.getRows().get(i);
                                            message_notifi.setVisibility(View.VISIBLE);
                                            message_title.setText(rowsBean.getTitle());
                                            message_content.setText(rowsBean.getContent());
//                                            2017-10-18 15:15:22
                                            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            try{
                                                Date d= format.parse(rowsBean.getCreateTimeString());
                                                SimpleDateFormat fom=new SimpleDateFormat("HH:mm");
                                                message_time.setText(fom.format(d));
                                            }
                                            catch (Exception e){
                                                message_time.setText("日期未获取");
                                                e.printStackTrace();
                                            }

                                        }
                                    }
                                    else {
                                        lis.add(myMessage.getRows().get(i));
                                    }
                                }
                                list.addAll(lis);
                                adapter.notifyDataSetChanged();

                            } else {
                                messageListView.setEmpey("没有查询到数据！");
                            }
                        } else {
                              messageListView.setEmpey("没有查询到数据！");
                        }
                    } catch (Exception e) {
                        messageListView.setEmpey("数据异常！");
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try {
                        Bean_MyMessageRead myMessageRead = gson.gson.fromJson(resStr, Bean_MyMessageRead.class);
                        if ("0".equals(myMessageRead.getCode())) {
                            Log.i("消息设置已读成功", "--messageid==" + unReadId);
                            list.get(unReadId).setIsRead(true);
                            adapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setChilidView(R.layout.activity_message);
        titleinclude_text.setText("消息");
        list = new ArrayList<>();
        adapter = new My_message_listViewAdapter(this, list);
        messageListView.setAdapter(adapter);
        getMessage(start,limit);
        messageListView.setOnScrollBottomListener(this);
        messageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (list.get(i).isIsRead() == false) {
                    setUnRead(i);
                }
                int msgType = list.get(i).getMsgType();
                switch (msgType) {  //消息类型--1=宇医公告，2=挂号通知，3=点赞资讯，4=点赞帖子，5=点赞帖子评论
                    case 1://公告页面
//                        startActivity(new Intent(con, My_message_notification_Activity.class));
                        break;
                    case 2:
                        //不做处里
                        break;
                    case 3:
                        //咨询详情页面
                        break;
                    case 4:
                        //帖子详情页面
                        break;
                    case 5:
                        //帖子评论页面
                        break;
                }
            }
        });
    }
    @OnClick(R.id.message_notifi)
    public void click(View vi){

    }


    //获取消息列表http://192.168.1.55:8080/yuyi/messagePhysician/findPage.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&start=0&limit=5
    public void getMessage(int st, int lim) {
        Map<String, String> mp = new HashMap<>();
        mp.put("token", User.token);
        mp.put("start", st + "");
        mp.put("limit", lim + "");
        OkUtils.getCall(Ip.path + Ip.interface_MyMessageList, mp, OkUtils.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr = response.body().string();
                Log.i("获取消息列表---", resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }


    //http://192.168.1.55:8080/yuyi/messagePhysicianLog/save.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&messageId=1
    public void setUnRead(int position) {//设置已读消息
        unReadId = position;
        Map<String, String> mp = new HashMap<>();
        mp.put("token", User.token);
        mp.put("messageId", list.get(position).getId() + "");
        OkUtils.getCall(Ip.path + Ip.interface_MyMessageRead, mp, OkUtils.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr = response.body().string();
                Log.i("设置已读消息---", resStr);
                handler.sendEmptyMessage(2);
            }
        });
    }

    @Override
    public void onScrollBottom() {
        getMessage(start,limit);
    }
}
