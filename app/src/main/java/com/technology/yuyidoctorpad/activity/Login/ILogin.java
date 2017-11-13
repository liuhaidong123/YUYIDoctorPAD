package com.technology.yuyidoctorpad.activity.Login;

import com.technology.yuyidoctorpad.activity.Login.Bean.BeanDoc;
import com.technology.yuyidoctorpad.activity.Login.Bean.BeanHosLogin;

/**
 * Created by wanyu on 2017/11/13.
 */

public interface ILogin {
    void onError(String msg);
    void onLoginSuccess( BeanDoc beanDoc);
    void onHospitalLoginSuccess(BeanHosLogin bean);
    void getSMSCodeSuccess();
    void getSMSCodeError(String msg);
    void getTimeOut(int count);
}
