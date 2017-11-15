package com.technology.yuyidoctorpad.activity.Settings;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.Net.OkUtils;
import com.technology.yuyidoctorpad.Net.gson;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.activity.Settings.Bean.Bean_MySetting_AboutUs;
import com.technology.yuyidoctorpad.lzhViews.MyActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class AboutActivity extends MyActivity {
    @BindView(R.id.titleinclude_text)TextView title;

    private ImageView roundRectImageView;
    private final Context con=AboutActivity.this;
    private String resStr;
    private TextView my_settings_aboutOurs_name;//宇医1。0
    private TextView my_settings_aboutOurs_pro;//简介
    private TextView my_setting_about_version;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:

                    break;
                case 1:
                    try{
                        Bean_MySetting_AboutUs info= gson.gson.fromJson(resStr,Bean_MySetting_AboutUs.class);
                        if ("0".equals(info.getCode())){
                            Bean_MySetting_AboutUs.ResultBean result= info.getResult();
                            my_settings_aboutOurs_name.setText(result.getTitle());
                            my_settings_aboutOurs_pro.setText(result.getContent());
                            my_setting_about_version.setText(result.getVersion());
                            Picasso.with(con).load(Ip.imagePath+result.getPicture()).error(R.mipmap.ic_launcher).into(roundRectImageView);
                        }
                        else {
                            Log.e("获取关于我们失败---","--------");
                        }
                    }
                    catch (Exception e){
                        Log.e("获取关于我们失败---","--------");
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setChilidView(R.layout.activity_about);
        title.setText("关于我们");
        initView();
        getData();
    }

    private void initView() {
        roundRectImageView= (ImageView) findViewById(R.id.my_settings_aboutOurs_imageview);
        my_settings_aboutOurs_name= (TextView) findViewById(R.id.my_settings_aboutOurs_name);
        my_settings_aboutOurs_pro= (TextView) findViewById(R.id.my_settings_aboutOurs_pro);
        my_setting_about_version= (TextView) findViewById(R.id.my_setting_about_version);
    }
    //{"result":{"createTimeString":"","updateTimeString":"","title":"宇医1.0","version":"当前版本号：1.0（wanyu2007）","content":"宇医APP，希望通过网上医疗的形式能够解决用户的一些医疗的基本需求，包括：测量监控自己及家人的健康数据；足不出户解决购药问题；提前预约专家挂号问题；在家与医生面对面交流，解决一些简单的问诊等","picture":"/static/image/avatar.jpeg","id":1},"code":"0"}
    public void getData() {
        Map<String,String> mp=new HashMap<>();
        OkUtils.getCall(Ip.path+Ip.interface_AboutUs,mp,OkUtils.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("关于我们---",resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }
}
