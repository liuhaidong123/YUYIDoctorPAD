package com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;

/**
 * y医院主页
 */
public class HomePageFragment extends Fragment {

    private TextView mH_Name, mH_Grade, mH_Tele, mContent;
    private ImageView mH_Img;

    public HomePageFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        mH_Name = (TextView) view.findViewById(R.id.h_name);
        mH_Grade = (TextView) view.findViewById(R.id.h_grade);
        mH_Tele = (TextView) view.findViewById(R.id.h_telephone);
        mContent = (TextView) view.findViewById(R.id.h_content);
        mContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        mH_Img = (ImageView) view.findViewById(R.id.h_img);
    }
}
