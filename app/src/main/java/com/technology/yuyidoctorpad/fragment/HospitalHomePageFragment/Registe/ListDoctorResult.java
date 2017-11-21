package com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Registe;

import java.sql.Timestamp;
import java.util.List;


/**
 * Created by wanyu on 2017/11/21.
 */

public class ListDoctorResult {
    public List<ResultBean> getList() {
        return list;
    }

    public void setList(List<ResultBean> list) {
        this.list = list;
    }

    List<ListDoctorResult.ResultBean>list;
   public static  class ResultBean{

       public String getPhysicianId() {
           return physicianId;
       }

       public void setPhysicianId(String physicianId) {
           this.physicianId = physicianId;
       }

       public int getAfterNum() {
           return afterNum;
       }

       public void setAfterNum(int afterNum) {
           this.afterNum = afterNum;
       }

       public int getBeforNum() {
           return beforNum;
       }

       public void setBeforNum(int beforNum) {
           this.beforNum = beforNum;
       }

        private String physicianId;
        private int afterNum;
        private int beforNum;

       public Timestamp getDate() {
           return date;
       }

       public void setDate(Timestamp date) {
           this.date = date;
       }

       Timestamp date;
    }
}
