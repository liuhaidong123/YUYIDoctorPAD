package com.technology.yuyidoctorpad.activity.UserInfo;

import android.os.Bundle;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technology.yuyidoctorpad.Enum.IntentValue;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.fragment.myFragment.UserBean;
import com.technology.yuyidoctorpad.lhdUtils.RoundImageView;
import com.technology.yuyidoctorpad.lzhUtils.Empty;
import com.technology.yuyidoctorpad.lzhViews.MyActivity;

import butterknife.BindView;

public class UserInfoActivity extends MyActivity {
    UserBean bean;
    @BindView(R.id.titleinclude_text)TextView titleinclude_text;
    @BindView(R.id.UserInfo_Image)RoundImageView UserInfo_Image;
    @BindView(R.id.UserInfo_Name)TextView UserInfo_Name;
    @BindView(R.id.UserInfo_HospitalName)TextView UserInfo_HospitalName;
    @BindView(R.id.UserInfo_KS)TextView UserInfo_KS;
    @BindView(R.id.UserInfo_ZC)TextView UserInfo_ZC;
    @BindView(R.id.UserInfo_Phone)TextView UserInfo_Phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setChilidView(R.layout.activity_user_info);
        titleinclude_text.setText("个人信息");
        bean= (UserBean) getIntent().getSerializableExtra(IntentValue.UserInfo);
        initView();
    }

    private void initView() {
        Picasso.with(this).load(Ip.imagePath+bean.getPhysician().getAvatar()).placeholder(R.mipmap.erroruser).error(R.mipmap.erroruser).into(UserInfo_Image);
        UserInfo_Name.setText(Empty.notEmpty(bean.getPhysician().getTrueName())?bean.getPhysician().getTrueName():"用户未填写");

        UserInfo_HospitalName.setText(Empty.notEmpty(bean.getPhysician().getHospitalName())?bean.getPhysician().getHospitalName():"未获取");
        UserInfo_KS.setText(Empty.notEmpty(bean.getPhysician().getDepartmentName())?bean.getPhysician().getDepartmentName():"未获取");
        UserInfo_ZC.setText(Empty.notEmpty(bean.getPhysician().getTitle())?bean.getPhysician().getTitle():"未获取");
        UserInfo_Phone.setText(bean.getPhysician().getTelephone()==0?"未获取":bean.getPhysician().getTelephone()+"");
    }


}
