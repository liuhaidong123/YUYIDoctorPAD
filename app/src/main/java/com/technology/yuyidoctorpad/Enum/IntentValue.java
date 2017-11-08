package com.technology.yuyidoctorpad.Enum;

/**
 * Created by wanyu on 2017/10/9.
 */
//定义所有intent的参数值
public interface IntentValue {
    String UserInfoActivity_Add="添加";//添加用户信息
    String UserInfoActivity_Change="修改";//修改用户信息
    String PaintMsg="患者信息";//患者列表跳转至患者详情（PatientFragment到PaintMsgActivity）
    String UserInfo="医生信息";//myFragment跳转到医生信息页面
}
