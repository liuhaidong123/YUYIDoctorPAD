package com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Registe;

import java.util.List;

/**
 * Created by wanyu on 2017/11/20.
 */

public class DoctorListBean {
    private List<ResultBean> result;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    String code;



    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        private String date;

        public boolean isCanSelect() {
            return canSelect;
        }

        public void setCanSelect(boolean canSelect) {
            this.canSelect = canSelect;
        }

        /**
         * departmentName : 内科
         * clinicId : null
         * createTimeString :
         * updateTimeString :
         * datestr :
         * departmentId : 1
         * beforNum : 7
         * dateString :
         * telephone : null
         * avatar : /static/image/L3.jpeg
         * title : 主治医生
         * afterNum : 6
         * trueName : 华佗
         * physicianId : null
         * hospitalId : null
         * id : 3
         */
        boolean canSelect;
        private String departmentName;
        private Object clinicId;
        private String createTimeString;
        private String updateTimeString;
        private String datestr;
        private int departmentId;
        private int beforNum;
        private String dateString;
        private Object telephone;
        private String avatar;
        private String title;
        private int afterNum;
        private String trueName;
        private String physicianId;
        private String hospitalId;
        private int id;

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

        public String getDatestr() {
            return datestr;
        }

        public void setDatestr(String datestr) {
            this.datestr = datestr;
        }

        public int getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(int departmentId) {
            this.departmentId = departmentId;
        }

        public int getBeforNum() {
            return beforNum;
        }

        public void setBeforNum(int beforNum) {
            this.beforNum = beforNum;
        }

        public String getDateString() {
            return dateString;
        }

        public void setDateString(String dateString) {
            this.dateString = dateString;
        }

        public Object getTelephone() {
            return telephone;
        }

        public void setTelephone(Object telephone) {
            this.telephone = telephone;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getAfterNum() {
            return afterNum;
        }

        public void setAfterNum(int afterNum) {
            this.afterNum = afterNum;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public String getPhysicianId() {
            return physicianId;
        }

        public void setPhysicianId(String physicianId) {
            this.physicianId = physicianId;
        }

        public String getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(String hospitalId) {
            this.hospitalId = hospitalId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
