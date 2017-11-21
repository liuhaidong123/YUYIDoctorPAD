package com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Depart.Adapter;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.Net.OkUtils;
import com.technology.yuyidoctorpad.Net.gson;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.activity.Hospital.HospitalEditActivity;
import com.technology.yuyidoctorpad.code.RSCode;
import com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Depart.Bean.DepartDeleteBean;
import com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Depart.Bean.DepartmentBean;
import com.technology.yuyidoctorpad.lzhUtils.Empty;
import com.technology.yuyidoctorpad.lzhUtils.toast;
import com.technology.yuyidoctorpad.lzhViews.MyGridView;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/11/15.
 */

public class DepartListAdapter extends BaseAdapter {
    List<DepartmentBean.ResultBean> list;
    Fragment con;
    boolean flag;
    String resStr;
    int current;
    Handler hand=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    flag=false;
                    toast.toast(con.getActivity(),"删除失败：网络异常！");
                    break;
                case 1:
                    flag=false;
                    try{
                        DepartDeleteBean bean= gson.gson.fromJson(resStr,DepartDeleteBean.class);
                        if (bean!=null&&"0".equals(bean.getCode())){
                            toast.toast(con.getActivity(),"删除成功！");
                            list.remove(current);
                            notifyDataSetChanged();
                        }
                        else {
                            toast.toast(con.getActivity(), Empty.notEmpty(bean.getMessage())?bean.getMessage():"删除失败！");
                        }
                    }
                    catch (Exception e){
                        toast.toast(con.getActivity(),"删除失败：数据异常！");
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    public DepartListAdapter(Fragment con, List<DepartmentBean.ResultBean> list){
        this.con=con;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHodler hodler;
        if (view==null){
            view= LayoutInflater.from(con.getActivity()).inflate(R.layout.list_depart,null);
            hodler=new ViewHodler(view);
            view.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) view.getTag();
        }
        hodler.depart_name.setText(list.get(i).getDepartmentName());
        hodler.depart_rela.setVisibility(list.get(i).isCanOpen()?View.VISIBLE:View.GONE);
        DepartListGridViewAdapter adapter=new DepartListGridViewAdapter(list.get(i).getClinicList(),con.getActivity());
        hodler.depart_gridView.setAdapter(adapter);
        hodler.depart_delete.setTag(i);
        hodler.depart_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag){
                    toast.toast(con.getActivity(),"正在操作，请稍后...");
                    return;
                }
                else {
                    flag=true;
                    current= (int) view.getTag();
                    delete(current);
                }

            }
        });
        hodler.depart_edit.setTag(i);
        hodler.depart_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos= (int) view.getTag();
                Intent intent=new Intent();
                intent.setClass(con.getActivity(), HospitalEditActivity.class);
                intent.putExtra("data",list.get(pos));
                con.startActivityForResult(intent, RSCode.requestCode);
            }
        });
        return view;
    }

    class ViewHodler{
        @BindView(R.id.depart_rela)RelativeLayout depart_rela;//编辑与删除所在的layout
        @BindView(R.id.depart_name)TextView depart_name;//科室名称（大类名）
        @BindView(R.id.depart_delete)TextView depart_delete;//全部删除按钮
        @BindView(R.id.depart_edit)ImageView depart_edit;//编辑按钮
        @BindView(R.id.depart_gridView)MyGridView depart_gridView;
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
    }
//    http://192.168.1.44:8080/yuyi/department/DeleteDepartmentId.do?DepartmentId=5&hospitalId=1
    public void delete( int pos){
        Map<String,String>mp=new HashMap<>();
        mp.put("DepartmentId",list.get(pos).getId()+"");
        mp.put("hospitalId", User.HospitalId);
        OkUtils.getCall(Ip.path_F+Ip.interface_deleteDepart,mp,OkUtils.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                hand.sendEmptyMessage(0);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("删除科室id="+list.get(current).getId(),resStr);
                hand.sendEmptyMessage(1);
            }
        });
    }
}
