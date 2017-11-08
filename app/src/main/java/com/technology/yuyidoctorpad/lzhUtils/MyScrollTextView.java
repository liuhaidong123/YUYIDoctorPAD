package com.technology.yuyidoctorpad.lzhUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by wanyu on 2017/10/12.
 */

public class MyScrollTextView extends TextView
{

    public MyScrollTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollTextView(Context context) {
        super(context);
    }

    @Override

    public boolean isFocused() {
        return true;
        }
    }

