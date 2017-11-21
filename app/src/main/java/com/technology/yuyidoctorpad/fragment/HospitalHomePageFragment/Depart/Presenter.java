package com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Depart;

/**
 * Created by wanyu on 2017/11/15.
 */

public class Presenter {
    DepartModel model;
    public void getDepartmentData(IDepart listener){
        if (model==null){
            model=new DepartModel();
        }
    model.getDepartmentData(listener);
    }
}
