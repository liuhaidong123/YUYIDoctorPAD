package com.technology.yuyidoctorpad.activity.Registion;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.Net.OkUtils;
import com.technology.yuyidoctorpad.Net.gson;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.lhdUtils.MyDialog;
import com.technology.yuyidoctorpad.lzhUtils.toast;
import com.technology.yuyidoctorpad.lzhViews.MyActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


//intent.putExtra("id",lis.get(position).getId()+"");
//挂号信息详情
public class My_registration_Info_Activity extends MyActivity {
    TextView titleTextView;
    private final Context con = My_registration_Info_Activity.this;
    TextView my_registrationInfo_textV_time,my_registrationInfo_textV_hospitalName;//就诊时间,医院

    private TextView my_registrationInfo_textV_bianhao, my_registrationInfo_textV_name, my_registrationInfo_textV_sex;//编号,名字，性别
    private TextView my_registrationInfo_textV_age, my_registrationInfo_textV_tel, my_registrationInfo_textV_address;//年龄，电话，地址
    private TextView my_registrationInfo_textV_keshi, my_registrationInfo_textV_docName, my_registrationInfo_textV_docAddress;//科室名称，医生名称，科室地址
    private String id;
    private String resStr;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    MyDialog.stopDia();
                    toast.toast(con,"网络异常！");
                    break;
                case 1:
                    MyDialog.stopDia();
                    try {
                        Bean_MyRegister_info info = gson.gson.fromJson(resStr, Bean_MyRegister_info.class);
                        if (info != null) {
                            if ("0".equals(info.getCode())) {
                                Bean_MyRegister_info.ResultBean bean = info.getResult();
                                if (bean != null) {
//                                    my_registrationInfo_textV_bianhao.setText();
                                    my_registrationInfo_textV_name.setText(bean.getTrueName());
                                    String sex = "女";
                                    if (bean.getGender() != null) {
                                        if ("0".equals(bean.getGender())) {
                                            sex = "女";
                                        } else if (!"1".equals(bean.getGender())) {
                                            sex = "男";
                                        }
                                    } else {
                                        sex = "保密";
                                    }

                                    my_registrationInfo_textV_sex.setText(sex);
                                    my_registrationInfo_textV_bianhao.setText(bean.getId()+"");
                                    my_registrationInfo_textV_age.setText((bean.getAge() == null ? 0 : bean.getAge()) + "");
//                                    if (bean.getAge()==null){
//                                        my_registrationInfo_textV_age.setText("用户未填写");
//                                    }
                                    my_registrationInfo_textV_tel.setText(bean.getTelephone() + "");
                                    if (bean.getTelephone() == null) {
                                        my_registrationInfo_textV_tel.setText("用户未填写");
                                    }
                                    my_registrationInfo_textV_hospitalName.setText(bean.getHospitalName());
                                    my_registrationInfo_textV_time.setText(bean.getVisitTimeString());
//                                    my_registrationInfo_textV_address.setText(bean.get);//地址
                                    my_registrationInfo_textV_keshi.setText(bean.getDepartmentName());
                                    my_registrationInfo_textV_docName.setText(bean.getPhysicianTrueName());
                                    my_registrationInfo_textV_docAddress.setText(bean.getClinicName());//门诊
                                } else {
                                    Toast.makeText(con, "获取挂号信息失败", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(con, "获取挂号信息失败", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            toast.toast(con,"没有查询到数据！");
                        }
                    } catch (Exception e) {
                        toast.toast(con,"数据异常！");
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setChilidView(R.layout.activity_my_registration__info_);
        initView();
        id = getIntent().getStringExtra("id");
        if (!"".equals(id) && !TextUtils.isEmpty(id)) {
            getData();
        }
    }


    private void initView() {

        titleTextView = (TextView) findViewById(R.id.titleinclude_text);
        titleTextView.setText("挂号单");
        my_registrationInfo_textV_hospitalName= (TextView) findViewById(R.id.my_registrationInfo_textV_hospitalName);
        my_registrationInfo_textV_time= (TextView) findViewById(R.id.my_registrationInfo_textV_time);

        my_registrationInfo_textV_bianhao = (TextView) findViewById(R.id.my_registrationInfo_textV_bianhao);
        my_registrationInfo_textV_name = (TextView) findViewById(R.id.my_registrationInfo_textV_name);
        my_registrationInfo_textV_sex = (TextView) findViewById(R.id.my_registrationInfo_textV_sex);

        my_registrationInfo_textV_age = (TextView) findViewById(R.id.my_registrationInfo_textV_age);
        my_registrationInfo_textV_tel = (TextView) findViewById(R.id.my_registrationInfo_textV_tel);
        my_registrationInfo_textV_address = (TextView) findViewById(R.id.my_registrationInfo_textV_address);


        my_registrationInfo_textV_keshi = (TextView) findViewById(R.id.my_registrationInfo_textV_keshi);
        my_registrationInfo_textV_docName = (TextView) findViewById(R.id.my_registrationInfo_textV_docName);
        my_registrationInfo_textV_docAddress = (TextView) findViewById(R.id.my_registrationInfo_textV_docAddress);
    }

    //获取挂号单http://192.168.1.55:8080/yuyi/register/get.do?token=EA62E69E02FABA4E4C9A0FDC1C7CAE10&id=1
    public void getData() {
        MyDialog.showDialog(My_registration_Info_Activity.this);
        Map<String, String> m = new HashMap<>();
        m.put("token", User.token);
        m.put("id", id);
        OkUtils.getCall(Ip.path + Ip.interface_MyRegisterGH_Msg, m, OkUtils.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr = response.body().string();
                Log.i("获取挂号详情---", resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }
}
