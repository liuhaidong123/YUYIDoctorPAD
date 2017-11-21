package com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Registe;

import com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Depart.IDepart;

/**
 * Created by wanyu on 2017/11/18.
 */

public class RegisterPresenter {
    RegisterModel model;
    //获取所有科室信息
    public void getAllDepartment(IDepart lis){
        if (model==null){
            model=new RegisterModel();
        }
        model.getDepartmentData(lis);
    }
    public void getDoctorList(String departId,IRegister iRegister,int start,int limit,String data){
        if (model==null){
            model=new RegisterModel();
        }
        model.getDoctorList(departId,iRegister,start,limit,data);
    }

    public void submit(ListDoctorResult bean,IRegister lis){
        model.submit(bean,lis);
    }
}
