package com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Depart;

import com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Depart.Bean.DepartmentBean;

import java.util.List;

/**
 * Created by wanyu on 2017/11/15.
 */

public interface IDepart {
    void onGetDrpartError(String msg);
    void onGetDrpartSuccess(List<DepartmentBean.ResultBean> list);
}
