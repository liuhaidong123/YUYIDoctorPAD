package com.technology.yuyidoctorpad.fragment.paintFragment;

/**
 * Created by wanyu on 2017/11/3.
 */

public class paintPresenter {
    PaintModel model;
    public void getList(Ipaint ipaint,int st,int limit){
        if (model==null){
            model=new PaintModel();
        }
        model.getPaintList(ipaint,st,limit);
    }
}
