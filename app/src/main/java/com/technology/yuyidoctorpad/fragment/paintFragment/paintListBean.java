package com.technology.yuyidoctorpad.fragment.paintFragment;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanyu on 2017/11/3.
 */

public class paintListBean {
    /**
     * total : 1
     * rows : [{"gender":1,"telephone":18301264693,"avatar":"/static/image/201752/20adefc4206147a5ae80479c7a9fb45b.jpg","trueName":"刘大东","id":116,"age":26}]
     * colmodel : []
     */
    private int total;
    private List<RowsBean> rows;
    private List<?> colmodel;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public List<?> getColmodel() {
        return colmodel;
    }

    public void setColmodel(List<?> colmodel) {
        this.colmodel = colmodel;
    }

    public static class RowsBean implements Serializable{
        /**
         * gender : 1
         * telephone : 18301264693
         * avatar : /static/image/201752/20adefc4206147a5ae80479c7a9fb45b.jpg
         * trueName : 刘大东
         * id : 116
         * age : 26
         */

        private int gender;
        private long telephone;
        private String avatar;
        private String trueName;
        private int id;
        private int age;

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
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

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
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
    }
}
