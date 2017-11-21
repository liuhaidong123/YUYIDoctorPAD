package com.technology.yuyidoctorpad.activity.Hospital.Listener;

import java.util.List;

/**
 * Created by wanyu on 2017/11/17.
 */

public interface Iadd {
    void onSuccess();
    void onError(String msg);
    void onPartError(List<String>list);//部分成功list集合存放的是不成功的
}
