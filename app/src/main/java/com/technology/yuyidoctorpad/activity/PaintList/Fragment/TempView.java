package com.technology.yuyidoctorpad.activity.PaintList.Fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.lzhViews.FormView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanyu on 2017/11/3.
 */
public class TempView {
    FormView form_temp;
    ImageView img;//体温是否正常的view
    TextView temp_text;//当前体温
    public TempView(View vi){
        form_temp=vi.findViewById(R.id.form_temp);
        img=vi.findViewById(R.id.temp_image);
        temp_text=vi.findViewById(R.id.temp_text);
        form_temp.drawRightTextView(new String[]{"体温"});//血压
        List<Integer> li=new ArrayList<>();//血压Y轴数据源
        int min=35;//最低血压
        for (int i=0;i<8;i++){
            li.add(min);
            min+=1;
        }
        form_temp.drawTopView(li);
    }

    //给血压图填充数据
    public void setDate(List<String>listTime,List<Float>listSource,float tem){
        form_temp.drawBottomView(listTime);
        form_temp.drawFirstDataView(listSource);
        temp_text.setText(TemPressUtils.getInstance().getText(tem)+"℃");
        img.setImageResource(TemPressUtils.getInstance().getInfoRes(DatePresenter.Type.TEMP,0f,0f,tem));
    }

}
