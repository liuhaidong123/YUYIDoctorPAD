package com.technology.yuyidoctorpad.activity.Hospital.Bean;

import java.util.List;

/**
 * Created by wanyu on 2017/11/17.
 */

public class DepartAddBean {

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * result : [{"departmentName":"a","createTimeString":"","updateTimeString":"","oid":null,"clinicList":[],"hospitalId":1,"id":21,"open":false}]
     * code : 0
     */
    String message;
    private String code;
    private List<ResultBean> result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * departmentName : a
         * createTimeString :
         * updateTimeString :
         * oid : null
         * clinicList : []
         * hospitalId : 1
         * id : 21
         * open : false
         */

        private String departmentName;
        private String createTimeString;
        private String updateTimeString;
        private Object oid;
        private int hospitalId;
        private int id;
        private boolean open;
        private List<?> clinicList;

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
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

        public Object getOid() {
            return oid;
        }

        public void setOid(Object oid) {
            this.oid = oid;
        }

        public int getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(int hospitalId) {
            this.hospitalId = hospitalId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isOpen() {
            return open;
        }

        public void setOpen(boolean open) {
            this.open = open;
        }

        public List<?> getClinicList() {
            return clinicList;
        }

        public void setClinicList(List<?> clinicList) {
            this.clinicList = clinicList;
        }
    }
}
