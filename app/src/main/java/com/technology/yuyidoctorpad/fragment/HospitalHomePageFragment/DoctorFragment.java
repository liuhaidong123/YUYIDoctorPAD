package com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import com.squareup.picasso.Picasso;
import com.technology.yuyidoctorpad.HttpTools.HttpTools;
import com.technology.yuyidoctorpad.HttpTools.UrlTools;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.activity.AddDcotorActivity;
import com.technology.yuyidoctorpad.activity.DoctorDetilsActivity;
import com.technology.yuyidoctorpad.activity.DoctorSearchActivity;
import com.technology.yuyidoctorpad.activity.HospitalHomePageActivity;
import com.technology.yuyidoctorpad.adapter.DepartmentAda;
import com.technology.yuyidoctorpad.bean.DepartmentBean.Result;
import com.technology.yuyidoctorpad.bean.DoctorList.Root;
import com.technology.yuyidoctorpad.bean.DoctorList.Rows;
import com.technology.yuyidoctorpad.lhdUtils.RoundImageView;
import com.technology.yuyidoctorpad.lhdUtils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * 医生信息
 */
public class DoctorFragment extends Fragment implements View.OnClickListener {

    private TextView department_tv;
    private RelativeLayout mSelect_Department_Rl, mNoData_Rl;
    private ImageView mSearch_Img, mAdd_Img;
    private ListView mDepartment_Listview, mDoctor_Listview;
    private DepartmentAda mDepartmentAda;
    private long deparmentId = 0;
    private View mFooter;
    private ProgressBar mBar;
    private List<Result> mDeparmentListOne1 = new ArrayList<>();
    private List<Rows> mDoctorList = new ArrayList<>();
    private DoctorAda mDoctorAda;
    private int start = 0, limit = 20;
    private boolean isMoreFlag = false;//true代表点击了加载更多
    private HospitalHomePageActivity hospitalHomePageActivity;

