package com.technology.yuyidoctorpad.activity.Login.Bean;

/**
 * Created by wanyu on 2017/11/13.
 */

public class BeanDoc {

    /**
     * result : 62C9F78E089F99C1EC893061F65EC882
     * code : 0
     * hasMessage : true
     * physician : {"clinicName":"呼吸内科门诊","clinicId":1,"createTimeString":"","permissionRead":true,"departmentId":2,"oid":null,"title":"","permissionRegister":true,"trueName":"张医生","hospitalId":1,"permissionData":true,"id":35,"permissionInfo":true,"departmentName":"妇产科","updateTimeString":"","telephone":17743516301,"avatar":"/static/image/avatar.jpeg","hospitalName":"涿州市中医医院","token":""}
     */

    private String result;
    private String code;
    private boolean hasMessage;
    private PhysicianBean physician;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message;
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isHasMessage() {
        return hasMessage;
    }

    public void setHasMessage(boolean hasMessage) {
        this.hasMessage = hasMessage;
    }

    public PhysicianBean getPhysician() {
        return physician;
    }

    public void setPhysician(PhysicianBean physician) {
        this.physician = physician;
    }

    public static class PhysicianBean {
        /**
         * clinicName : 呼吸内科门诊
         * clinicId : 1
         * createTimeString :
         * permissionRead : true
         * departmentId : 2
         * oid : null
         * title :
         * permissionRegister : true
         * trueName : 张医生
         * hospitalId : 1
         * permissionData : true
         * id : 35
         * permissionInfo : true
         * departmentName : 妇产科
         * updateTimeString :
         * telephone : 17743516301
         * avatar : /static/image/avatar.jpeg
         * hospitalName : 涿州市中医医院
         * token :
         */

        private String clinicName;
        private int clinicId;
        private String createTimeString;
        private boolean permissionRead;
        private int departmentId;
        private Object oid;
        private String title;
        private boolean permissionRegister;
        private String trueName;
        private int hospitalId;
        private boolean permissionData;
        private int id;
        private boolean permissionInfo;
        private String departmentName;
        private String updateTimeString;
        private long telephone;
        private String avatar;
        private String hospitalName;
        private String token;

        public String getClinicName() {
            return clinicName;
        }

        public void setClinicName(String clinicName) {
            this.clinicName = clinicName;
        }

        public int getClinicId() {
            return clinicId;
        }

        public void setClinicId(int clinicId) {
            this.clinicId = clinicId;
        }

        public String getCreateTimeString() {
            return createTimeString;
        }

        public void setCreateTimeString(String createTimeString) {
            this.createTimeString = createTimeString;
        }

        public boolean isPermissionRead() {
            return permissionRead;
        }

        public void setPermissionRead(boolean permissionRead) {
            this.permissionRead = permissionRead;
        }

        public int getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(int departmentId) {
            this.departmentId = departmentId;
        }

        public Object getOid() {
            return oid;
        }

        public void setOid(Object oid) {
            this.oid = oid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isPermissionRegister() {
            return permissionRegister;
        }

        public void setPermissionRegister(boolean permissionRegister) {
            this.permissionRegister = permissionRegister;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public int getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(int hospitalId) {
            this.hospitalId = hospitalId;
        }

        public boolean isPermissionData() {
            return permissionData;
        }

        public void setPermissionData(boolean permissionData) {
            this.permissionData = permissionData;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isPermissionInfo() {
            return permissionInfo;
        }

        public void setPermissionInfo(boolean permissionInfo) {
            this.permissionInfo = permissionInfo;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public String getUpdateTimeString() {
            return updateTimeString;
        }

        public void setUpdateTimeString(String updateTimeString) {
            this.updateTimeString = updateTimeString;
        }

        public long getTelephone() {
            return telephone;
        }

        public void setTelephone(long telephone) {
            this.telephone = telephone;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getHospitalName() {
            return hospitalName;
        }

        public void setHospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
