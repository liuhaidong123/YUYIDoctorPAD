package com.technology.yuyidoctorpad.activity.Hospital.Adapter;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.activity.Hospital.Listener.IonDeleteOld;
import com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Depart.Bean.DepartmentBean;
import com.technology.yuyidoctorpad.lzhUtils.Empty;
import com.technology.yuyidoctorpad.lzhUtils.ListChangePosition;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/11/17.
 */

public class EditGridAdapter extends BaseAdapter{
    List<DepartmentBean.ResultBean.ClinicListBean> list;
    Context con;
    boolean flag=false;//是否不可编辑（true不显示编辑，只能查看）
    IonDeleteOld listener;
    public EditGridAdapter(List<DepartmentBean.ResultBean.ClinicListBean> list, Context con, IonDeleteOld listener){
        this.list=list;
        this.con=con;
        this.listener=listener;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHodler hodler;
        boolean isLast=list.get(i).isLast();
        View vi= LayoutInflater.from(con).inflate(R.layout.edit_gridview,null);
        hodler=new ViewHodler(vi);
        if (isLast) {//最后一项，显示添加按钮
            hodler.addClin_text.setVisibility(View.VISIBLE);//显示添加按钮
            hodler.addClin_layout.setVisibility(View.GONE);//隐藏编辑的layout
        }
        else {
            hodler.addClin_edit.setText(list.get(i).getClinicName());
            hodler.addClin_text.setVisibility(View.GONE);//隐藏添加
            hodler.addClin_layout.setVisibility(View.VISIBLE);//显示编辑的layout
            if (flag){//不可编辑状态
                hodler.addClin_delete.setVisibility(View.GONE);
                hodler.addClin_edit.setInputType(InputType.TYPE_NULL);
            }
            else{//可编辑状态
                hodler.addClin_delete.setVisibility(View.VISIBLE);
                if (list.get(i).isNew()){//新增项目可以更改
                    hodler.addClin_edit.setInputType(InputType.TYPE_CLASS_TEXT);
                    hodler.addClin_edit.setFocusableInTouchMode(true);
                }
                else {//原有项目不能改名字，只能删除
                    hodler.addClin_edit.setInputType(InputType.TYPE_NULL);
                    hodler.addClin_edit.setFocusableInTouchMode(false);
                }
            }
            }
        hodler.addClin_edit.addTextChangedListener(new TextWatcher() {
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
                    list.get(i).setClinicName(text);
                }
                else {
                    list.get(i).setClinicName("");
                }
            }
        });
        hodler.addClin_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DepartmentBean.ResultBean.ClinicListBean be=new DepartmentBean.ResultBean.ClinicListBean();
                be.setLast(false);
                be.setNew(true);
                list.add(be);
                ListChangePosition.getInstance().changeList(list,list.size()-2,list.size()-1);
                notifyDataSetChanged();
            }
        });
        hodler.addClin_delete.setTag(i);
        hodler.addClin_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ps= (int) view.getTag();
                if (list.get(ps).getId()!=0&&list.get(ps).isNew()==false){
                    listener.onDeleteOld(list.get(ps).getId());
                }
                list.remove(ps);
                notifyDataSetChanged();
            }
        });
        return vi;
    }
    public void setSuccess(){
        flag=true;
        notifyDataSetChanged();
    }
    class ViewHodler{
        @BindView(R.id.editClin_text)TextView addClin_text;//点击添加
        @BindView(R.id.editClin_layout)RelativeLayout addClin_layout;//editext所在的layout
        @BindView(R.id.editClin_delete)ImageView addClin_delete;//删除按钮
        @BindView(R.id.editClin_edit)EditText addClin_edit;//科室的输入框
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
    }
}
