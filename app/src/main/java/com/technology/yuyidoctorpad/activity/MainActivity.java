package com.technology.yuyidoctorpad.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.technology.yuyidoctorpad.bean.BeanPriRong;
import com.technology.yuyidoctorpad.fragment.AskFragment;
import com.technology.yuyidoctorpad.fragment.CircleFragment;
import com.technology.yuyidoctorpad.fragment.myFragment.MyFragment;
import com.technology.yuyidoctorpad.fragment.paintFragment.PatientFragment;
import com.technology.yuyidoctorpad.lzhUtils.JpRegister;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout mInfor_Btn_Rl, mCircle_Btn_Rl, mPatient_Btn_Rl, mMy_Btn_rl, mFragment_Rl;
    private TextView mInfor_Tv, mCircle_Tv, mPatient_Tv, mMy_Tv;
    private ImageView Infor_Img, mCircle_Img, mPatient_Img, mMy_Img;
    private FragmentManager mFragmentManager;
    public final String informationTag = "informationFragment";
    public final String academicTag = "academicFragment";
    public final String patientTag = "patientFragment";
    public final String myTag = "myFragment";

    private String resStr;
    private Handler han=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    break;
                case 1:
                    try{
                        BeanPriRong rong= gson.gson.fromJson(resStr,BeanPriRong.class);
                        if (rong!=null){
                            if (rong.getCode()==0){
                                if (rong.isPermissionInfo()==true){
                                    User.hasvRongPri=true;
                                    String name="医生";
                                    String uri="http://img5.imgtn.bdimg.com/it/u=1482475142,4125104797&fm=23&gp=0.jpg";
                                    if (rong.getTrueName()!=null&&!"".equals(rong.getTrueName())){
                                        name=rong.getTrueName();
                                    }
                                    if (!"".equals(rong.getAvatar())&&!TextUtils.isEmpty(rong.getAvatar())){
                                        uri=rong.getAvatar();
                                    }
                                    Log.e("name====",name);
//                                    io.rong.imlib.model.UserInfo info=new io.rong.imlib.model.UserInfo(rong.getId()+"",name, Uri.parse(uri));
                                    RongUserInfo.RongToken=rong.getToken();
                                    RongConnection.connRong(MainActivity.this, RongUserInfo.RongToken);
                                }
                                else {
                                    Log.e("当前医生无法接收到咨询xinxi ","mainActivity:医院未授予当前医生接收视频到权限");
                                }
                            }
                            else if (rong.getCode()==-1){
                                Log.e("当前用户信息无法查询到","mainActivity:当前用户没有在任何医院注册，请通知去注册");
                            }
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CheckPri();
        initUI();
        if (JpRegister.getInstance().isJPSHSucc(MainActivity.this) == false) {
            JpRegister.getInstance().setAlias(MainActivity.this, User.tele);
            Log.e("激光推送在MainActivity注册----", "Login激光推送注册失败");
        } else {
            Log.e("激光推送在LoginActiity注册----", "Login激光推送注册成功");
        }
    }


    private void initUI() {
        mInfor_Btn_Rl = (RelativeLayout) findViewById(R.id.infor_rl_btn);
        mCircle_Btn_Rl = (RelativeLayout) findViewById(R.id.circle_rl_btn);
        mPatient_Btn_Rl = (RelativeLayout) findViewById(R.id.patient_rl_btn);
        mMy_Btn_rl = (RelativeLayout) findViewById(R.id.my_rl_btn);
        mFragment_Rl = (RelativeLayout) findViewById(R.id.fragment_rl);//存放fragment的布局

        mInfor_Btn_Rl.setOnClickListener(this);
        mCircle_Btn_Rl.setOnClickListener(this);
        mPatient_Btn_Rl.setOnClickListener(this);
        mMy_Btn_rl.setOnClickListener(this);


        mInfor_Tv = (TextView) findViewById(R.id.infor_text);
        mCircle_Tv = (TextView) findViewById(R.id.circle_text);
        mPatient_Tv = (TextView) findViewById(R.id.patient_text);
        mMy_Tv = (TextView) findViewById(R.id.my_text);

        Infor_Img = (ImageView) findViewById(R.id.infor_image);
        mCircle_Img = (ImageView) findViewById(R.id.circle_image);
        mPatient_Img = (ImageView) findViewById(R.id.patient_image);
        mMy_Img = (ImageView) findViewById(R.id.my_image);

        mFragmentManager = getSupportFragmentManager();
        showInformationFragment();
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == mInfor_Btn_Rl.getId()) {//资讯
            selectInfor();
            showInformationFragment();
        } else if (id == mCircle_Btn_Rl.getId()) {//学术圈
            selectCircle();
            showAcademicFragment();
        } else if (id == mPatient_Btn_Rl.getId()) {//患者
            selectPatient();
            showPatientFragment();
        } else if (id == mMy_Btn_rl.getId()) {//个人
            selectMy();
            showMyFragment();
        }
    }


    private void selectInfor() {
        mInfor_Tv.setTextColor(ContextCompat.getColor(this, R.color.color_1ebeec));
        Infor_Img.setImageResource(R.mipmap.infor);
        mCircle_Tv.setTextColor(ContextCompat.getColor(this, R.color.color_a6a6a6));
        mCircle_Img.setImageResource(R.mipmap.circle_no);
        mPatient_Tv.setTextColor(ContextCompat.getColor(this, R.color.color_a6a6a6));
        mPatient_Img.setImageResource(R.mipmap.patient_no);
        mMy_Tv.setTextColor(ContextCompat.getColor(this, R.color.color_a6a6a6));
        mMy_Img.setImageResource(R.mipmap.my_no);
    }

    private void selectCircle() {
        mInfor_Tv.setTextColor(ContextCompat.getColor(this, R.color.color_a6a6a6));
        Infor_Img.setImageResource(R.mipmap.infor_no);
        mCircle_Tv.setTextColor(ContextCompat.getColor(this, R.color.color_1ebeec));
        mCircle_Img.setImageResource(R.mipmap.circle);
        mPatient_Tv.setTextColor(ContextCompat.getColor(this, R.color.color_a6a6a6));
        mPatient_Img.setImageResource(R.mipmap.patient_no);
        mMy_Tv.setTextColor(ContextCompat.getColor(this, R.color.color_a6a6a6));
        mMy_Img.setImageResource(R.mipmap.my_no);
    }

    private void selectPatient() {
        mInfor_Tv.setTextColor(ContextCompat.getColor(this, R.color.color_a6a6a6));
        Infor_Img.setImageResource(R.mipmap.infor_no);
        mCircle_Tv.setTextColor(ContextCompat.getColor(this, R.color.color_a6a6a6));
        mCircle_Img.setImageResource(R.mipmap.circle_no);
        mPatient_Tv.setTextColor(ContextCompat.getColor(this, R.color.color_1ebeec));
        mPatient_Img.setImageResource(R.mipmap.patient);
        mMy_Tv.setTextColor(ContextCompat.getColor(this, R.color.color_a6a6a6));
        mMy_Img.setImageResource(R.mipmap.my_no);

    }

    private void selectMy() {
        mInfor_Tv.setTextColor(ContextCompat.getColor(this, R.color.color_a6a6a6));
        Infor_Img.setImageResource(R.mipmap.infor_no);
        mCircle_Tv.setTextColor(ContextCompat.getColor(this, R.color.color_a6a6a6));
        mCircle_Img.setImageResource(R.mipmap.circle_no);
        mPatient_Tv.setTextColor(ContextCompat.getColor(this, R.color.color_a6a6a6));
        mPatient_Img.setImageResource(R.mipmap.patient_no);
        mMy_Tv.setTextColor(ContextCompat.getColor(this, R.color.color_1ebeec));
        mMy_Img.setImageResource(R.mipmap.my);

    }

    //显示资讯
    public void showInformationFragment() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        AskFragment askFragment= (AskFragment) mFragmentManager.findFragmentByTag(informationTag);
        CircleFragment circleFragment= (CircleFragment) mFragmentManager.findFragmentByTag(academicTag);
        PatientFragment patientFragment= (PatientFragment) mFragmentManager.findFragmentByTag(patientTag);
        MyFragment myFragment= (MyFragment) mFragmentManager.findFragmentByTag(myTag);

        if (askFragment != null) {//显示资讯
            fragmentTransaction.show(askFragment);
        } else {
            AskFragment informationF = new AskFragment();
            fragmentTransaction.add(mFragment_Rl.getId(), informationF, informationTag);
        }
        if (circleFragment != null) {//隐藏学术圈
            fragmentTransaction.hide(circleFragment);
        }
        if (patientFragment != null) {//隐藏患者
            fragmentTransaction.hide(patientFragment);
        }
        if (myFragment != null) {//隐藏我的
            fragmentTransaction.hide(myFragment);
        }
        fragmentTransaction.commit();
    }
    //显示学术圈页面
    public void showAcademicFragment() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        AskFragment askFragment= (AskFragment) mFragmentManager.findFragmentByTag(informationTag);
        CircleFragment circleFragment= (CircleFragment) mFragmentManager.findFragmentByTag(academicTag);
        PatientFragment patientFragment= (PatientFragment) mFragmentManager.findFragmentByTag(patientTag);
        MyFragment myFragment= (MyFragment) mFragmentManager.findFragmentByTag(myTag);

        if (circleFragment != null) {//显示学术圈
            fragmentTransaction.show(circleFragment);
        } else {
            CircleFragment circleF = new CircleFragment();
            fragmentTransaction.add(mFragment_Rl.getId(), circleF, academicTag);
        }
        if (askFragment != null) {//隐藏资讯
            fragmentTransaction.hide(askFragment);
        }
        if (patientFragment != null) {//隐藏患者
            fragmentTransaction.hide(patientFragment);
        }
        if (myFragment != null) {//隐藏我的
            fragmentTransaction.hide(myFragment);
        }
        fragmentTransaction.commit();
    }
    //显示患者页面
    public void showPatientFragment() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        AskFragment askFragment= (AskFragment) mFragmentManager.findFragmentByTag(informationTag);
        CircleFragment circleFragment= (CircleFragment) mFragmentManager.findFragmentByTag(academicTag);
        PatientFragment patientFragment= (PatientFragment) mFragmentManager.findFragmentByTag(patientTag);
        MyFragment myFragment= (MyFragment) mFragmentManager.findFragmentByTag(myTag);

        if (patientFragment != null) {//显示患者
            fragmentTransaction.show(patientFragment);
        } else {
            PatientFragment patientF = new PatientFragment();
            fragmentTransaction.add(mFragment_Rl.getId(), patientF, patientTag);
        }
        if (askFragment != null) {//隐藏资讯
            fragmentTransaction.hide(askFragment);
        }
        if (circleFragment != null) {//隐藏xueshuquan
            fragmentTransaction.hide(circleFragment);
        }
        if (myFragment != null) {//隐藏我的
            fragmentTransaction.hide(myFragment);
        }
        fragmentTransaction.commit();
    }
    //显示我的页面
    public void showMyFragment() {

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        AskFragment askFragment= (AskFragment) mFragmentManager.findFragmentByTag(informationTag);
        CircleFragment circleFragment= (CircleFragment) mFragmentManager.findFragmentByTag(academicTag);
        PatientFragment patientFragment= (PatientFragment) mFragmentManager.findFragmentByTag(patientTag);
        MyFragment myFragment= (MyFragment) mFragmentManager.findFragmentByTag(myTag);

        if (myFragment != null) {//显示wode
            fragmentTransaction.show(myFragment);
        } else {
            MyFragment myF = new MyFragment();
            fragmentTransaction.add(mFragment_Rl.getId(), myF, myTag);
        }
        if (askFragment != null) {//隐藏资讯
            fragmentTransaction.hide(askFragment);
        }
        if (circleFragment != null) {//隐藏xueshuquan
            fragmentTransaction.hide(circleFragment);
        }
        if (patientFragment != null) {//隐藏我的
            fragmentTransaction.hide(patientFragment);
        }
        fragmentTransaction.commit();
    }
    //检查当前用户是否有权限接受视频，语音，聊天
    private void CheckPri() {
        Map<String,String> mp=new HashMap<>();
        mp.put("telephone", User.tele);
        OkUtils.getCall(Ip.path+Ip.interface_CheckPri,mp, OkUtils.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                han.sendEmptyMessage(0);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("聊天权限检查---",resStr);
                han.sendEmptyMessage(1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }
}