    private HttpTools mHttptools;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 57) {//获取医生列表
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root != null) {
                        mDoctor_Listview.removeFooterView(mFooter);
                        mBar.setVisibility(View.GONE);
                        if (isMoreFlag) {
                            mDoctorList.addAll(root.getRows());
                            mDoctorAda.notifyDataSetChanged();

                        } else {
                            mDoctorList = root.getRows();
                            mDoctorAda.notifyDataSetChanged();
                        }

                        if (mDoctorList.size() == 0) {
                            mNoData_Rl.setVisibility(View.VISIBLE);
                        } else {
                            mNoData_Rl.setVisibility(View.GONE);
                        }

                        if (root.getRows().size() != 20) {
                            mDoctor_Listview.removeFooterView(mFooter);
                        } else {
                            mDoctor_Listview.addFooterView(mFooter);
                        }

                    } else {
                        ToastUtils.myToast(getContext(), "获取医生列表错误");
                    }
                }


            } else if (msg.what == 55) {//获取科室信息
                Object o = msg.obj;
                if (o != null && o instanceof com.technology.yuyidoctorpad.bean.DepartmentBean.Root) {
                    com.technology.yuyidoctorpad.bean.DepartmentBean.Root root = (com.technology.yuyidoctorpad.bean.DepartmentBean.Root) o;
                    if (root != null && root.getResult() != null) {
                        mDeparmentListOne1 = root.getResult();
                        if (mDeparmentListOne1.size() != 0) {
                            // department_tv.setText("全部");
                            mDeparmentListOne1.add(new Result("全部"));
                            mDepartmentAda = new DepartmentAda(mDeparmentListOne1, getContext());
                            mDepartment_Listview.setAdapter(mDepartmentAda);
                        } else {
                            ToastUtils.myToast(getContext(), "无法获取科室列表");
                            department_tv.setText("无法获取科室列表");
                        }
                    }
                }
            }
        }
    };

    public DoctorFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor, container, false);
        initUI(view);
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.getDepartmentMessage(mHandler, User.HospitalId);//获取科室列表
        mHttptools.getDoctorList(mHandler, Long.valueOf(User.HospitalId), deparmentId, start, limit);//默认请求全部医生
        return view;
    }

    private void initUI(View view) {
        hospitalHomePageActivity = (HospitalHomePageActivity) getActivity();
        mNoData_Rl = view.findViewById(R.id.nodata_rl);
        department_tv = view.findViewById(R.id.department_type_tv);
        mSelect_Department_Rl = view.findViewById(R.id.department_type_rl);
        mSelect_Department_Rl.setOnClickListener(this);
        mSearch_Img = view.findViewById(R.id.search_img);//搜索
        mSearch_Img.setOnClickListener(this);
        mAdd_Img = view.findViewById(R.id.add_img);//添加医生
        mAdd_Img.setOnClickListener(this);
        //选择科室
        mDepartment_Listview = view.findViewById(R.id.department_listview);
        mDepartment_Listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                department_tv.setText(mDeparmentListOne1.get(i).getDepartmentName());
                mDepartment_Listview.setVisibility(View.GONE);
                if (i == mDeparmentListOne1.size() - 1) {//点击的是全部
                    deparmentId = 0;
                } else {
                    deparmentId = mDeparmentListOne1.get(i).getId();
                }
                start = 0;
                isMoreFlag = false;
                mHttptools.getDoctorList(mHandler, Long.valueOf(User.HospitalId), deparmentId, start, limit);//根据科室id请求全部医生
            }
        });
        //医生
        mDoctor_Listview = view.findViewById(R.id.doctor_listview);
        mDoctorAda = new DoctorAda();
        mFooter = LayoutInflater.from(getContext()).inflate(R.layout.circle_listview_footer, null);
        mBar = mFooter.findViewById(R.id.pbLocate);
        mDoctor_Listview.setAdapter(mDoctorAda);
        mDoctor_Listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == mDoctorList.size()) {//加载更多
                    mBar.setVisibility(View.VISIBLE);
                    isMoreFlag = true;
                    start += 20;
                    mHttptools.getDoctorList(mHandler, Long.valueOf(User.HospitalId), deparmentId, start, limit);//根据科室id请求全部医生
                } else {//跳转医生详情
                    Intent intent = new Intent(getContext(), DoctorDetilsActivity.class);
                    intent.putExtra("doctorId", mDoctorList.get(i).getId());
                    startActivity(intent);
                }
            }
        });
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
            mHttptools.getDepartmentMessage(mHandler, User.HospitalId);//获取科室列表


        } else if (id == mAdd_Img.getId()) {//添加医生
            if (mDepartment_Listview.getVisibility() == View.VISIBLE) {
                mDepartment_Listview.setVisibility(View.GONE);
            }
            Intent intent = new Intent(getActivity(), AddDcotorActivity.class);
           // startActivity(intent);
            startActivityForResult(intent,100);
        } else if (id == mSearch_Img.getId()) {
            if (mDepartment_Listview.getVisibility() == View.VISIBLE) {
                mDepartment_Listview.setVisibility(View.GONE);
            }
            Intent intent = new Intent(getActivity(), DoctorSearchActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==RESULT_OK){
            deparmentId=0;
            department_tv.setText("全部");
            mHttptools.getDoctorList(mHandler, Long.valueOf(User.HospitalId), deparmentId, start, limit);//默认请求全部医生
            Log.e("onActivityResult","====");
        }
    }

    //医生列表适配器
    class DoctorAda extends BaseAdapter {

        @Override
        public int getCount() {
            return mDoctorList.size();
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
            Picasso.with(getContext()).load(UrlTools.BASE + mDoctorList.get(i).getAvatar()).error(R.mipmap.erroruser).into(holder.head);
            holder.name.setText(mDoctorList.get(i).getTrueName());
            holder.type.setText(mDoctorList.get(i).getTitle());
            holder.department.setText(mDoctorList.get(i).getDepartmentName());
            return view;
        }

        class DoctorHolder {
            RoundImageView head;
            TextView name, type, department;
        }
    }

}
