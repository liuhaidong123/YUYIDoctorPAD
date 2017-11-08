package com.technology.yuyidoctorpad.activity.PaintList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.technology.yuyidoctorpad.activity.PaintList.Fragment.DateFragment;
import com.technology.yuyidoctorpad.activity.PaintList.Fragment.EleFragment;

/**
 * Created by wanyu on 2017/11/3.
 */

public class PaintMsgPresenter {
    FragmentManager manager;
    Fragment mFragment;
    DateFragment dateFragment;//患者数据
    EleFragment eleFragment;//电子病历
    View first;
    View second;
    public PaintMsgPresenter(FragmentManager manager){
        this.manager=manager;
    }

    public void initFragment(String ids,int resId,View first,View second){
        this.first=first;
        this.second=second;
        this.first.setSelected(true);
        this.second.setSelected(false);
        dateFragment=new DateFragment();
        eleFragment=new EleFragment();
        eleFragment.setIds(ids);
        dateFragment.setIds(ids);
        mFragment=eleFragment;
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.add(resId,dateFragment);
        transaction.add(resId,eleFragment);
        transaction.hide(dateFragment).hide(eleFragment).show(mFragment).commit();
    }

    public void show(int select){
        manager.beginTransaction().hide(mFragment).commit();
        switch (select){
            case 0:
                first.setSelected(true);
                second.setSelected(false);
                mFragment=eleFragment;
                break;
            case 1:
                first.setSelected(false);
                second.setSelected(true);
                mFragment=dateFragment;
                break;
        }
        manager.beginTransaction().show(mFragment).commit();
    }
}
