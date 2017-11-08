package com.technology.yuyidoctorpad.activity.PaintList.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.activity.PaintList.Bean.BeanTempPress;
import com.technology.yuyidoctorpad.activity.PaintList.Model.DateModel;
import com.technology.yuyidoctorpad.activity.PaintList.Model.IDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wanyu on 2017/11/3.
 */

public class DatePresenter {
    DateModel model;
    PressView pView;
    TempView tView;
    ViewGroup vGroup;
    Context con;
    View p,t;
    int[] imageResid={R.mipmap.a_unmesure,R.mipmap.a_low,R.mipmap.a_normal,R.mipmap.a_high,R.mipmap.a_error};//待测，偏低，正常，偏高，异常
    public DatePresenter(Context con,ViewGroup vGroup){
            this.vGroup=vGroup;
            this.con=con;
            //血压
            p= LayoutInflater.from(con).inflate(R.layout.press,null);
            pView=new PressView(p);
            //体温
            t=LayoutInflater.from(con).inflate(R.layout.temp,null);
            tView=new TempView(t);

            vGroup.addView(p);
    }
    //切换体温与血压
    public void showView(Type tp){
        if (vGroup.getChildCount()!=0){
            vGroup.removeViewAt(0);
        }
        switch (tp){
            case TEMP:
                vGroup.addView(t);
                break;
            case PRESS:
                vGroup.addView(p);
                break;
        }
    }

    //获取体温，血压数据
    public void getPressTemp(String id,IDate iDate){
        if (model==null){
            model=new DateModel();
        }
        model.getDate(id,iDate);
    }

    //设置数据
    public void setDate(BeanTempPress bean){
        BeanTempPress.ResultBean result= bean.getResult();
        if (result!=null){
            //最后一条数据的高压，低压，体温值（用于页面显示）
            float HighPress=0.0f;
            float LowPress=0.0f;
            float Temp=0.0f;
            List<BeanTempPress.ResultBean.BloodpressureListBean> listBlood=result.getBloodpressureList();//血压
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
            //处理血压中的数据
            if (listBlood!=null&&listBlood.size()>0){
                LowPress=listBlood.get(listBlood.size()-1).getDiastolic();
                HighPress=listBlood.get(listBlood.size()-1).getSystolic();
                List<String>listTime=new ArrayList<>();//血压测量的日期的集合
                List<Float>listSource=new ArrayList<>();//高压集合值
                List<Float>listOhtherSource=new ArrayList<>();//低压集合值
                for (int i=0;i<listBlood.size();i++){
                    try{
                        String dstr=listBlood.get(i).getCreateTimeString();
                        Date date=sdf.parse(dstr);
                        SimpleDateFormat format=new SimpleDateFormat("MM月dd日");
                        String time=format.format(date);
                        listTime.add(time);
                        listSource.add(Float.parseFloat(listBlood.get(i).getSystolic()+""));//高压
                        listOhtherSource.add(Float.parseFloat(listBlood.get(i).getDiastolic()+""));//低压
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if (listTime.size()<7){//补全不足7天的日期
                    try{
                        int tp=7-listTime.size();
                        SimpleDateFormat format=new SimpleDateFormat("MM月dd日");
                        Calendar c = Calendar.getInstance();
                        String dstr=listBlood.get(listBlood.size()-1).getCreateTimeString();
                        Date date=sdf.parse(dstr);
                        c.setTime(date);//以最后一个测量日期为准加够7天
                        for (int i=0;i<tp;i++){//填充日期，最后一个日期加1天
                            c.add(Calendar.DAY_OF_MONTH,1);
                            listTime.add(format.format(c.getTime()));
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if (listSource.size()<7){//补全不足7天的高压
                    int tp=7-listSource.size();
                    for (int i=0;i<tp;i++){
                        listSource.add(-1f);
                    }
                }
                if (listOhtherSource.size()<7){//补全不足7天的低压
                    int tp=7-listOhtherSource.size();
                    for (int i=0;i<tp;i++){
                        listOhtherSource.add(-1f);
                    }
                }
               pView.setDate(listTime,listSource,listOhtherSource,HighPress,LowPress);
            }
            else {
                pView.setDate(null,null,null,0.0f,0.0f);
                }
            //处理温度的中的数据
            List<BeanTempPress.ResultBean.TemperatureListBean>listTemp=result.getTemperatureList();//温度
            if (listTemp!=null&&listTemp.size()>0){
                Temp=listTemp.get(listTemp.size()-1).getTemperaturet();
                List<String>listTime=new ArrayList<>();//测量的日期
                List<Float>listSource=new ArrayList<>();//测量到的血压数据
                for (int i=0;i<listTemp.size();i++){
                    try{
                        String dstr=listTemp.get(i).getCreateTimeString();
                        Date date=sdf.parse(dstr);
                        SimpleDateFormat format=new SimpleDateFormat("MM月dd日");
                        String time=format.format(date);
                        listTime.add(time);
                        listSource.add(Float.parseFloat(listTemp.get(i).getTemperaturet()+""));
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }
                if (listTime!=null&&listTime.size()<7){//补全不足7天的日期
                    try{
                        int tp=7-listTime.size();
                        SimpleDateFormat format=new SimpleDateFormat("MM月dd日");
                        Calendar c = Calendar.getInstance();
                        String dstr=listTemp.get(listTemp.size()-1).getCreateTimeString();
                        Date date=sdf.parse(dstr);
                        c.setTime(date);//以最后一个测量日期为准加够7天
                        for (int i=0;i<tp;i++){//填充日期，最后一个日期加1天
                            c.add(Calendar.DAY_OF_MONTH,1);
                            listTime.add(format.format(c.getTime()));
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    try{
                        int tp=7-listTime.size();
                        SimpleDateFormat format=new SimpleDateFormat("MM月dd日");
                        Calendar c = Calendar.getInstance();
                        String dstr=listTemp.get(listTemp.size()-1).getCreateTimeString();
                        Date date=sdf.parse(dstr);
                        c.setTime(date);//以最后一个测量日期为准加够7天
                        for (int i=0;i<tp;i++){//填充日期，最后一个日期加1天
                            c.add(Calendar.DAY_OF_MONTH,1);
                            listTime.add(format.format(c.getTime()));
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if (listSource!=null&&listSource.size()<7){//补全不足7天的体温
                    int tp=7-listSource.size();
                    for (int i=0;i<tp;i++){
                        listSource.add(-1f);//-1f表示当天没有数据
                    }
                }
                tView.setDate(listTime,listSource,Temp);
            }
            else {
                tView.setDate(null,null,0.0f);
            }
        }
        else {
            Log.e("error","获取到的体温与血压数据不正确，DateFragment中的Preenter控制器setDate方法");
        }
    }
    public enum Type{
        TEMP,PRESS  //温度、血压
    }
}
