package com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Depart.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Depart.Bean.DepartmentBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/11/15.
 */

public class DepartListGridViewAdapter extends BaseAdapter{
    List<DepartmentBean.ResultBean.ClinicListBean> li;
    Activity con;
    public DepartListGridViewAdapter( List<DepartmentBean.ResultBean.ClinicListBean> li,Activity con){
        this.li=li;
        this.con=con;
    }
    @Override
    public int getCount() {
        return li==null?0:li.size();
    }

    @Override
    public Object getItem(int i) {
        return li.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHodler hodler;
        if (view==null){
            view= LayoutInflater.from(con).inflate(R.layout.list_grid,null);
            hodler=new ViewHodler(view);
            view.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) view.getTag();
        }
        hodler.depart_list_gridView_text.setText(li.get(i).getClinicName());
        return view;
    }
    class ViewHodler{
        @BindView(R.id.depart_list_gridView_text)TextView depart_list_gridView_text;
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
    }
}
