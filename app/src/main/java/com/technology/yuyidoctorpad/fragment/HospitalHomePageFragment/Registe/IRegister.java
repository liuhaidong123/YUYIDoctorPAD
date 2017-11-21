package com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Registe;

/**
 * Created by wanyu on 2017/11/18.
 */

public interface IRegister {
    void onGetDocError(String msg);
    void onGetDocSuccess(DoctorListBean listBean);//获取医生信息成功
    void onRegisterError(String msg);//设置挂号成功
    void onRegisterSuccess();//设置挂号成功
}
