package com.technology.yuyidoctorpad.fragment.myFragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import com.technology.yuyidoctorpad.PermissionCheck.checkNotificationAllowed;
import com.technology.yuyidoctorpad.R;

/**
 * Created by wanyu on 2017/11/7.
 */

public class myPresenter {
    myModel model;
    public void checkNotification(Context con){
        if (checkNotificationAllowed.isNOtificationOpen(con) == false) {//当用户没有通知栏权限时
            SharedPreferences preferences = con.getSharedPreferences("NOTIFICATION", Context.MODE_APPEND);
            if (preferences.contains("notifi") == false) {
                //用户第一次点击修改权限弹窗时写入，用于判断是否显示跳转到权限修改到界面
                // （true：用户之前已经进入过修改权限到页面，但不给予通知但权限，false：用户没有进入过）
                showWindowRevampLimit(con);
            }
        }
    }
    //跳转到修改权限页面到弹窗
    private void showWindowRevampLimit(final Context con) {
        new AlertDialog.Builder(con).setTitle("应用通知栏权限被禁止").
                setMessage("无法接收到应用发送的相关通知，需要您手动打开通知权限，是否现在去打开？").setIcon(R.mipmap.ic_launcher).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        con.getSharedPreferences("NOTIFICATION", Context.MODE_APPEND).edit().putBoolean("notifi", true).commit();
                        checkNotificationAllowed.goToSet(con);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setCancelable(true).show();
    }

    public void getUserInfo(IListener iListener){
        if (model==null){
            model=new myModel();
        }
        model.getUserInfo(iListener);
    }

    //获取有无未读消息
    public void getUnReadMessage(IListener iListener){
        if (model==null){
            model=new myModel();
        }
        model.getUnReadMsg(iListener);
    }
}
