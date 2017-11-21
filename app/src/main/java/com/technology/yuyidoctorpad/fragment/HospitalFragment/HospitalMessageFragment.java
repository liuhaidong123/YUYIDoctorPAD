package com.technology.yuyidoctorpad.fragment.HospitalFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.activity.WriteHospitalMessageActivity;
//编辑医院信息
/**
 * A simple {@link Fragment} subclass.
 */
public class HospitalMessageFragment extends Fragment implements View.OnClickListener {
    private TextView mNext_Btn;
    private WriteHospitalMessageActivity mActivity;

    public HospitalMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hospital_message, container, false);
        InitUI(view);
        return view;
    }

    private void InitUI(View view) {
        mNext_Btn = view.findViewById(R.id.hospital_next_btn);
        mNext_Btn.setOnClickListener(this);
        mActivity = (WriteHospitalMessageActivity) getActivity();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == mNext_Btn.getId()) {
            mActivity.showManagerFragment();
        }
    }
}
