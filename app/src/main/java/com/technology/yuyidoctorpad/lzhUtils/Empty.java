package com.technology.yuyidoctorpad.lzhUtils;

import android.text.TextUtils;

/**
 * Created by wanyu on 2017/9/27.
 */

public class Empty {
    public static boolean notEmpty(String text){
        return !"".equals(text)&&!TextUtils.isEmpty(text);
    }
}
