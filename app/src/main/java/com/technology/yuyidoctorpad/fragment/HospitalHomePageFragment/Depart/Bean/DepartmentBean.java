package com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Depart.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanyu on 2017/11/15.
 */

public class DepartmentBean {

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * result : [{"departmentName":"男科","createTimeString":"","updateTimeString":"","oid":7,"clinicList":[{"clinicName":"男科门诊","createTimeString":"","updateTimeString":"","departmentId":7,"id":14,"oid":0}],"hospitalId":2,"id":7,"open":true},{"departmentName":"神经科","createTimeString":"","updateTimeString":"","oid":8,"clinicList":[{"clinicName":"脑神经门诊","createTimeString":"","updateTimeString":"","departmentId":8,"id":13,"oid":0}],"hospitalId":2,"id":8,"open":false},{"departmentName":"内科","createTimeString":"","updateTimeString":"","oid":9,"clinicList":[{"clinicName":"内科门诊","createTimeString":"","updateTimeString":"","departmentId":9,"id":15,"oid":0}],"hospitalId":2,"id":9,"open":false},{"departmentName":"骨科","createTimeString":"","updateTimeString":"","oid":10,"clinicList":[{"clinicName":"骨科门诊","createTimeString":"","updateTimeString":"","departmentId":10,"id":16,"oid":0}],"hospitalId":2,"id":10,"open":false}]
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

    public static class ResultBean implements Serializable{
        public boolean isCanOpen() {
            return canOpen;
        }

        public void setCanOpen(boolean canOpen) {
            this.canOpen = canOpen;
        }

        /**
         * departmentName : 男科
         * createTimeString :
         * updateTimeString :
         * oid : 7
         * clinicList : [{"clinicName":"男科门诊","createTimeString":"","updateTimeString":"","departmentId":7,"id":14,"oid":0}]
         * hospitalId : 2
         * id : 7
         * open : true
         */
        private boolean canOpen;//是否处于编辑状态
        private String departmentName;
        private String createTimeString;
        private String updateTimeString;
        private int oid;
        private int hospitalId;
        private int id;
        private boolean open;

        private List<ClinicListBean> clinicList;

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

        public int getOid() {
            return oid;
        }

        public void setOid(int oid) {
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

        public List<ClinicListBean> getClinicList() {
            return clinicList;
        }

        public void setClinicList(List<ClinicListBean> clinicList) {
            this.clinicList = clinicList;
        }

        public static class ClinicListBean implements Serializable{
            public boolean isLast() {
                return isLast;
            }

            public void setLast(boolean last) {
                isLast = last;
            }

            public boolean isCanNotEdit() {
                return canNotEdit;
            }

            public void setCanNotEdit(boolean canNotEdit) {
                this.canNotEdit = canNotEdit;
            }

            /**
             * clinicName : 男科门诊
             * createTimeString :
             * updateTimeString :
             * departmentId : 7
             * id : 14
             * oid : 0
             */
            boolean isLast;
            boolean canNotEdit;
            private String clinicName;
            private String createTimeString;
            private String updateTimeString;
            private int departmentId;
            private int id;
            private int oid;

            public boolean isHashWord() {
                return hashWord;
            }

            public void setHashWord(boolean hashWord) {
                this.hashWord = hashWord;
            }

            boolean hashWord;//是否有名字
            public boolean isNew() {
                return isNew;
            }

            public void setNew(boolean aNew) {
                isNew = aNew;
            }

            boolean isNew;//是否新增
            public String getClinicName() {
                return clinicName;
            }

            public void setClinicName(String clinicName) {
                this.clinicName = clinicName;
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

            public int getDepartmentId() {
                return departmentId;
            }

            public void setDepartmentId(int departmentId) {
                this.departmentId = departmentId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getOid() {
                return oid;
            }

            public void setOid(int oid) {
                this.oid = oid;
            }
        }
    }
}
