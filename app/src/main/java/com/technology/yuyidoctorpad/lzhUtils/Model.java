package com.technology.yuyidoctorpad.lzhUtils;

import android.os.Handler;
import android.os.Message;

/**
 * Created by wanyu on 2017/11/14.
 */

public class Model extends Handler{
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
    }
    public void sendErrorMsg(String msg,int what){
        Message ms=new Message();
        ms.obj=msg;
        ms.what=what;
        sendMessage(ms);
    }
    public void sendSuccessMsg(Object o,int what){
        Message ms=new Message();
        ms.obj=o;
        ms.what=what;
        sendMessage(ms);
    }

}
