package com.technology.yuyidoctorpad.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.DepartmentFragment;
import com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.DoctorFragment;
import com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.HomePageFragment;
import com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.InformationPostFragment;
import com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.RegisterNumFragment;

import java.util.ArrayList;
import java.util.List;

public class HospitalHomePageActivity extends AppCompatActivity implements View.OnClickListener {

    private List<String> mList = new ArrayList<>();
    private ListView myListview;
    private ImageView mMenu_Img;
    private TextView title;

    private RelativeLayout mFragment_Rl;
    private FragmentManager mFragmentManager;
    public final String homepageTag = "homepage";
    public final String departmentTag = "department";
    public final String doctorTag = "doctor";
    public final String registerTag = "register";
    public final String informationTag = "information";
    public final String addDoctorTag = "adddoctor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_home_page);
        initUI();
    }

    private void initUI() {
        mList.add("医院主页");
        mList.add("科室信息");
        mList.add("医生信息");
        mList.add("挂号设置");
        mList.add("资讯发布");

        mMenu_Img = (ImageView) findViewById(R.id.h_menu);
        mMenu_Img.setOnClickListener(this);
        title = (TextView) findViewById(R.id.h_title);
        myListview = (ListView) findViewById(R.id.h_type_listview);
        myListview.setAdapter(new MyAdapter());
        myListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        showHomePage();
                        myListview.setVisibility(View.GONE);
                        break;
                    case 1:
                        showDepartment();
                        myListview.setVisibility(View.GONE);
                        break;
                    case 2:
                        showDoctor();
                        myListview.setVisibility(View.GONE);
                        break;
                    case 3:
                        showRegister();
                        myListview.setVisibility(View.GONE);
                        break;
                    case 4:
                        showInformation();
                        myListview.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });

        mFragment_Rl = (RelativeLayout) findViewById(R.id.fragment_rl);
        mFragmentManager = getSupportFragmentManager();
        showHomePage();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == mMenu_Img.getId()) {
            if (myListview.getVisibility() == View.VISIBLE) {
                myListview.setVisibility(View.GONE);
            } else if (myListview.getVisibility() == View.GONE) {
                myListview.setVisibility(View.VISIBLE);
            }

        }
    }

    public void showHomePage() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        HomePageFragment homePageFragment = (HomePageFragment) mFragmentManager.findFragmentByTag(homepageTag);
        DepartmentFragment departmentFragment = (DepartmentFragment) mFragmentManager.findFragmentByTag(departmentTag);
        DoctorFragment doctorFragment = (DoctorFragment) mFragmentManager.findFragmentByTag(doctorTag);
        RegisterNumFragment registerNumFragment = (RegisterNumFragment) mFragmentManager.findFragmentByTag(registerTag);
        InformationPostFragment informationPostFragment = (InformationPostFragment) mFragmentManager.findFragmentByTag(informationTag);
        //   AddDoctorFragment addDoctorFragment= (AddDoctorFragment) mFragmentManager.findFragmentByTag(addDoctorTag);
        if (homePageFragment != null) {
            fragmentTransaction.show(homePageFragment);
        } else {
            HomePageFragment homePage = new HomePageFragment();
            fragmentTransaction.add(mFragment_Rl.getId(), homePage, homepageTag);
        }

        if (departmentFragment != null) {
            fragmentTransaction.hide(departmentFragment);
        }
        if (doctorFragment != null) {
            fragmentTransaction.hide(doctorFragment);
        }
        if (registerNumFragment != null) {
            fragmentTransaction.hide(registerNumFragment);
        }
        if (informationPostFragment != null) {
            fragmentTransaction.hide(informationPostFragment);
        }
//        if (addDoctorFragment!=null){
//            fragmentTransaction.hide(addDoctorFragment);
//        }
        fragmentTransaction.commit();
        title.setText("医院主页");

    }

    public void showDepartment() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        HomePageFragment homePageFragment = (HomePageFragment) mFragmentManager.findFragmentByTag(homepageTag);
        DepartmentFragment departmentFragment = (DepartmentFragment) mFragmentManager.findFragmentByTag(departmentTag);
        DoctorFragment doctorFragment = (DoctorFragment) mFragmentManager.findFragmentByTag(doctorTag);
        RegisterNumFragment registerNumFragment = (RegisterNumFragment) mFragmentManager.findFragmentByTag(registerTag);
        InformationPostFragment informationPostFragment = (InformationPostFragment) mFragmentManager.findFragmentByTag(informationTag);

        if (departmentFragment != null) {
            fragmentTransaction.show(homePageFragment);
        } else {
            DepartmentFragment department = new DepartmentFragment();
            fragmentTransaction.add(mFragment_Rl.getId(), department, departmentTag);
        }

        if (homePageFragment != null) {
            fragmentTransaction.hide(homePageFragment);
        }
        if (doctorFragment != null) {
            fragmentTransaction.hide(doctorFragment);
        }
        if (registerNumFragment != null) {
            fragmentTransaction.hide(registerNumFragment);
        }
        if (informationPostFragment != null) {
            fragmentTransaction.hide(informationPostFragment);
        }
