package com.technology.yuyidoctorpad.fragment.HospitalFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.activity.Login.LoginActivity;
import com.technology.yuyidoctorpad.activity.WriteHospitalMessageActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubmitMessageFragment extends Fragment implements View.OnClickListener{

    private TextView mBack_Btn;
    private WriteHospitalMessageActivity mActivity;
    public SubmitMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_submit_message, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view){
        mBack_Btn=view.findViewById(R.id.submit_back_btn);
        mBack_Btn.setOnClickListener(this);
        mActivity= (WriteHospitalMessageActivity) getActivity();
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if (id==mBack_Btn.getId()){
            startActivity(new Intent(getContext(), LoginActivity.class));
        }
    }
}
