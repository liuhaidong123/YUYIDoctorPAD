package com.technology.yuyidoctorpad.activity.PaintList.Model;

import com.technology.yuyidoctorpad.activity.PaintList.Bean.bean_MedicalRecordList;

import java.util.List;

/**
 * Created by wanyu on 2017/11/3.
 */
//电子病历
public interface IEle {
    void onSuccess(List<bean_MedicalRecordList.ResultBean>list);
    void onError(String msg);
}
