package com.technology.yuyidoctorpad.activity.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.activity.Login.LoginActivity;
import com.technology.yuyidoctorpad.lzhUtils.MyApplication;
import com.technology.yuyidoctorpad.lzhUtils.toast;
import com.technology.yuyidoctorpad.lzhViews.MyActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends MyActivity implements ExitModel.ExitListener {
    ExitModel model;
    @BindView(R.id.titleinclude_text)TextView title;
    boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setChilidView(R.layout.activity_setting);
        title.setText("设置");
        model=new ExitModel();
    }
    @OnClick({R.id.setting_contact,R.id.setting_about,R.id.setting_exit})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.setting_contact://意见反馈
                startActivity(new Intent(this,FeedActivity.class));
                break;
            case R.id.setting_about://关于我们
                startActivity(new Intent(this,AboutActivity.class));
                break;
            case R.id.setting_exit://退出登录
                toast.toast(this,"退出成功！");
                User.clearLogin(this);
                MyApplication.removeActivity();
                startActivity(new Intent(this, LoginActivity.class));
//              if (flag){
//                  return;
//              }
//                else {
//                  model.exit(this);
//              }
                break;
        }
    }

    @Override
    public void onSuccess() {
        toast.toast(this,"退出成功！");
        User.removeJPSH(this);
        User.clearLogin(this);
        MyApplication.removeActivity();
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void onError(String obj) {
        toast.toast(this,obj);
    }
}
