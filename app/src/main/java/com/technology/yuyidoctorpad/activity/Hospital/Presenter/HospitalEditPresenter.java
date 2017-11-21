package com.technology.yuyidoctorpad.activity.Hospital.Presenter;

import com.technology.yuyidoctorpad.activity.Hospital.Listener.Iedit;
import com.technology.yuyidoctorpad.activity.Hospital.Model.HospitalEditModel;

/**
 * Created by wanyu on 2017/11/17.
 */

public class HospitalEditPresenter {
    HospitalEditModel model;
    public void submit(String DeleteClinicId,String AddClinicName,String DepartmentId,String departmentName, Iedit listener){
            if (model==null){
                model=new HospitalEditModel();
            }
            model.submit(DeleteClinicId,AddClinicName,DepartmentId,departmentName,listener);
    }
}
