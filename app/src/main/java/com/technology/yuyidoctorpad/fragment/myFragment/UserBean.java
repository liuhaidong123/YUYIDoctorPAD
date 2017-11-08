package com.technology.yuyidoctorpad.fragment.myFragment;

import java.io.Serializable;

/**
 * Created by wanyu on 2017/11/7.
 */
//用户实体类
public class UserBean implements Serializable{

    /**
     * result : 55571124891717A9FFDD698F8B2824E9
     * code : 0
     * hasMessage : false
     * physician : {"clinicName":"","departmentName":"","clinicId":null,"createTimeString":"","updateTimeString":"","departmentId":null,"telephone":17743516301,"avatar":"","hospitalName":"","oid":null,"hospital_id":null,"title":"","token":"","trueName":"","id":35}
     */

    private String result;
    private String code;
    private boolean hasMessage;
    private PhysicianBean physician;

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

    public static class PhysicianBean implements Serializable {
        /**
         * clinicName :
         * departmentName :
         * clinicId : null
         * createTimeString :
         * updateTimeString :
         * departmentId : null
         * telephone : 17743516301
         * avatar :
         * hospitalName :
         * oid : null
         * hospital_id : null
         * title :
         * token :
         * trueName :
         * id : 35
         */

        private String clinicName;
        private String departmentName;
        private Object clinicId;
        private String createTimeString;
        private String updateTimeString;
        private Object departmentId;
        private long telephone;
        private String avatar;
        private String hospitalName;
        private Object oid;
        private Object hospital_id;
        private String title;
        private String token;
        private String trueName;
        private Long id;

        public String getClinicName() {
            return clinicName;
        }

        public void setClinicName(String clinicName) {
            this.clinicName = clinicName;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public Object getClinicId() {
            return clinicId;
        }

        public void setClinicId(Object clinicId) {
            this.clinicId = clinicId;
        }

        public String getCreateTimeString() {
            return createTimeString;
        }

        public void setCreateTimeString(String createTimeString) {
            this.createTimeString = createTimeString;
        }

        public String getUpdateTimeString() {
            return updateTimeString;
        }

        public void setUpdateTimeString(String updateTimeString) {
            this.updateTimeString = updateTimeString;
        }

        public Object getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(Object departmentId) {
            this.departmentId = departmentId;
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

        public Object getOid() {
            return oid;
        }

        public void setOid(Object oid) {
            this.oid = oid;
        }

        public Object getHospital_id() {
            return hospital_id;
        }

        public void setHospital_id(Object hospital_id) {
            this.hospital_id = hospital_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}
