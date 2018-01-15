package com.technology.yuyidoctorpad.activity.Login;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.Net.OkUtils;
import com.technology.yuyidoctorpad.Net.gson;
import com.technology.yuyidoctorpad.activity.Login.Bean.BeanDoc;
import com.technology.yuyidoctorpad.activity.Login.Bean.BeanHosLogin;
import com.technology.yuyidoctorpad.activity.Login.Bean.BeanHosSMS;
import com.technology.yuyidoctorpad.activity.Login.Bean.BeanSMS;
import com.technology.yuyidoctorpad.lhdUtils.MyDialog;
import com.technology.yuyidoctorpad.lzhUtils.Empty;
import com.technology.yuyidoctorpad.lzhUtils.MyApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanyu on 2017/11/13.
 */

public class LoginModel {
    ILogin listener;
    String cookie;
    String resStr;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -2://医生获取验证码
                    listener.getSMSCodeError("网络异常！");
                    break;
                case 2://医生获取验证码
                    try {
                        BeanSMS bean = gson.gson.fromJson(resStr, BeanSMS.class);
                        if (bean != null) {
                            if ("0".equals(bean.getCode())) {
                                listener.getSMSCodeSuccess();
                            } else {
                                listener.getSMSCodeError(Empty.notEmpty(bean.getResult()) ? bean.getResult() : "获取失败！");
                            }
                        } else {
                            listener.getSMSCodeError("获取失败！");
                        }
                    } catch (Exception e) {
                        listener.getSMSCodeError("数据异常！");
                        e.printStackTrace();
                    }
                    break;

                case -1://医生登录
                    MyDialog.stopDia();
                    listener.onError("网络异常！");
                    break;
                case 1://医生登录
                    MyDialog.stopDia();
                    try {
                        BeanDoc beanDoc = gson.gson.fromJson(resStr, BeanDoc.class);
                        if (beanDoc != null) {
                            if ("0".equals(beanDoc.getCode())) {
                                listener.onLoginSuccess(beanDoc);
                            } else {
                                listener.onError(Empty.notEmpty(beanDoc.getMessage()) ? beanDoc.getMessage() : "登录失败！");
                            }
                        } else {
                            listener.onError("登录失败！");
                        }
                    } catch (Exception e) {
                        listener.onError("登录失败！");
                        e.printStackTrace();
                    }
                    break;
                case -3://医院获取验证码
                    listener.getSMSCodeError("网络异常！");
                    break;
                case 3://医院获取验证码
                    try {
                        BeanHosSMS bea = gson.gson.fromJson(resStr, BeanHosSMS.class);
                        if (bea != null) {
                            if ("0".equals(bea.getCode())) {
                                listener.getSMSCodeSuccess();
                            } else {
                                listener.onError(Empty.notEmpty(bea.getResult()) ? bea.getResult() : "获取失败！");
                            }
                        } else {
                            listener.getSMSCodeError("获取失败！");
                        }
                    } catch (Exception e) {
                        listener.getSMSCodeError("数据异常！");
                        e.printStackTrace();
                    }
                    break;
                case -4://医院登录
                    MyDialog.stopDia();
                    listener.onError("网络异常！");
                    break;
                case 4://医院登录
                    MyDialog.stopDia();
                    try {
                        BeanHosLogin be = gson.gson.fromJson(resStr, BeanHosLogin.class);
                        if (be != null) {
                            if ("0".equals(be.getCode())) {
                                listener.onHospitalLoginSuccess(be);
                            } else {
                                listener.onError(be.getMessage());
                            }
                        } else {
                            listener.onError("登录失败！");
                        }
                    } catch (Exception e) {
                        listener.onError("数据异常！");
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    public void login(String phoneNum, String smsCode, ILogin listener, LoginType tp) {
        MyDialog.showDialog(MyApplication.activityCurrent);
        this.listener = listener;
        switch (tp) {
            case DOCTOR:
                onDoctorLogin(phoneNum, smsCode);
                break;
            case HOSPITAL:
                onHospitalLogin(phoneNum, smsCode);
                break;
        }
    }

    public void getSmS(String phoneNum, String currentMillis, String imgcode, String mycookie, ILogin listener, LoginType tp) {
        this.listener = listener;
        switch (tp) {
            case DOCTOR:
                getDocSmsCode(phoneNum, currentMillis, imgcode, mycookie);
               // Log.e("传递中myCooike=", mycookie);
                break;
            case HOSPITAL:
                onHospitalSmsCode(phoneNum, currentMillis, imgcode, mycookie);
                //Log.e("传递中myCooike=", mycookie);
                break;
        }
    }

    //医生登录验证码获取
    private void getDocSmsCode(String phoneNum, String currentMillis, String imgcode, final String mycookie) {
        Map<String, String> mp = new HashMap<>();
        mp.put("id", phoneNum);
        mp.put("ts", currentMillis);
        mp.put("imgcode", imgcode);
        OkUtils.getCallCookie(Ip.path + Ip.interface_SMSCode, mp, mycookie).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(-2);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr = response.body().string();
                //cookie = response.headers().get("Set-Cookie");
                cookie=mycookie;
                Log.i("获取验证码---", resStr);
                //Log.e("获取验证码cookie=", cookie);
                handler.sendEmptyMessage(2);

            }
        });
    }


    //医生登录
    private void onDoctorLogin(String phoneNum, String smsCode) {
        Map<String, String> mp = new HashMap<>();
        mp.put("id", phoneNum);
        mp.put("vcode", smsCode);
        OkUtils.getCallCookie(Ip.path + Ip.interface_Login, mp, cookie).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(-1);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr = response.body().string();
                Log.i("登陆返回---", resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }

    //医院登录
    private void onHospitalLogin(String phoneNum, String smsCode) {
        Map<String, String> mp = new HashMap<>();
        mp.put("id", phoneNum);
        mp.put("vcode", smsCode);
        OkUtils.getCallCookie(Ip.path_F + Ip.interface_HospitalLogin, mp, cookie).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(-4);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr = response.body().string();
                Log.i("医院登录", resStr);
                handler.sendEmptyMessage(4);
            }
        });
    }

    //医院获取验证码
    private void onHospitalSmsCode(String phoneNum, String currentMillis, String imgcode, final String mycookie) {
        Map<String, String> mp = new HashMap<>();
        mp.put("id", phoneNum);
        mp.put("ts", currentMillis);
        mp.put("imgcode", imgcode);
        OkUtils.getCallCookie(Ip.path_F + Ip.interface_SMSCodeHospital, mp, mycookie).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(-3);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr = response.body().string();
               // cookie = response.headers().get("Set-Cookie");
                cookie=mycookie;
                Log.i("医院获取验证码", resStr);
                //Log.e("获取验证码=", cookie);
                handler.sendEmptyMessage(3);
            }
        });
    }
}
