package com.technology.yuyidoctorpad.activity.Hospital.Bean;

import java.util.List;

/**
 * Created by wanyu on 2017/11/16.
 */
//科室实体类
public class DepartBean {
    //date formats

    //columns START
    //科室编号
    private Long id;
    //医院编号--医院表
    private Long hospitalId;
    //科室名称
    private String departmentName;
    //序号
    private Integer oid;
    //columns END
    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public boolean getIsErroPart() {
        return isErroPart;
    }

    public void setIsErroPart(boolean isErroPart) {
        this.isErroPart = isErroPart;
    }

    boolean isErroPart;//是否没有添加成功true：没有添加陈工，false添加成功（默认false）
    boolean isLast;
    //科室下的门诊列表
    private List<Clinic> ClinicList;
    //是否默认展开
    private boolean open = false;

    public boolean isCanNotOpen() {
        return canNotOpen;
    }

    public void setCanNotOpen(boolean canNotOpen) {
        this.canNotOpen = canNotOpen;
    }

    boolean canNotOpen;//是否不可编辑
    public boolean isHashWord() {
        return hashWord;
    }

    public void setHashWord(boolean hashWord) {
        this.hashWord = hashWord;
    }

    boolean hashWord=false;//是否有值

    public DepartBean(){
    }
    /**
     * 科室编号
     * @return
     */
    public Long getId() {
        return this.id;
    }

    /**
     * 科室编号
     * @param value
     */
    public void setId(Long value) {
        this.id = value;
    }
    /**
     * 医院编号--医院表
     * @return
     */
    public Long getHospitalId() {
        return this.hospitalId;
    }

    /**
     * 医院编号--医院表
     * @param value
     */
    public void setHospitalId(Long value) {
        this.hospitalId = value;
    }
    /**
     * 科室名称
     * @return
     */
    public String getDepartmentName() {
        return this.departmentName;
    }

    /**
     * 科室名称
     * @param value
     */
    public void setDepartmentName(String value) {
        this.departmentName = value;
    }
    /**
     * 序号
     * @return
     */
    public Integer getOid() {
        return this.oid;
    }

    /**
     * 序号
     * @param value
     */
    public void setOid(Integer value) {
        this.oid = value;
    }

    public List<Clinic> getClinicList() {
        return ClinicList;
    }

    public void setClinicList(List<Clinic> clinicList) {
        ClinicList = clinicList;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
    public static   class Clinic  {

        //date formats

        //columns START
        //门诊编号
        private Long id;
        //科室编号--医院——科室表--外键
        private Long departmentId;
        //门诊名称
        private String clinicName;
        //序号
        private Integer oid;

        public boolean isLast() {
            return isLast;
        }

        public void setLast(boolean last) {
            isLast = last;
        }

        boolean isLast;
        public boolean isHashWord() {
            return hashWord;
        }

        public void setHashWord(boolean hashWord) {
            this.hashWord = hashWord;
        }

        boolean hashWord=false;//是否有值
        public Clinic(){

        }


        /**
         * 门诊编号
         * @return
         */
        public Long getId() {
            return this.id;
        }

        /**
         * 门诊编号
         * @param value
         */
        public void setId(Long value) {
            this.id = value;
        }
        /**
         * 科室编号--医院——科室表--外键
         * @return
         */
        public Long getDepartmentId() {
            return this.departmentId;
        }

        /**
         * 科室编号--医院——科室表--外键
         * @param value
         */
        public void setDepartmentId(Long value) {
            this.departmentId = value;
        }
        /**
         * 门诊名称
         * @return
         */
        public String getClinicName() {
            return this.clinicName;
        }

        /**
         * 门诊名称
         * @param value
         */
        public void setClinicName(String value) {
            this.clinicName = value;
        }
        /**
         * 序号
         * @return
         */
        public Integer getOid() {
            return this.oid;
        }

        /**
         * 序号
         * @param value
         */
        public void setOid(Integer value) {
            this.oid = value;
        }
    }

}
