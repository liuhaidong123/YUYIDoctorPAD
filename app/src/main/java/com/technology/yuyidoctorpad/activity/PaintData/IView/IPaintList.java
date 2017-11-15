package com.technology.yuyidoctorpad.activity.PaintData.IView;

import com.technology.yuyidoctorpad.activity.PaintData.Bean.Bean_MyPaintList;

/**
 * Created by wanyu on 2017/11/14.
 */

public interface IPaintList {
    void onGetPaintListError(String msg);
    void onGetPaintListSuccess(Bean_MyPaintList bean);
}
