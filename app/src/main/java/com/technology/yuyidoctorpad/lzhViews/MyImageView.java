package com.technology.yuyidoctorpad.lzhViews;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.technology.yuyidoctorpad.lhdUtils.RoundImageView;


/**
 * Created by wanyu on 2017/4/10.
 */

public class MyImageView extends RoundImageView {
    private Boolean isRead=false;
    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyImageView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isRead==false){
            Paint paint=new Paint();
            paint.setColor(Color.RED);
            canvas.drawCircle(canvas.getWidth()-5,8,5f,paint);
        }
//        else {
//            Paint paint=new Paint();
//            paint.setColor(Color.BLUE);
//            canvas.drawCircle(canvas.getWidth()-15,15,15.0f,paint);
//        }
    }


    public void setIsRead(Boolean isRead){
        this.isRead=isRead;
        invalidate();
    }
    public boolean getIsRead(){
        return isRead;
    }
}
