package com.technology.yuyidoctorpad.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.fragment.HospitalFragment.HospitalMessageFragment;
import com.technology.yuyidoctorpad.fragment.HospitalFragment.ManagerMessageFragment;
import com.technology.yuyidoctorpad.fragment.HospitalFragment.SubmitMessageFragment;

import java.io.File;
import java.util.List;

public class WriteHospitalMessageActivity extends AppCompatActivity {

    private RelativeLayout mFragment_Rl;
    private FragmentManager mFragmentManager;
    public final String hospitalTag = "hospital";
    public final String managerTag = "manager";
    public final String submitTag = "submit";

    private TextView mHospital_Tv, mManager_Tv, mSubmit_Tv;
    private ImageView mHospital_Img, mManager_Img, mHospital_SanJiao_Img, mManager_SanJiao_Img, mSubmit_Sanjiao_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_hospital_message);
        init();
    }

    private void init() {
        mFragment_Rl = (RelativeLayout) findViewById(R.id.hospital_fragment_rl);
        mFragmentManager = getSupportFragmentManager();
        mHospital_Tv = (TextView) findViewById(R.id.hospital_mess);
        mManager_Tv = (TextView) findViewById(R.id.manager_mess);
        mSubmit_Tv = (TextView) findViewById(R.id.submit_mess);
        mHospital_Img = (ImageView) findViewById(R.id.hospital_line);
        mManager_Img = (ImageView) findViewById(R.id.manager_line);
        mHospital_SanJiao_Img = (ImageView) findViewById(R.id.hospital_sanjiao_img);
        mManager_SanJiao_Img = (ImageView) findViewById(R.id.manager_sanjiao_img);
        mSubmit_Sanjiao_img = (ImageView) findViewById(R.id.submit_sanjiao_img);


        showHospitalFragment();
    }

    public void showHospitalFragment() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        HospitalMessageFragment hospitalMessageFragment = (HospitalMessageFragment) mFragmentManager.findFragmentByTag(hospitalTag);
        ManagerMessageFragment managerMessageFragment = (ManagerMessageFragment) mFragmentManager.findFragmentByTag(managerTag);
        SubmitMessageFragment submitMessageFragment = (SubmitMessageFragment) mFragmentManager.findFragmentByTag(submitTag);

        if (hospitalMessageFragment != null) {
            fragmentTransaction.show(hospitalMessageFragment);
        } else {
            HospitalMessageFragment hospitalFragment = new HospitalMessageFragment();
            fragmentTransaction.add(mFragment_Rl.getId(), hospitalFragment, hospitalTag);
        }
        if (managerMessageFragment != null) {
            fragmentTransaction.hide(managerMessageFragment);
        }
        if (submitMessageFragment != null) {
            fragmentTransaction.hide(submitMessageFragment);
        }
        fragmentTransaction.commit();

        mHospital_Tv.setTextColor(ContextCompat.getColor(this, R.color.color_333333));
        mHospital_SanJiao_Img.setVisibility(View.VISIBLE);
        mHospital_Img.setImageResource(R.mipmap.lineselected);

        mManager_Tv.setTextColor(ContextCompat.getColor(this, R.color.color_c5c5c5));
        mManager_SanJiao_Img.setVisibility(View.INVISIBLE);
        mManager_Img.setImageResource(R.mipmap.line);

        mSubmit_Tv.setTextColor(ContextCompat.getColor(this, R.color.color_c5c5c5));
        mSubmit_Sanjiao_img.setVisibility(View.INVISIBLE);
    }

    public void showManagerFragment(String name, String grade, String jj, String tele, File hospitalFile, List<File> bookFileList,String address) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        HospitalMessageFragment hospitalMessageFragment = (HospitalMessageFragment) mFragmentManager.findFragmentByTag(hospitalTag);
        ManagerMessageFragment managerMessageFragment = (ManagerMessageFragment) mFragmentManager.findFragmentByTag(managerTag);
        SubmitMessageFragment submitMessageFragment = (SubmitMessageFragment) mFragmentManager.findFragmentByTag(submitTag);

        if (managerMessageFragment != null) {
            managerMessageFragment.setHospitalMsg(name,grade,jj,tele,hospitalFile,bookFileList,address);//设置数据
            fragmentTransaction.show(managerMessageFragment);
        } else {
            ManagerMessageFragment managerFragment = new ManagerMessageFragment();
            managerFragment.setHospitalMsg(name,grade,jj,tele,hospitalFile,bookFileList,address);//设置数据
            fragmentTransaction.add(mFragment_Rl.getId(), managerFragment, managerTag);
        }
        if (hospitalMessageFragment != null) {
            fragmentTransaction.hide(hospitalMessageFragment);
        }
        if (submitMessageFragment != null) {
            fragmentTransaction.hide(submitMessageFragment);
        }
        fragmentTransaction.commit();

        mHospital_Tv.setTextColor(ContextCompat.getColor(this, R.color.color_c5c5c5));
        mHospital_SanJiao_Img.setVisibility(View.INVISIBLE);
        mHospital_Img.setImageResource(R.mipmap.line);

        mManager_Tv.setTextColor(ContextCompat.getColor(this, R.color.color_333333));
        mManager_SanJiao_Img.setVisibility(View.VISIBLE);
        mManager_Img.setImageResource(R.mipmap.lineselected);

        mSubmit_Tv.setTextColor(ContextCompat.getColor(this, R.color.color_c5c5c5));
        mSubmit_Sanjiao_img.setVisibility(View.INVISIBLE);
    }


    public void showSubmitFragment() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        HospitalMessageFragment hospitalMessageFragment = (HospitalMessageFragment) mFragmentManager.findFragmentByTag(hospitalTag);
        ManagerMessageFragment managerMessageFragment = (ManagerMessageFragment) mFragmentManager.findFragmentByTag(managerTag);
        SubmitMessageFragment submitMessageFragment = (SubmitMessageFragment) mFragmentManager.findFragmentByTag(submitTag);

        if (submitMessageFragment != null) {
            fragmentTransaction.show(submitMessageFragment);
        } else {
            SubmitMessageFragment submitFragment = new SubmitMessageFragment();
            fragmentTransaction.add(mFragment_Rl.getId(), submitFragment, submitTag);
        }
        if (hospitalMessageFragment != null) {
            fragmentTransaction.hide(hospitalMessageFragment);
        }
        if (managerMessageFragment != null) {
            fragmentTransaction.hide(managerMessageFragment);
        }
        fragmentTransaction.commit();

        mHospital_Tv.setTextColor(ContextCompat.getColor(this, R.color.color_c5c5c5));
        mHospital_SanJiao_Img.setVisibility(View.INVISIBLE);
        mHospital_Img.setImageResource(R.mipmap.line);

        mManager_Tv.setTextColor(ContextCompat.getColor(this, R.color.color_c5c5c5));
        mManager_SanJiao_Img.setVisibility(View.INVISIBLE);
        mManager_Img.setImageResource(R.mipmap.line);

        mSubmit_Tv.setTextColor(ContextCompat.getColor(this, R.color.color_333333));
        mSubmit_Sanjiao_img.setVisibility(View.VISIBLE);
    }
}
