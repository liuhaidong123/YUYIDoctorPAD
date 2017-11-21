package com.technology.yuyidoctorpad.activity.AddPaintRecord;

import com.technology.yuyidoctorpad.Enum.UserSex;

/**
 * Created by wanyu on 2017/11/20.
 */

public interface IpaintAdd {
    void onSexSelect(UserSex sex);
    void onAddError(String msg);
    void onAddSuccess();
}
