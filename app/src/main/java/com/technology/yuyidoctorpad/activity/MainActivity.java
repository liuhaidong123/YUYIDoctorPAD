package com.technology.yuyidoctorpad.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.fragment.AskFragment;
import com.technology.yuyidoctorpad.fragment.CircleFragment;
import com.technology.yuyidoctorpad.fragment.myFragment.MyFragment;
import com.technology.yuyidoctorpad.fragment.paintFragment.PatientFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout mInfor_Btn_Rl, mCircle_Btn_Rl, mPatient_Btn_Rl, mMy_Btn_rl, mFragment_Rl;
    private TextView mInfor_Tv, mCircle_Tv, mPatient_Tv, mMy_Tv;
    private ImageView Infor_Img, mCircle_Img, mPatient_Img, mMy_Img;
    private FragmentManager mFragmentManager;
    public final String informationTag = "informationFragment";
    public final String academicTag = "academicFragment";
    public final String patientTag = "patientFragment";
    public final String myTag = "myFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
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

}
