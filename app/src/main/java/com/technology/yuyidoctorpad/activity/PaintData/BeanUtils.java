package com.technology.yuyidoctorpad.activity.PaintData;

import com.technology.yuyidoctorpad.Net.gson;
import com.technology.yuyidoctorpad.activity.PaintData.Bean.Bean_MyPaintList;
import com.technology.yuyidoctorpad.fragment.paintFragment.paintListBean;

/**
 * Created by wanyu on 2017/11/14.
 */

public class BeanUtils {
    public static paintListBean.RowsBean getBean(Bean_MyPaintList.RowsBean bean){
            paintListBean.RowsBean result=null;
        try{
            String str=gson.gson.toJson(bean);
            result=gson.gson.fromJson(str,paintListBean.RowsBean.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            return result;
        }


    }
}
