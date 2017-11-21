package com.technology.yuyidoctorpad.bean.DepartmentBean;

/**
 * Created by liuhaidong on 2017/11/15.
 */

public class ClinicList {
    private String clinicName;

    private String createTimeString;

    private String updateTimeString;

    private int departmentId;

    private int id;

    private int oid;

    public void setClinicName(String clinicName){
        this.clinicName = clinicName;
    }
    public String getClinicName(){
        return this.clinicName;
    }
    public void setCreateTimeString(String createTimeString){
        this.createTimeString = createTimeString;
    }
    public String getCreateTimeString(){
        return this.createTimeString;
    }
    public void setUpdateTimeString(String updateTimeString){
        this.updateTimeString = updateTimeString;
    }
    public String getUpdateTimeString(){
        return this.updateTimeString;
    }
    public void setDepartmentId(int departmentId){
        this.departmentId = departmentId;
    }
    public int getDepartmentId(){
        return this.departmentId;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setOid(int oid){
        this.oid = oid;
    }
    public int getOid(){
        return this.oid;
    }
}
