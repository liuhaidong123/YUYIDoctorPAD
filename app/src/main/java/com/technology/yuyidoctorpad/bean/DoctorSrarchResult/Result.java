package com.technology.yuyidoctorpad.bean.DoctorSrarchResult;

/**
 * Created by liuhaidong on 2017/11/17.
 */

public class Result {
    private String clinicName;

    private int clinicId;

    private String createTimeString;

    private boolean permissionRead;

    private int departmentId;

    private String oid;

    private String title;

    private boolean permissionRegister;

    private String trueName;

    private int hospitalId;

    private boolean permissionData;

    private long id;

    private boolean permissionInfo;

    private String departmentName;

    private String updateTimeString;

    private long telephone;

    private String avatar;

    private String hospitalName;

    private String token;

    public void setClinicName(String clinicName){
        this.clinicName = clinicName;
    }
    public String getClinicName(){
        return this.clinicName;
    }
    public void setClinicId(int clinicId){
        this.clinicId = clinicId;
    }
    public int getClinicId(){
        return this.clinicId;
    }
    public void setCreateTimeString(String createTimeString){
        this.createTimeString = createTimeString;
    }
    public String getCreateTimeString(){
        return this.createTimeString;
    }
    public void setPermissionRead(boolean permissionRead){
        this.permissionRead = permissionRead;
    }
    public boolean getPermissionRead(){
        return this.permissionRead;
    }
    public void setDepartmentId(int departmentId){
        this.departmentId = departmentId;
    }
    public int getDepartmentId(){
        return this.departmentId;
    }
    public void setOid(String oid){
        this.oid = oid;
    }
    public String getOid(){
        return this.oid;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setPermissionRegister(boolean permissionRegister){
        this.permissionRegister = permissionRegister;
    }
    public boolean getPermissionRegister(){
        return this.permissionRegister;
    }
    public void setTrueName(String trueName){
        this.trueName = trueName;
    }
    public String getTrueName(){
        return this.trueName;
    }
    public void setHospitalId(int hospitalId){
        this.hospitalId = hospitalId;
    }
    public int getHospitalId(){
        return this.hospitalId;
    }
    public void setPermissionData(boolean permissionData){
        this.permissionData = permissionData;
    }
    public boolean getPermissionData(){
        return this.permissionData;
    }
    public void setId(long id){
        this.id = id;
    }
    public long getId(){
        return this.id;
    }
    public void setPermissionInfo(boolean permissionInfo){
        this.permissionInfo = permissionInfo;
    }
    public boolean getPermissionInfo(){
        return this.permissionInfo;
    }
    public void setDepartmentName(String departmentName){
        this.departmentName = departmentName;
    }
    public String getDepartmentName(){
        return this.departmentName;
    }
    public void setUpdateTimeString(String updateTimeString){
        this.updateTimeString = updateTimeString;
    }
    public String getUpdateTimeString(){
        return this.updateTimeString;
    }
    public void setTelephone(long telephone){
        this.telephone = telephone;
    }
    public long getTelephone(){
        return this.telephone;
    }
    public void setAvatar(String avatar){
        this.avatar = avatar;
    }
    public String getAvatar(){
        return this.avatar;
    }
    public void setHospitalName(String hospitalName){
        this.hospitalName = hospitalName;
    }
    public String getHospitalName(){
        return this.hospitalName;
    }
    public void setToken(String token){
        this.token = token;
    }
    public String getToken(){
        return this.token;
    }
}
