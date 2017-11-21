package com.technology.yuyidoctorpad.activity.Hospital.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.activity.Hospital.Bean.DepartBean;
import com.technology.yuyidoctorpad.lzhUtils.Empty;
import com.technology.yuyidoctorpad.lzhUtils.ListChangePosition;
import com.technology.yuyidoctorpad.lzhViews.MyGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/11/16.
 */

public class AddDepartAdapter extends BaseAdapter{
    final String DEFAULT="+添加新科室";
    List<DepartBean>list;
    Context con;
    boolean flag=false;
    public void setSuccess(){
        flag=true;
        notifyDataSetChanged();
    }
    public AddDepartAdapter(List<DepartBean>list, Context con){
        this.list=list;
        this.con=con;
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
    public View getView(final int positon, View view, ViewGroup viewGroup) {
             ViewHodler hodler;
            boolean isLast=list.get(positon).isLast();
            View vi= LayoutInflater.from(con).inflate(R.layout.adddepart_item,null);
            hodler=new ViewHodler(vi);
        if (isLast){//最后一项（添加按钮）
            hodler.add_delete.setVisibility(View.GONE);
            hodler.add_edit.setVisibility(View.GONE);
            hodler.add_addText.setVisibility(View.VISIBLE);
            hodler.addDepartGridView.setVisibility(View.GONE);
        }
        else {//不是最后一项
            hodler.addDepartGridView.setVisibility(View.VISIBLE);
            hodler.add_delete.setVisibility(View.VISIBLE);
            hodler.add_addText.setVisibility(View.GONE);//隐藏添加的TextView
            hodler.add_edit.setVisibility(View.VISIBLE);//显示添加的eiditext

            addDepartList_GridViewAdapter adapter=new addDepartList_GridViewAdapter(list.get(positon).getClinicList(),con,list.get(positon).isCanNotOpen());
            hodler.addDepartGridView.setAdapter(adapter);
        }
            if (list.get(positon).isCanNotOpen()){//不可编辑状态
                hodler.add_delete.setVisibility(View.GONE);
                hodler.add_edit.setInputType(InputType.TYPE_NULL);
            }
            else {
                if (!isLast){
                    hodler.add_delete.setVisibility(View.VISIBLE);
                }
                else {//最后一个隐藏
                    hodler.add_delete.setVisibility(View.GONE);
                }
                hodler.add_edit.setInputType(InputType.TYPE_CLASS_TEXT);
            }

            hodler.add_edit.setHint("输入科室名称");
        if (list.get(positon).getIsErroPart()){//没有添加成功的项目
            hodler.add_edit.setTextColor(Color.RED);
            hodler.add_edit.setText(list.get(positon).getDepartmentName()+"（失败）");
        }
        else {
            hodler.add_edit.setTextColor(con.getResources().getColor(R.color.color_333333));
            if (flag){
                hodler.add_edit.setText(list.get(positon).getDepartmentName()+"（成功）");
            }
            else {
                hodler.add_edit.setText(list.get(positon).getDepartmentName());
            }

        }
            hodler.add_edit.setTag(positon);
            hodler.add_edit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    String text=editable.toString().trim();
                    if (Empty.notEmpty(text)){
                        list.get(positon).setDepartmentName(text);
                        list.get(positon).setHashWord(true);
                    }
                    else {
                        list.get(positon).setDepartmentName("");
                        list.get(positon).setHashWord(false);
                    }
                }
            });
            hodler.add_delete.setTag(positon);
            //取消添加操作
            hodler.add_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int ps= (int) view.getTag();
                    list.remove(ps);
                    notifyDataSetChanged();
                }
            });
            hodler.add_addText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DepartBean bean=new DepartBean();
                    bean.setHospitalId(Long.parseLong(User.HospitalId));
                    DepartBean.Clinic clinic=new DepartBean.Clinic();
                    clinic.setHashWord(false);
                    clinic.setLast(true);
                    List<DepartBean.Clinic>li=new ArrayList<DepartBean.Clinic>();
                    li.add(clinic);
                    bean.setClinicList(li);
                    bean.setHashWord(false);
                    bean.setLast(false);
                    list.add(bean);
                    ListChangePosition.getInstance().changeList(list,list.size()-2,list.size()-1);
                    notifyDataSetChanged();
                }
            });

        return vi;
    }

    class ViewHodler{
        @BindView(R.id.add_addText)TextView add_addText;//添加按钮
        @BindView(R.id.add_edit)EditText add_edit;//科室名称
        @BindView(R.id.add_delete)TextView add_delete;//取消添加科室
        @BindView(R.id.addDepartGridView)MyGridView addDepartGridView;//门诊的gridView
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
    }

}
