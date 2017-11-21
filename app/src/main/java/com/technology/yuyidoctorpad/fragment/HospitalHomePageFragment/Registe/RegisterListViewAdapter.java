package com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Registe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technology.yuyidoctorpad.Net.Ip;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.lhdUtils.RoundImageView;
import com.technology.yuyidoctorpad.lzhUtils.Empty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wanyu on 2017/11/18.
 */

public class RegisterListViewAdapter extends BaseAdapter{
    List<DoctorListBean.ResultBean>list;
    Context con;
    boolean tp=false;//true下午，false上午
    IonSelect isAllSelct;
    public RegisterListViewAdapter(List<DoctorListBean.ResultBean>list,Context con,IonSelect isAllSelct){
        this.list=list;
        this.con=con;
        this.isAllSelct=isAllSelct;
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
            view= LayoutInflater.from(con).inflate(R.layout.doclist,null);
            hodler=new ViewHodler(view);
            view.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) view.getTag();
        }
        hodler.docList_img.setTag(i);
        hodler.docList_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos= (int) view.getTag();
                view.setSelected(!view.isSelected());
                list.get(pos).setCanSelect(view.isSelected());
                isAllSelct.isAllSelect(isAll());
                notifyDataSetChanged();
            }
        });
        Picasso.with(con).load(Ip.imagePath+list.get(i).getAvatar()).placeholder(R.mipmap.erroruser).error(R.mipmap.erroruser).into(hodler.docList_photo);
        hodler.docList_name.setText(Empty.notEmpty(list.get(i).getTrueName())?list.get(i).getTrueName():"姓名");
        hodler.docList_Job.setText(Empty.notEmpty(list.get(i).getTitle())?list.get(i).getTitle():"职称");
        hodler.docList_KS.setText(Empty.notEmpty(list.get(i).getDepartmentName())?list.get(i).getDepartmentName():"科室");
        hodler.docList_registerNum.setText(tp?list.get(i).getAfterNum()+"":list.get(i).getBeforNum()+"");
        hodler.docList_img.setSelected(list.get(i).isCanSelect());
        return view;
    }

    class ViewHodler{
        @BindView(R.id.docList_registerNumX)TextView docList_registerNum;
        @BindView(R.id.docList_KS)TextView docList_KS;//科室名称
        @BindView(R.id.docList_Job)TextView   docList_Job;//职称
        @BindView(R.id.docList_name)TextView docList_name;//姓名
        @BindView(R.id.docList_photo)RoundImageView docList_photo;//头像
        @BindView(R.id.docList_img)ImageView docList_img;//选定按钮
        public ViewHodler(View vi){
            ButterKnife.bind(this,vi);
        }
    }
    //是否处于全选状态
    public interface IonSelect{
        void isAllSelect(boolean flag);
    }

    private boolean isAll(){
        boolean flag=true;
        for (int i=0;i<list.size();i++){
            if (list.get(i).isCanSelect()==false){
                flag=false;
                break;
            }
        }
        return flag;
    }
    //true下午，false上午
    public void setAfterNoon(boolean tp)
    {
        this.tp=tp;
        notifyDataSetChanged();
    }
}
