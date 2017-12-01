package com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technology.yuyidoctorpad.HttpTools.HttpTools;
import com.technology.yuyidoctorpad.HttpTools.UrlTools;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.bean.HospitalHomePage.Root;
import com.technology.yuyidoctorpad.lhdUtils.MyDialog;
import com.technology.yuyidoctorpad.lhdUtils.ToastUtils;

/**
 * y医院主页
 */
public class HomePageFragment extends Fragment {

    private TextView mH_Name, mH_Grade, mH_Tele, mContent, mJJ;
    private ImageView mH_Img;
    private View line;
    private HttpTools httpTools;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MyDialog.stopDia();
            if (msg.what == 63) {
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root != null) {
                        line.setBackgroundResource(R.color.color_1ebeec);
                        mJJ.setText("医院简介");
                        mH_Name.setText(root.getHospitalName());
                        mH_Grade.setText(root.getGrade());
                        mH_Tele.setText("联系电话：" + root.getTell());
                        mContent.setText(root.getIntroduction());
                        Picasso.with(getContext()).load(UrlTools.BASE + root.getPicture()).error(R.mipmap.errorpicture).into(mH_Img);
                    }
                } else {
                    ToastUtils.myToast(getContext(), "医院信息错误");
                }
            }
        }
    };

    public HomePageFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        httpTools = HttpTools.getHttpToolsInstance();
        httpTools.getHospitalHomePage(handler, Long.valueOf(User.HospitalId));
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        MyDialog.showDialog(getActivity());
        mH_Name = (TextView) view.findViewById(R.id.h_name);
        mH_Grade = (TextView) view.findViewById(R.id.h_grade);
        mH_Tele = (TextView) view.findViewById(R.id.h_telephone);
        mContent = (TextView) view.findViewById(R.id.h_content);
        mContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        mH_Img = (ImageView) view.findViewById(R.id.h_img);
        mJJ = (TextView) view.findViewById(R.id.h_message);
        line = view.findViewById(R.id.h_line2);
    }
}
