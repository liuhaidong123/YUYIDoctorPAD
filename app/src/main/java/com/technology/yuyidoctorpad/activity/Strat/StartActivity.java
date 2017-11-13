package com.technology.yuyidoctorpad.activity.Strat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.activity.Login.LoginActivity;
import com.technology.yuyidoctorpad.activity.MainActivity;
import com.technology.yuyidoctorpad.lzhUtils.toast;
import com.technology.yuyidoctorpad.lzhViews.MyActivity;

public class StartActivity extends MyActivity {
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                if (User.isLogin(StartActivity.this)){
                    if (User.lTp!=null){
                        switch (User.lTp){
                            case DOC:
                                startActivity(new Intent(StartActivity.this, MainActivity.class));
                                finish();
                                break;
                            case HOS:
                                toast.toast(StartActivity.this,"界面未搭建");
                                break;
                        }
                    }
                    else {
                        startActivity(new Intent(StartActivity.this, LoginActivity.class));
                        finish();
                    }
                }
                else {
                    startActivity(new Intent(StartActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setChilidView(R.layout.activity_start);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