//        AddDoctorFragment addDoctorFragment= (AddDoctorFragment) mFragmentManager.findFragmentByTag(addDoctorTag);
//        if (addDoctorFragment!=null){
//            fragmentTransaction.hide(addDoctorFragment);
//        }
        fragmentTransaction.commit();
        title.setText("科室信息");
    }

    public void showDoctor() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        HomePageFragment homePageFragment = (HomePageFragment) mFragmentManager.findFragmentByTag(homepageTag);
        DepartmentFragment departmentFragment = (DepartmentFragment) mFragmentManager.findFragmentByTag(departmentTag);
        DoctorFragment doctorFragment = (DoctorFragment) mFragmentManager.findFragmentByTag(doctorTag);
        RegisterNumFragment registerNumFragment = (RegisterNumFragment) mFragmentManager.findFragmentByTag(registerTag);
        InformationPostFragment informationPostFragment = (InformationPostFragment) mFragmentManager.findFragmentByTag(informationTag);

        if (doctorFragment != null) {
            fragmentTransaction.show(doctorFragment);
        } else {
            DoctorFragment doctor = new DoctorFragment();
            fragmentTransaction.add(mFragment_Rl.getId(), doctor, doctorTag);
        }

        if (homePageFragment != null) {
            fragmentTransaction.hide(homePageFragment);
        }
        if (departmentFragment != null) {
            fragmentTransaction.hide(departmentFragment);
        }
        if (registerNumFragment != null) {
            fragmentTransaction.hide(registerNumFragment);
        }
        if (informationPostFragment != null) {
            fragmentTransaction.hide(informationPostFragment);
        }
//        AddDoctorFragment addDoctorFragment= (AddDoctorFragment) mFragmentManager.findFragmentByTag(addDoctorTag);
//        if (addDoctorFragment!=null){
//            fragmentTransaction.hide(addDoctorFragment);
//        }
        fragmentTransaction.commit();
        title.setText("医生信息");
    }

    public void showRegister() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        HomePageFragment homePageFragment = (HomePageFragment) mFragmentManager.findFragmentByTag(homepageTag);
        DepartmentFragment departmentFragment = (DepartmentFragment) mFragmentManager.findFragmentByTag(departmentTag);
        DoctorFragment doctorFragment = (DoctorFragment) mFragmentManager.findFragmentByTag(doctorTag);
        RegisterNumFragment registerNumFragment = (RegisterNumFragment) mFragmentManager.findFragmentByTag(registerTag);
        InformationPostFragment informationPostFragment = (InformationPostFragment) mFragmentManager.findFragmentByTag(informationTag);

        if (registerNumFragment != null) {
            fragmentTransaction.show(registerNumFragment);
        } else {
            RegisterNumFragment register = new RegisterNumFragment();
            fragmentTransaction.add(mFragment_Rl.getId(), register, registerTag);
        }

        if (homePageFragment != null) {
            fragmentTransaction.hide(homePageFragment);
        }
        if (departmentFragment != null) {
            fragmentTransaction.hide(departmentFragment);
        }
        if (doctorFragment != null) {
            fragmentTransaction.hide(doctorFragment);
        }
        if (informationPostFragment != null) {
            fragmentTransaction.hide(informationPostFragment);
        }
//        AddDoctorFragment addDoctorFragment= (AddDoctorFragment) mFragmentManager.findFragmentByTag(addDoctorTag);
//        if (addDoctorFragment!=null){
//            fragmentTransaction.hide(addDoctorFragment);
//        }
        fragmentTransaction.commit();
        title.setText("挂号设置");
    }

    public void showInformation() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        HomePageFragment homePageFragment = (HomePageFragment) mFragmentManager.findFragmentByTag(homepageTag);
        DepartmentFragment departmentFragment = (DepartmentFragment) mFragmentManager.findFragmentByTag(departmentTag);
        DoctorFragment doctorFragment = (DoctorFragment) mFragmentManager.findFragmentByTag(doctorTag);
        RegisterNumFragment registerNumFragment = (RegisterNumFragment) mFragmentManager.findFragmentByTag(registerTag);
        InformationPostFragment informationPostFragment = (InformationPostFragment) mFragmentManager.findFragmentByTag(informationTag);

        if (informationPostFragment != null) {
            fragmentTransaction.show(informationPostFragment);
        } else {
            InformationPostFragment information = new InformationPostFragment();
            fragmentTransaction.add(mFragment_Rl.getId(), information, informationTag);
        }

        if (homePageFragment != null) {
            fragmentTransaction.hide(homePageFragment);
        }
        if (departmentFragment != null) {
            fragmentTransaction.hide(departmentFragment);
        }
        if (doctorFragment != null) {
            fragmentTransaction.hide(doctorFragment);
        }
        if (registerNumFragment != null) {
            fragmentTransaction.hide(registerNumFragment);
        }
        fragmentTransaction.commit();
        title.setText("资讯");
    }

    public void showAddDoctor() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        DoctorFragment doctorFragment = (DoctorFragment) mFragmentManager.findFragmentByTag(doctorTag);


        if (doctorFragment != null) {
            fragmentTransaction.hide(doctorFragment);
        }
        fragmentTransaction.commit();
        title.setText("添加医生信息");
    }

    public boolean isMenuShow() {
        if (myListview.getVisibility() == View.VISIBLE) {
            myListview.setVisibility(View.GONE);
            return true;
        }
        return false;
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            MyHolder myHolder = null;
            if (view == null) {
                myHolder = new MyHolder();
                view = LayoutInflater.from(HospitalHomePageActivity.this).inflate(R.layout.hospital_homepage_item, null);
                myHolder.textView = view.findViewById(R.id.h_type);
                view.setTag(myHolder);
            } else {
                myHolder = (MyHolder) view.getTag();
            }
            myHolder.textView.setText(mList.get(i));
            return view;
        }

        class MyHolder {
            TextView textView;
        }
    }
}
