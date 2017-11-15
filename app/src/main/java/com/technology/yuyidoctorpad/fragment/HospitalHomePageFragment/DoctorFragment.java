package com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.activity.HospitalHomePageActivity;
import com.technology.yuyidoctorpad.adapter.DepartmentAda;
import com.technology.yuyidoctorpad.lhdUtils.RoundImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 医生信息
 */
public class DoctorFragment extends Fragment implements View.OnClickListener {

    private TextView department_tv;
    private RelativeLayout mSelect_Department_Rl;
    private ImageView mSearch_Img, mAdd_Img;
    private ListView mDepartment_Listview, mDoctor_Listview;
    private DepartmentAda mDepartmentAda;
    private View mFooter;
    private ProgressBar mBar;
    private List<String> mDepartmentList = new ArrayList();
    private HospitalHomePageActivity hospitalHomePageActivity;

    public DoctorFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        hospitalHomePageActivity= (HospitalHomePageActivity) getActivity();
        mDepartmentList.add("内科");
        mDepartmentList.add("外科");
        mDepartmentList.add("眼科");
        mDepartmentList.add("神经科");
        department_tv = view.findViewById(R.id.department_type_tv);
        mSelect_Department_Rl = view.findViewById(R.id.department_type_rl);
        mSelect_Department_Rl.setOnClickListener(this);
        mSearch_Img = view.findViewById(R.id.search_img);
        mAdd_Img = view.findViewById(R.id.add_img);//添加医生
        mAdd_Img.setOnClickListener(this);
        //选择科室
        mDepartment_Listview = view.findViewById(R.id.department_listview);
        mDepartmentAda = new DepartmentAda(mDepartmentList, getContext());
        mDepartment_Listview.setAdapter(mDepartmentAda);
        mDepartment_Listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                department_tv.setText(mDepartmentList.get(i));
                mDepartment_Listview.setVisibility(View.GONE);
            }
        });
        //医生
        mDoctor_Listview = view.findViewById(R.id.doctor_listview);
        mFooter = LayoutInflater.from(getContext()).inflate(R.layout.circle_listview_footer, null);
        mBar = mFooter.findViewById(R.id.pbLocate);
        mDoctor_Listview.addFooterView(mFooter);
        mDoctor_Listview.setAdapter(new DoctorAda());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == mSelect_Department_Rl.getId()) {//点击选择科室
            hospitalHomePageActivity.isMenuShow();//将hospitalHomePageActivity中的ListView隐藏
            if (mDepartment_Listview.getVisibility() == View.VISIBLE) {
                mDepartment_Listview.setVisibility(View.GONE);
            } else if (mDepartment_Listview.getVisibility() == View.GONE) {
                mDepartment_Listview.setVisibility(View.VISIBLE);
            }

        } else if (id == mAdd_Img.getId()) {//添加医生
            hospitalHomePageActivity.showAddDoctor();
            if (mDepartment_Listview.getVisibility() == View.VISIBLE) {
                mDepartment_Listview.setVisibility(View.GONE);
            }

        }
    }


    class DoctorAda extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            DoctorHolder holder = null;
            if (view == null) {
                holder = new DoctorHolder();
                view = LayoutInflater.from(getContext()).inflate(R.layout.doctor_listview_item, null);
                holder.head = view.findViewById(R.id.doctor_head_img);
                holder.name = view.findViewById(R.id.doctor_name);
                holder.type = view.findViewById(R.id.doctor_type);
                holder.department = view.findViewById(R.id.doctor_department);
                view.setTag(holder);
            } else {
                holder = (DoctorHolder) view.getTag();
            }
            holder.head.setImageResource(R.mipmap.erroruser);
            holder.name.setText("JsonMarin");
            holder.type.setText("主治医师");
            holder.department.setText("妇产科");
            return view;
        }

        class DoctorHolder {
            RoundImageView head;
            TextView name, type, department;
        }
    }

}
