package com.technology.yuyidoctorpad.fragment.HospitalFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.activity.WriteHospitalMessageActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManagerMessageFragment extends Fragment implements View.OnClickListener{
    private TextView mNext_Btn;
    private WriteHospitalMessageActivity mActivity;

    public ManagerMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_manager_message, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view){
        mNext_Btn=view.findViewById(R.id.manager_next_btn);
        mNext_Btn.setOnClickListener(this);
        mActivity= (WriteHospitalMessageActivity) getActivity();
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if (id==mNext_Btn.getId()){
            mActivity.showSubmitFragment();
        }

    }
}
