package com.technology.yuyidoctorpad.activity.PaintList;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technology.yuyidoctorpad.Enum.IntentValue;
import com.technology.yuyidoctorpad.Enum.UserSex;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.fragment.paintFragment.paintListBean;
import com.technology.yuyidoctorpad.lhdUtils.RoundImageView;
import com.technology.yuyidoctorpad.lzhUtils.Empty;
import com.technology.yuyidoctorpad.lzhViews.MyActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.technology.yuyidoctorpad.Enum.UserSex.GIRL;

public class PaintMsgActivity extends MyActivity {
    PaintMsgPresenter presenter;
    @BindView(R.id.titleinclude_text)TextView titleinclude_text;
    @BindView(R.id.paintmsg_userimage)RoundImageView paintmsg_userimage;//头像
    @BindView(R.id.paintmsg_username)TextView paintmsg_username;//姓名
    @BindView(R.id.paintmsg_userage)TextView paintmsg_userage;//年龄
    @BindView(R.id.paintmsg_usersex)ImageView paintmsg_usersex;//性别
    @BindView(R.id.paintmsg_ele)TextView paintmsg_ele;//电子病历切换button
    @BindView(R.id.paintmsg_data)TextView paintmsg_data;//患者数据切换button
    paintListBean.RowsBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setChilidView(R.layout.activity_paint_msg);
        titleinclude_text.setText("患者详情");
        bean= (paintListBean.RowsBean) getIntent().getSerializableExtra(IntentValue.PaintMsg);
        initView();
        presenter=new PaintMsgPresenter(getSupportFragmentManager());
        presenter.initFragment(bean.getId()+"",R.id.fraglayout,paintmsg_ele,paintmsg_data);
    }

    private void initView() {
        if (bean!=null){
            Picasso.with(this).load(Ip.imagePath+bean.getAvatar()).placeholder(R.mipmap.erroruser).error(R.mipmap.erroruser).into(paintmsg_userimage);
            paintmsg_username.setText(Empty.notEmpty(bean.getTrueName())?bean.getTrueName():"姓名");
            paintmsg_userage.setText(bean.getAge()+"岁");
            if (UserSex.getUserSex(bean.getGender())==GIRL){
                paintmsg_usersex.setSelected(false);
            }
            else {
                paintmsg_usersex.setSelected(true);
            }
        }
    }
    @OnClick({R.id.paintmsg_ele,R.id.paintmsg_data})
    public void 点击事件(View vi){
        switch (vi.getId()){
            case R.id.paintmsg_ele://电子病历
                presenter.show(0);
                break;
            case R.id.paintmsg_data://患者数据
                presenter.show(1);
                break;

        }
    }
}
