package com.technology.yuyidoctorpad.lhdUtils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.technology.yuyidoctorpad.R;


/**
 * Created by wanyu on 2017/3/17.
 */

public class MyDialog {
    public static Dialog dialog;
    public static void showDialog(Context context){
        if (dialog!=null&&dialog.isShowing()){
            return;
        }
        if (context!=null){
//            ImageView imageView= (ImageView) v.findViewById(R.id.dialogimg);
//            imageView.setBackgroundResource(R.drawable.animt);
//            AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
//
//            animationDrawable.start();
            dialog=new Dialog(context, R.style.dialog);
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.window, null);
            dialog.setContentView(v);
            dialog.show();
        }
    }
    public static void  stopDia(){
        if(dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
