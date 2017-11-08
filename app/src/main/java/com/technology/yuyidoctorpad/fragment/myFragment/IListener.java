package com.technology.yuyidoctorpad.fragment.myFragment;

/**
 * Created by wanyu on 2017/11/7.
 */

public interface IListener {
    void onError(String msg,UserBean user);
    void onSuccess(UserBean user);
    void onTokenError();
    void HaveUnReadMsg(boolean flag);
}
