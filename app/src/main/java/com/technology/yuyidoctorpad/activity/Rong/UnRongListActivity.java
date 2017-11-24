package com.technology.yuyidoctorpad.activity.Rong;

import android.os.Bundle;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.lzhViews.MyActivity;
//没有聊天接收权限的activity
public class UnRongListActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setChilidView(R.layout.activity_un_rong_list);
    }
}
