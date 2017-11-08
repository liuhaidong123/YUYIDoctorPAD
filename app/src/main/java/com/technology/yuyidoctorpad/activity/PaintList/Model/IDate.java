package com.technology.yuyidoctorpad.activity.PaintList.Model;

import com.technology.yuyidoctorpad.activity.PaintList.Bean.BeanTempPress;

/**
 * Created by wanyu on 2017/11/3.
 */

public interface IDate {
    void onError(String msg);
    void onSuccess(BeanTempPress bean);
}
