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
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(phonenum);
        b = m.matches();
        return b;
    }


    /**
     *
     * @param fixedPhone
     * @return
     */
    public static boolean isFixedPhone(String fixedPhone){

        Pattern p1 = null, p2 = null;
        Matcher m = null;
        boolean b = false;
        p1 = Pattern.compile("^[0][1-9]{2,3}[0-9]{5,10}$");  // 验证带区号的
        p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
        if (fixedPhone.length() > 9) {
            m = p1.matcher(fixedPhone);
            b = m.matches();
        } else {
            m = p2.matcher(fixedPhone);
            b = m.matches();
        }
        return b;
    }
}
