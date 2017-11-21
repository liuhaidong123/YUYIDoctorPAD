package com.technology.yuyidoctorpad.bean.DepartmentBean;

import java.util.List;

/**
 * Created by liuhaidong on 2017/11/15.
 */

public class Result {
    private String departmentName;

    private String createTimeString;

    private String updateTimeString;

    private int oid;

    private List<ClinicList> clinicList;

    private int hospitalId;

    private int id;

    private boolean open;

    public void setDepartmentName(String departmentName){
        this.departmentName = departmentName;
    }
    public String getDepartmentName(){
        return this.departmentName;
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
    public void setOid(int oid){
        this.oid = oid;
    }
    public int getOid(){
        return this.oid;
    }
    public void setClinicList(List<ClinicList> clinicList){
        this.clinicList = clinicList;
    }
    public List<ClinicList> getClinicList(){
        return this.clinicList;
    }
    public void setHospitalId(int hospitalId){
        this.hospitalId = hospitalId;
    }
    public int getHospitalId(){
        return this.hospitalId;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setOpen(boolean open){
        this.open = open;
    }
    public boolean getOpen(){
        return this.open;
    }

    public Result(String departmentName) {
        this.departmentName = departmentName;
    }
}
