package com.technology.yuyidoctorpad.activity.PaintList;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.activity.PaintList.Adapter.LookElecAdapter;
import com.technology.yuyidoctorpad.activity.PaintList.Bean.bean_MedicalRecordList;
import com.technology.yuyidoctorpad.lzhViews.MyActivity;
import com.technology.yuyidoctorpad.lzhViews.MyGridView;

import java.util.ArrayList;
import java.util.List;

//病历详情
public class PaintEleMsgActivity extends MyActivity {
    bean_MedicalRecordList.ResultBean beanUser;//用户自己的电子病例
    private ImageView mBack;
    TextView eleMsg_text_creatTime,eleMsg_text_hospitalName,eleMsg_text_KS,eleMsg_text_DocName;//时间，医院，科室，医生
    MyGridView ele_gridView;//传入的图片
    LookElecAdapter adapter;
    TextView ele_message;//病例内容
    private int id;
//    private String type;//0我的病历，1家人病历
    List<String> list;//存放图片的url（不加ip）
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setChilidView(R.layout.activity_paint_ele_msg);
        ele_message = (TextView) findViewById(R.id.ele_message);
        ele_message.setMovementMethod(ScrollingMovementMethod.getInstance());
        initView();
        initIntentData();
    }

    private void initIntentData() {
                list=new ArrayList<>();
                beanUser= (bean_MedicalRecordList.ResultBean) getIntent().getSerializableExtra("data");
                if (beanUser!=null){
                    eleMsg_text_creatTime.setText(beanUser.getCreateTimeString());
                    eleMsg_text_hospitalName.setText(beanUser.getHospitalName());
                    eleMsg_text_KS.setText(beanUser.getDepartmentName());
                    eleMsg_text_DocName.setText(beanUser.getPhysicianName());
                    ele_message.setText(beanUser.getMedicalrecord());
                    String url=beanUser.getPicture();
                    if (!"".equals(url)&&url!=null) {
                        String[] str = url.split(";");
                        adapter = new LookElecAdapter(this, str);
                        ele_gridView.setAdapter(adapter);
                    }
                }
    }

    private void initView() {
        eleMsg_text_creatTime= (TextView) findViewById(R.id.eleMsg_text_creatTime);
        eleMsg_text_hospitalName= (TextView) findViewById(R.id.eleMsg_text_hospitalName);
        eleMsg_text_KS= (TextView) findViewById(R.id.eleMsg_text_KS);
        eleMsg_text_DocName= (TextView) findViewById(R.id.eleMsg_text_DocName);
        ele_gridView= (MyGridView) findViewById(R.id.ele_gridView);
    }
}
