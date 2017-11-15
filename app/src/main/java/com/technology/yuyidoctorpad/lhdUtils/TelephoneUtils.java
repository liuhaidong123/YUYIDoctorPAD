package com.technology.yuyidoctorpad.lhdUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuhaidong on 2017/11/15.
 */

public class TelephoneUtils {
    /**
     * 检查手机号
     *
     * @param phonenum
     * @return true 代表手机号正确
     */
    public static boolean checkPhone(String phonenum) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(phonenum);
        b = m.matches();
        return b;
    }
}
