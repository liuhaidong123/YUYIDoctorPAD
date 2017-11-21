package com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Depart;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.activity.Hospital.HospitalAddDepartActivity;
import com.technology.yuyidoctorpad.code.RSCode;
import com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Depart.Adapter.DepartListAdapter;
import com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Depart.Bean.DepartmentBean;
import com.technology.yuyidoctorpad.lzhViews.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
//科室信息
/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentFragment extends BaseFragment implements IDepart{
    Presenter presenter;
    List<DepartmentBean.ResultBean> li;
    DepartListAdapter adapter;
    @BindView(R.id.DepartListView)ListView DepartListView;
    @BindView(R.id.Depart_add)TextView Depart_add;//添加科室按钮
    @BindView(R.id.Depart_Manager)TextView Depart_Manager;//管理／完成按钮
    @BindView(R.id.loadingLayout)RelativeLayout loadingLayout;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View vi=inflater.inflate(R.layout.fragment_department, container, false);
            unbinder= ButterKnife.bind(this,vi);
            li=new ArrayList<>();
            adapter=new DepartListAdapter(this,li);
            DepartListView.setAdapter(adapter);
            presenter=new Presenter();
            presenter.getDepartmentData(this);
            return vi;
    }
    @OnClick({R.id.Depart_add,R.id.Depart_Manager})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.Depart_add://添加
                startActivityForResult(new Intent(getActivity(), HospitalAddDepartActivity.class), RSCode.requestCode);
                break;
            case R.id.Depart_Manager://管理／完成
                ExpandList();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RSCode.requestCode){//添加页面
            if (resultCode==RSCode.resultCode){//数据改变了，需要重新请求
                loadingLayout.setVisibility(View.VISIBLE);
                presenter.getDepartmentData(this);
            }
            if (resultCode==100){//编辑页面
                    loadingLayout.setVisibility(View.VISIBLE);
                    presenter.getDepartmentData(this);
            }
        }
    }

    @Override
    public void onGetDrpartError(String msg) {
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void onGetDrpartSuccess(List<DepartmentBean.ResultBean> list) {
        if (loadingLayout!=null){
            loadingLayout.setVisibility(View.GONE);
        }
        if (list!=null&&list.size()>0){
                li.clear();
                li.addAll(list);
                adapter.notifyDataSetChanged();
        }
    }

    private void ExpandList(){
        Depart_Manager.setSelected(!Depart_Manager.isSelected());
        boolean flag=Depart_Manager.isSelected();
        if (flag){
            Depart_add.setVisibility(View.GONE);
            Depart_Manager.setText("完成");
        }
        else {
            Depart_add.setVisibility(View.VISIBLE);
            Depart_Manager.setText("管理");
        }
        for (int i=0;i<li.size();i++){
            li.get(i).setCanOpen(flag);
        }
        adapter.notifyDataSetChanged();
    }
}
