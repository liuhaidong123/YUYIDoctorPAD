package com.technology.yuyidoctorpad.activity.PaintList.Fragment;

import android.util.Log;

import com.technology.yuyidoctorpad.R;

/**
 * Created by wanyu on 2017/11/6.
 */
//处理得到的体温／血压的float转sting以及控制根据体温／血压数据返回正确的提示图片
public class TemPressUtils {
 static    TemPressUtils utils;
    private TemPressUtils(){}
    public static TemPressUtils getInstance(){
        if (utils==null){
            utils=new TemPressUtils();
        }
        return utils;
    }
    public int getInfoRes(DatePresenter.Type tp ,float highP, float lowP, float tem){
        int[] imageResid={R.mipmap.a_unmesure,R.mipmap.a_low,R.mipmap.a_normal,R.mipmap.a_high,R.mipmap.a_error};//待测，偏低，正常，偏高，异常
//        体温范围：36.1~37.2
//        血压范围：maxP收缩压／高压90~140，minP舒张压／低压60~90
        int pos=-1;
        switch (tp){
            case PRESS:
                if (lowP==0|highP==0){
                    pos=0;//待测
                }
                else if (lowP<60|highP<90){
                    pos=1;//偏低
                }
                else if (lowP>90|highP>140){
                    pos=3;//偏高
                }
                else if (lowP>=60&&lowP<=90&&highP>=90&&highP<=140){
                    pos=2;//正常
                }

                break;
            case TEMP:
                if (tem==0){
                    pos=0;//待测
                }
                else if (tem<=34){
                    pos=4;//异常
                }
                else if (tem>34&&tem<36.1){
                    pos=1;//偏低
                }
                else if (tem>=36.1&&tem<=37.2){
                    pos=2;//正常
                }
                else if (tem>37.2&&tem<=42){
                    pos=3;//偏高
                }
                else {
                    pos=4;//异常
                }
                break;
        }
        if (pos==-1){
            Log.e("getInfoRes错误","TemPressUtils：下表不正确");
            return imageResid[2];
        }
        return imageResid[pos];
    }


    public String getText(float tex){
        String text=tex+"";
        String tt=text.substring(0,text.indexOf("."));
        String last=text.substring(text.indexOf(".")+1,text.length());
        if ("0".equals(last)|"00".equals(last)){
            text=tt;
        }
        Log.i("text===",text);
        return text;
    }
}
