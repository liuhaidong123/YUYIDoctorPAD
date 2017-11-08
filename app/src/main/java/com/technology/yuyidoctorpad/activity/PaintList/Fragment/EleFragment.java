package com.technology.yuyidoctorpad.activity.PaintList.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.activity.PaintList.Adapter.ElectronicMessListViewAdapter;
import com.technology.yuyidoctorpad.activity.PaintList.Bean.bean_MedicalRecordList;
import com.technology.yuyidoctorpad.activity.PaintList.Model.EleModel;
import com.technology.yuyidoctorpad.activity.PaintList.Model.IEle;
import com.technology.yuyidoctorpad.lzhUtils.MyListView;
import com.technology.yuyidoctorpad.lzhViews.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/11/3.
 */

public class EleFragment extends BaseFragment implements MyListView.IonScrollBottomListener,AdapterView.OnItemClickListener,IEle{
    int start=0;
    int limit=10;
    String id;
    @BindView(R.id.paintEleListView)MyListView paintEleListView;
    ElectronicMessListViewAdapter adapter;
    List<bean_MedicalRecordList.ResultBean> li;
    EleModel model;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.frag_ele,null);
        unbinder= ButterKnife.bind(this,vi);
        li=new ArrayList<>();
        adapter=new ElectronicMessListViewAdapter(getActivity(),li);
        paintEleListView.setAdapter(adapter);
        paintEleListView.setOnScrollBottomListener(this);
        model=new EleModel();
        model.getEleDate(this,id);
        return vi;
    }
    public void setIds(String ids){
        this.id=ids;
    }

    @Override
    public void onScrollBottom() {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onSuccess(List<bean_MedicalRecordList.ResultBean> list) {
        li.addAll(list);
        adapter.notifyDataSetChanged();
        paintEleListView.setLoadingComplete();
    }

    @Override
    public void onError(String msg) {
        paintEleListView.setEmpey(msg);
    }
}
