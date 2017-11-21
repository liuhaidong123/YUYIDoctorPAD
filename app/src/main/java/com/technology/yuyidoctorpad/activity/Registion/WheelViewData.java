package com.technology.yuyidoctorpad.activity.Registion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wanyu on 2017/9/4.
 */

public class WheelViewData {
   static WheelViewData data;
    private WheelViewData(){

    }
    public static WheelViewData getInstance(){
        if (data==null){
            data=new WheelViewData();
        }
        return data;
    }
    //获取当前月份的剩余日期包括当天
    public List<String> getTimeList(){
        List<String> li=new ArrayList<>();
        SimpleDateFormat format=new SimpleDateFormat();
        Date date=new Date();
        Calendar c= Calendar.getInstance();
        c.setTime(date);
        int max=c.getActualMaximum(Calendar.DAY_OF_MONTH);//获取总天数
        int current=date.getDate();//今天的日期
        for (int i=0;i<max-current+1;i++){
            li.add((current+i)+"日");
        }
        return li;
    }

    //返回今天以及之后的6天共7天的年月日
    public List<String>getDate(){
        List<String> li=new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = calendar.getTime();
        li.add(sdf.format(date));
        for (int i=0;i<6;i++){
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            date = calendar.getTime();
            li.add(sdf.format(date));
        }
        return li;
    }
}
