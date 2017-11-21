package com.technology.yuyidoctorpad.lhdUtils;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;

import java.io.File;


/**
 * Created by wanyu on 2017/10/9.
 */

public class UserInfoPresenter implements View.OnClickListener{
    Fragment activity;
    PopupWindow pop;
    File outImage;
    //弹窗选择
    public void showWindow(Fragment acs,View parentView,File out){
        this.outImage=out;
        this.activity=acs;
        View vi= LayoutInflater.from(acs.getActivity()).inflate(R.layout.pop_photoselect,null);
        LinearLayout usereditor_pop_layout= (LinearLayout) vi.findViewById(R.id.usereditor_pop_layout);
        TextView usereditor_textv_cancle= (TextView) vi.findViewById(R.id.usereditor_textv_cancle);
        TextView usereditor_textv_picture= (TextView) vi.findViewById(R.id.usereditor_textv_picture);
        TextView usereditor_textv_camera= (TextView) vi.findViewById(R.id.usereditor_textv_camera);
        usereditor_textv_cancle.setOnClickListener(this);
        usereditor_textv_camera.setOnClickListener(this);
        usereditor_textv_picture.setOnClickListener(this);
        if (pop==null){
            pop=new PopupWindow();
        }
        PopupSettings.getInstance().showWindowCenter(activity.getActivity(),pop,vi,parentView);
        ViewGroup.LayoutParams param=usereditor_pop_layout.getLayoutParams();
        param.width=(int)(activity.getActivity().getWindowManager().getDefaultDisplay().getWidth()*0.7);
        usereditor_pop_layout.setLayoutParams(param);
    }




    @Override
    public void onClick(View view) {
        if (pop!=null&&pop.isShowing()){
            pop.dismiss();
        }
        switch (view.getId()){
            case R.id.usereditor_textv_picture://图库选取头像
                PicturePhotoUtils.getInstance().searchPhtoFrag(activity,outImage);
                break;
            case R.id.usereditor_textv_camera://拍照头像
                PicturePhotoUtils.getInstance().takePhotoFrag(activity,outImage);
                break;
            case R.id.usereditor_textv_cancle://取消
                break;
        }
    }
}
