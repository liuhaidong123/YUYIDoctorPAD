package com.technology.yuyidoctorpad.lzhUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.technology.yuyidoctorpad.R;


/**
 * Created by wanyu on 2017/10/18.
 */

public class toast {
    private Toast mToast;
    private toast(Context context, String text) {
        View v = LayoutInflater.from(context).inflate(R.layout.eplay_toast, null);
        TextView textView = (TextView) v.findViewById(R.id.textView1);
        textView.setText(text);
        mToast = new Toast(context);
        mToast.setDuration(1500);
        mToast.setView(v);
        mToast.show();
    }

    public static toast toast(Context context, String text) {
        return new toast(context, text);
    }
    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }
    }
}
