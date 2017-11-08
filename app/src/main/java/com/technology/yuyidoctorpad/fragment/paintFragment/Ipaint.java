package com.technology.yuyidoctorpad.fragment.paintFragment;

/**
 * Created by wanyu on 2017/11/3.
 */

public interface   Ipaint {
    void onError(String msg,String interfaceName);
    void onSuccess(paintListBean bean);
    void onTokenError();
}
