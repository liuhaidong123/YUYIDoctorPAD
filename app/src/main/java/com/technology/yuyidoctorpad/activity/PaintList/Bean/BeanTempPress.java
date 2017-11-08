package com.technology.yuyidoctorpad.activity.PaintList.Bean;

import java.util.List;

/**
 * Created by wanyu on 2017/11/6.
 */
//体温血压数据
public class BeanTempPress {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * result : {"createTimeString":"","role":0,"updateTimeString":"","gender":1,"nickName":"","groupId":18,"telephone":18301264693,"avatar":"/static/image/avatar.jpeg","oid":null,"bloodpressureList":[{"createTimeString":"2017-10-24 17:07:21","diastolic":89,"systolic":120,"updateTimeString":"","humeuserId":116,"id":219}],"trueName":"刘大东","marital":null,"id":116,"temperatureList":[{"createTimeString":"2017-08-28 13:37:31","updateTimeString":"","humeuserId":116,"id":127,"temperaturet":37}],"age":26}
     * code : 0
     */
    String message;
    private ResultBean result;
    private String code;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class ResultBean {
        /**
         * createTimeString :
         * role : 0
         * updateTimeString :
         * gender : 1
         * nickName :
         * groupId : 18
         * telephone : 18301264693
         * avatar : /static/image/avatar.jpeg
         * oid : null
         * bloodpressureList : [{"createTimeString":"2017-10-24 17:07:21","diastolic":89,"systolic":120,"updateTimeString":"","humeuserId":116,"id":219}]
         * trueName : 刘大东
         * marital : null
         * id : 116
         * temperatureList : [{"createTimeString":"2017-08-28 13:37:31","updateTimeString":"","humeuserId":116,"id":127,"temperaturet":37}]
         * age : 26
         */

        private String createTimeString;
        private int role;
        private String updateTimeString;
        private int gender;
        private String nickName;
        private int groupId;
        private long telephone;
        private String avatar;
        private Object oid;
        private String trueName;
        private Object marital;
        private int id;
        private int age;
        private List<BloodpressureListBean> bloodpressureList;
        private List<TemperatureListBean> temperatureList;

        public String getCreateTimeString() {
            return createTimeString;
        }

        public void setCreateTimeString(String createTimeString) {
            this.createTimeString = createTimeString;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public String getUpdateTimeString() {
            return updateTimeString;
        }

        public void setUpdateTimeString(String updateTimeString) {
            this.updateTimeString = updateTimeString;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
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

        public Object getOid() {
            return oid;
        }

        public void setOid(Object oid) {
            this.oid = oid;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public Object getMarital() {
            return marital;
        }

        public void setMarital(Object marital) {
            this.marital = marital;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public List<BloodpressureListBean> getBloodpressureList() {
            return bloodpressureList;
        }

        public void setBloodpressureList(List<BloodpressureListBean> bloodpressureList) {
            this.bloodpressureList = bloodpressureList;
        }

        public List<TemperatureListBean> getTemperatureList() {
            return temperatureList;
        }

        public void setTemperatureList(List<TemperatureListBean> temperatureList) {
            this.temperatureList = temperatureList;
        }

        public static class BloodpressureListBean {
            /**
             * createTimeString : 2017-10-24 17:07:21
             * diastolic : 89
             * systolic : 120
             * updateTimeString :
             * humeuserId : 116
             * id : 219
             */

            private String createTimeString;
            private int diastolic;
            private int systolic;
            private String updateTimeString;
            private int humeuserId;
            private int id;

            public String getCreateTimeString() {
                return createTimeString;
            }

            public void setCreateTimeString(String createTimeString) {
                this.createTimeString = createTimeString;
            }

            public int getDiastolic() {
                return diastolic;
            }

            public void setDiastolic(int diastolic) {
                this.diastolic = diastolic;
            }

            public int getSystolic() {
                return systolic;
            }

            public void setSystolic(int systolic) {
                this.systolic = systolic;
            }

            public String getUpdateTimeString() {
                return updateTimeString;
            }

            public void setUpdateTimeString(String updateTimeString) {
                this.updateTimeString = updateTimeString;
            }

            public int getHumeuserId() {
                return humeuserId;
            }

            public void setHumeuserId(int humeuserId) {
                this.humeuserId = humeuserId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }

        public static class TemperatureListBean {
            /**
             * createTimeString : 2017-08-28 13:37:31
             * updateTimeString :
             * humeuserId : 116
             * id : 127
             * temperaturet : 37
             */

            private String createTimeString;
            private String updateTimeString;
            private int humeuserId;
            private int id;
            private int temperaturet;

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

            public int getHumeuserId() {
                return humeuserId;
            }

            public void setHumeuserId(int humeuserId) {
                this.humeuserId = humeuserId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getTemperaturet() {
                return temperaturet;
            }

            public void setTemperaturet(int temperaturet) {
                this.temperaturet = temperaturet;
            }
        }
    }
}
