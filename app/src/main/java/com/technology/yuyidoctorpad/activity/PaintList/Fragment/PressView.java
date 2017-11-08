package com.technology.yuyidoctorpad.activity.PaintList.Fragment;

/**
 * Created by wanyu on 2017/11/3.
 */
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.lzhViews.FormView;

import java.util.ArrayList;
import java.util.List;

public class PressView {
    View vi;
    FormView form_press;
    ImageView img;//血压是否正常的view
    TextView highPress,lowPress;//低压，高压
    public PressView(View vi){
        form_press=vi.findViewById(R.id.form_press);
        highPress=vi.findViewById(R.id.highPress);
        lowPress=vi.findViewById(R.id.lowPress);
        img=vi.findViewById(R.id.press_image);
        form_press.drawRightTextView(new String[]{"高压","低压"});//血压
        List<Integer> li=new ArrayList<>();//血压Y轴数据源
        int min=40;//最低血压
        for (int i=0;i<8;i++){
            li.add(min);
            min+=20;
        }
        form_press.drawTopView(li);
    }
    //给血压图填充数据
    public void setDate(List<String>liTime,List<Float>listSource,List<Float>listOtherSource,float highP,float LowP){
        form_press.drawBottomView(liTime);
        form_press.drawFirstDataView(listSource);
        form_press.drawOtherDataView(listOtherSource);
        lowPress.setText(TemPressUtils.getInstance().getText(LowP));
        highPress.setText(TemPressUtils.getInstance().getText(highP));
        img.setImageResource(TemPressUtils.getInstance().getInfoRes(DatePresenter.Type.PRESS,highP,LowP,0f));
    }
}
