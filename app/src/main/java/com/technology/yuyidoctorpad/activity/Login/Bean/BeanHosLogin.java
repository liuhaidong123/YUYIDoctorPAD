package com.technology.yuyidoctorpad.activity.Login.Bean;

/**
 * Created by wanyu on 2017/11/13.
 */

public class BeanHosLogin {
//    返回值：    返回值名称  返回值类型    备注
//    code    String      0成功-1失败
//    State    int      0表示从未注册过
//    1表示资料审核通过
//    2表示资料再审核中
//    3表示审核未通过
//    result          登陆的token

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * result : E2256DDE63C751A4597643B2D8B065C3
     * code : 0
     * State : 1
     */
    String message;
    private String result;
    private String code;
    private int State;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getState() {
        return State;
    }

    public void setState(int State) {
        this.State = State;
    }
}
