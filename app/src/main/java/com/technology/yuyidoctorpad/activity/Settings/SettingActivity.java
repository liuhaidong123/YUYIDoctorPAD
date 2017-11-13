package com.technology.yuyidoctorpad.activity.Settings;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.lzhUtils.MyApplication;
import com.technology.yuyidoctorpad.lzhViews.MyActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends MyActivity {
    @BindView(R.id.titleinclude_text)TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setChilidView(R.layout.activity_setting);
        title.setText("设置");
    }
    @OnClick({R.id.setting_contact,R.id.setting_about,R.id.setting_exit})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.setting_contact://联系我们

                break;
            case R.id.setting_about://关于我们

                break;
            case R.id.setting_exit://退出登录
                User.clearLogin(this);
                MyApplication.removeActivity();
                break;
        }
    }
}
