package com.technology.yuyidoctorpad.lhdUtils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by liuhaidong on 2017/11/6.
 */

public class PostCardPopUtils {


    /**
     * @param activity
     * @param itemView 弹框布局
     * @param height   弹框高度
     * @param width    弹框宽度
     * @param parent   弹框相对的布局
     */
    public static void showCardPop(final Activity activity, View itemView, int height, int width, View parent,PopupWindow mPopupwindow) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //设置透明度
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = 0.6f;
        activity.getWindow().setAttributes(params);
        //存放popupWindow的容器
        // ViewGroup container = (ViewGroup) findViewById(R.id.activity_money_input_sure);
       // mPopupwindow = new PopupWindow(itemView);
        //设置弹框的款，高
        mPopupwindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        mPopupwindow.setHeight(dip2px(activity,height));
        mPopupwindow.setWidth(dip2px(activity,width));
        mPopupwindow.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        mPopupwindow.setFocusable(true);//如果有交互需要设置焦点为true
        mPopupwindow.setOutsideTouchable(false);//设置内容外可以点击

        //mPopupwindow.setAnimationStyle(R.style.popup3_anim);
        //相对于父控件的位置
        mPopupwindow.showAtLocation(parent,Gravity.CENTER,0,0);
        //当弹框销毁时，将透明度初始化，否则弹框销毁后，所依附的activity页面背景将会改变
        mPopupwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params = activity.getWindow().getAttributes();
                params.alpha = 1f;
                activity.getWindow().setAttributes(params);
            }
        });
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context
     * @param dpValue
     * @return
     *
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
