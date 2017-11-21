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
import com.technology.yuyidoctorpad.activity.Hospital.Bean.DepartBean;
import com.technology.yuyidoctorpad.lzhUtils.Empty;
import com.technology.yuyidoctorpad.lzhUtils.ListChangePosition;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/11/16.
 */

public class addDepartList_GridViewAdapter extends BaseAdapter{
    List<DepartBean.Clinic> list;
    Context con;
    boolean flag;//是否不可编辑（true不显示编辑，只能查看）
    public addDepartList_GridViewAdapter( List<DepartBean.Clinic> list,Context con,boolean flag){
        this.list=list;
        this.con=con;
        this.flag=flag;
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
           View vi= LayoutInflater.from(con).inflate(R.layout.depart_list_grid_item,null);
            hodler=new ViewHodler(vi);
        if (isLast){//最后一项：添加按钮
            hodler.addClin_layout.setVisibility(View.GONE);
            hodler.addClin_text.setVisibility(View.VISIBLE);
        }
        else {
            hodler.addClin_layout.setVisibility(View.VISIBLE);
            hodler.addClin_text.setVisibility(View.GONE);
        }
        if (flag){//不可编辑
            hodler.addClin_edit.setInputType(InputType.TYPE_NULL);
            hodler.addClin_delete.setVisibility(View.GONE);
            }
        else {
            hodler.addClin_delete.setVisibility(View.VISIBLE);
            hodler.addClin_edit.setInputType(InputType.TYPE_CLASS_TEXT);
        }

            hodler.addClin_edit.setText(list.get(i).getClinicName());
            hodler.addClin_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.remove(i);
                    notifyDataSetChanged();
                }
            });
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
                        list.get(i).setHashWord(true);
                    }
                    else {
                        list.get(i).setClinicName("");
                        list.get(i).setHashWord(false);
                        }
                }
            });
        hodler.addClin_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DepartBean.Clinic cli=new DepartBean.Clinic();
                cli.setLast(false);
                list.add(cli);
                ListChangePosition.getInstance().changeList(list,list.size()-2,list.size()-1);
                notifyDataSetChanged();
            }
        });
        return vi;
    }

    class ViewHodler{
        @BindView(R.id.addClin_text)TextView addClin_text;//点击添加
        @BindView(R.id.addClin_layout)RelativeLayout addClin_layout;//editext所在的layout
        @BindView(R.id.addClin_delete)ImageView addClin_delete;//删除按钮
        @BindView(R.id.addClin_edit)EditText addClin_edit;//科室的输入框
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
    }
}
