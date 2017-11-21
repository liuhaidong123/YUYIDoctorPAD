package com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Registe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Depart.Bean.DepartmentBean;

import java.util.List;

/**
 * Created by wanyu on 2017/11/18.
 */

public class PopAdapter extends BaseAdapter{
    Context con;
    List<DepartmentBean.ResultBean> listAllDepart;
    public PopAdapter( List<DepartmentBean.ResultBean> listAllDepart,Context con){
        this.listAllDepart=listAllDepart;
        this.con=con;
    }
    @Override
    public int getCount() {
        return listAllDepart==null?0:listAllDepart.size();
    }

    @Override
    public Object getItem(int i) {
        return listAllDepart.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHodler hodler;
        if (view==null){
            view= LayoutInflater.from(con).inflate(R.layout.it,null);
            hodler=new ViewHodler();
            hodler.ListText=view.findViewById(R.id.ListText);
            view.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) view.getTag();
        }
        hodler.ListText.setText(listAllDepart.get(i).getDepartmentName());
        return view;
    }
    class ViewHodler{
        TextView ListText;
    }
}
