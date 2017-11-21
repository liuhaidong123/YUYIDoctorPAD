package com.technology.yuyidoctorpad.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technology.yuyidoctorpad.HttpTools.HttpTools;
import com.technology.yuyidoctorpad.HttpTools.UrlTools;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.bean.HospitalInformationDetails.Root;
import com.technology.yuyidoctorpad.lhdUtils.ToastUtils;

public class InformationDetailsActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView title,grade,status,way,content;
    private HttpTools httpTools;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==61){
                Object o=msg.obj;
                if (o!=null&&o instanceof Root){
                    Root root= (Root) o;
                    if (root!=null){
                        if (root.getCode().equals("0")){
                            Picasso.with(getApplicationContext()).load(UrlTools.BASE+root.getResult().getPicture()).error(R.mipmap.errorpicture).into(imageView);
                            title.setText(root.getResult().getTitle());
                            grade.setText(root.getResult().getSmallTitle());

                            if (root.getResult().getAuditState()==1){
                                status.setText("审核通过");
                            }else if (root.getResult().getAuditState()==2){
                                status.setText("审核中");
                            }else {
                                status.setText("审核失败");
                            }
                            content.setText(root.getResult().getContent());

                            if (root.getResult().getPublishchannel()==1){
                                way.setText("发布渠道：宇医");
                            }else {
                                way.setText("发布渠道：宇医医生");
                            }
                        }else {
                            ToastUtils.myToast(getApplicationContext(),"无法获取帖子详情");
                        }
                    }
                }else {
                    ToastUtils.myToast(getApplicationContext(),"获取帖子详情失败");
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_details);
        httpTools=HttpTools.getHttpToolsInstance();
        httpTools.getHospitalInfomationDetails(handler,getIntent().getLongExtra("inforID",-1));
        imageView= (ImageView) findViewById(R.id.infor_img);
        title= (TextView) findViewById(R.id.infor_title);
        grade= (TextView) findViewById(R.id.infor_grade);
        status= (TextView) findViewById(R.id.infor_status);
        way= (TextView) findViewById(R.id.infor_way);
        content= (TextView) findViewById(R.id.infor_content);
    }
}
