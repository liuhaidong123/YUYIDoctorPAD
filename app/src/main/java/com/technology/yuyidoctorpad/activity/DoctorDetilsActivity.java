package com.technology.yuyidoctorpad.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technology.yuyidoctorpad.HttpTools.HttpTools;
import com.technology.yuyidoctorpad.HttpTools.UrlTools;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.bean.DoctorDetails.Root;
import com.technology.yuyidoctorpad.lhdUtils.NetWorkUtils;
import com.technology.yuyidoctorpad.lhdUtils.RoundImageView;
import com.technology.yuyidoctorpad.lhdUtils.ToastUtils;

public class DoctorDetilsActivity extends AppCompatActivity {
    private long doctorId = -1;
    private RoundImageView mHead_Img;
    private RelativeLayout mNo_NetWork_Rl;
    private TextView mName, mJob, mDepartment, mTelephone, mAskPower, mLookPower, mRegisterPower, details_power_tv;
    private ImageView mEdit_Img;
    private HttpTools mHttptools;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 58) {
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root != null) {
                        Picasso.with(getApplicationContext()).load(UrlTools.BASE + root.getAvatar()).error(R.mipmap.erroruser).into(mHead_Img);

                        mName.setText(root.getTrueName());
                        mJob.setText(root.getTitle());
                        mDepartment.setText(root.getDepartmentName());
                        mTelephone.setText(root.getTelephone() + "");
                        if (root.getPermissionInfo()) {
                            mAskPower.setVisibility(View.VISIBLE);
                            mAskPower.setText("咨询权限");
                        } else {
                            mAskPower.setVisibility(View.GONE);
                        }
                        if (root.getPermissionData()) {
                            mLookPower.setVisibility(View.VISIBLE);
                            mLookPower.setText("查看数据权限");
                        } else {
                            mLookPower.setVisibility(View.GONE);
                        }
                        if (root.getPermissionRegister()) {
                            mRegisterPower.setVisibility(View.VISIBLE);
                            mRegisterPower.setText("挂号接收权限");
                        } else {
                            mRegisterPower.setVisibility(View.GONE);
                        }

                        details_power_tv.setText("拥有权限：");
                        mEdit_Img.setImageResource(R.mipmap.editdoctor);
                    } else {
                        ToastUtils.myToast(getApplicationContext(), "获取医生详情失败");
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detils);
        initUI();
    }

    private void initUI() {
        doctorId = getIntent().getLongExtra("doctorId", -1);
        Log.e("doctorId=",doctorId+"");
        mNo_NetWork_Rl = (RelativeLayout) findViewById(R.id.no_net_work);
        mHead_Img = (RoundImageView) findViewById(R.id.details_head);
        mName = (TextView) findViewById(R.id.details_name);
        mJob = (TextView) findViewById(R.id.details_job);
        mDepartment = (TextView) findViewById(R.id.details_department);
        mTelephone = (TextView) findViewById(R.id.details_telephone);
        mAskPower = (TextView) findViewById(R.id.details_askpower1);
        mLookPower = (TextView) findViewById(R.id.details_lookpower2);
        mRegisterPower = (TextView) findViewById(R.id.details_registerpower3);
        details_power_tv = (TextView) findViewById(R.id.details_power);
        //修改
        mEdit_Img = (ImageView) findViewById(R.id.edit_img);
        mEdit_Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorDetilsActivity.this, AddDcotorActivity.class);
                intent.putExtra("doctorId", doctorId);
                Log.e("doctorId=",doctorId+"");
                startActivity(intent);
                finish();
            }
        });

        if (NetWorkUtils.isNetworkConnected(this)) {
            mHttptools = HttpTools.getHttpToolsInstance();
            mHttptools.getDoctorDetails(handler, doctorId);//获取医生详情
        } else {
            mNo_NetWork_Rl.setVisibility(View.VISIBLE);
        }
    }
}
