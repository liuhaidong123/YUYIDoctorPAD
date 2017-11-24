package com.technology.yuyidoctorpad.activity.Strat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.TestActivity;
import com.technology.yuyidoctorpad.lzhViews.MyActivity;

public class StartActivity extends MyActivity {
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                startActivity(new Intent(StartActivity.this, TestActivity.class));
//                if (User.isLogin(StartActivity.this)){
//                    if (User.lTp!=null){
//                        switch (User.lTp){
//                            case DOC:
//                                startActivity(new Intent(StartActivity.this, MainActivity.class));
//                                finish();
//                                break;
//                            case HOS:
//                                startActivity(new Intent(getApplicationContext(),HospitalHomePageActivity.class));
//                                break;
//                        }
//                    }
//                    else {
//                        startActivity(new Intent(StartActivity.this, LoginActivity.class));
//                        finish();
//                    }
//                }
//                else {
//                    startActivity(new Intent(StartActivity.this, LoginActivity.class));
//                    finish();
//                }
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
