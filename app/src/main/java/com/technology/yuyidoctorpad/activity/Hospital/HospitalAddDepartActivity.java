package com.technology.yuyidoctorpad.activity.Hospital;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.TextView;

import com.technology.yuyidoctorpad.Net.gson;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.activity.Hospital.Adapter.AddDepartAdapter;
import com.technology.yuyidoctorpad.activity.Hospital.Bean.DepartBean;
import com.technology.yuyidoctorpad.activity.Hospital.Bean.DepartListBean;
import com.technology.yuyidoctorpad.activity.Hospital.Listener.Iadd;
import com.technology.yuyidoctorpad.activity.Hospital.Model.HospitalAddDepartModel;
import com.technology.yuyidoctorpad.code.RSCode;
import com.technology.yuyidoctorpad.lzhUtils.toast;
import com.technology.yuyidoctorpad.lzhViews.MyActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

//添加科室
public class HospitalAddDepartActivity extends MyActivity implements Iadd{
    HospitalAddDepartModel model;
    @BindView(R.id.addDepartSubmit)TextView addDepartSubmit;
    @BindView(R.id.titleinclude_text)TextView title;
    @BindView(R.id.addDepartListView)ListView addDepartListView;
    AddDepartAdapter adapter;
    List<DepartBean> list;
    boolean success=false;//本次是否添加成功
    boolean flag=false;//是否正在请求
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setChilidView(R.layout.activity_hospital_add_depart);
        title.setText("添加科室");
        init();
        model=new HospitalAddDepartModel();
    }
    //重新初始化
    public void init(){
        flag=false;
        list=new ArrayList<>();
        DepartBean bean=new DepartBean();
        bean.setHospitalId(Long.parseLong(User.HospitalId));
        bean.setCanNotOpen(false);
        bean.setLast(true);
        DepartBean.Clinic cli=new DepartBean.Clinic();
        cli.setLast(true);
        List<DepartBean.Clinic>li=new ArrayList<>();
        li.add(cli);
        bean.setClinicList(li);
        list.add(bean);
        adapter=new AddDepartAdapter(list,this);
        addDepartListView.setAdapter(adapter);
    }
    //添加成功
    @Override
    public void onSuccess() {
        success=true;
        addDepartSubmit.setText("返回");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (success){
                setResult(RSCode.resultCode);
                }
            finish();
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    //添加失败
    @Override
    public void onError(String msg) {
        toast.toast(this,msg);
        flag=false;
        DepartBean bean=new DepartBean();
        bean.setHospitalId(Long.parseLong(User.HospitalId));
        bean.setCanNotOpen(false);
        bean.setLast(true);
        DepartBean.Clinic cli=new DepartBean.Clinic();
        cli.setLast(true);
        List<DepartBean.Clinic>li=new ArrayList<>();
        li.add(cli);
        bean.setClinicList(li);
        list.add(bean);
        for (int i=0;i<list.size()-1;i++){//去除最后一个
            list.get(i).setCanNotOpen(false);//false可以编辑
            DepartBean.Clinic b=new DepartBean.Clinic();
            b.setLast(true);
            List<DepartBean.Clinic>lit=list.get(i).getClinicList();
            if (lit==null){
                lit=new ArrayList<>();
            }
            lit.add(b);
            list.get(i).setClinicList(lit);
        }
        adapter.notifyDataSetChanged();
    }
    //部分添加成功:list存放没有成功的醒目的科室名称集合
    @Override
    public void onPartError(List<String> listPart) {
        toast.toast(this,"不能添加已存在的科室！");
        for (int i=0;i<listPart.size();i++){
            String name=listPart.get(i);
            for (int j=0;j<list.size();j++){
                if (name.equals(list.get(j).getDepartmentName())){
                    list.get(j).setIsErroPart(true);
                }
            }
        }
        adapter.setSuccess();
        adapter.notifyDataSetChanged();
        success=true;
        addDepartSubmit.setText("返回");
    }

    //提交成功后此按钮会变成返回健
    @OnClick(R.id.addDepartSubmit)
    public void click(){
        if (success){//已经提交成功
            setResult(RSCode.resultCode);
            finish();
        }
        else {
            if (flag){
                toast.toast(this,"正在提交，请稍后。。。");
            }
            else {
                int result=isSuccess();
                String str="";
                switch (result){
                    case 0://0成功
                        str="成功";
                        list.remove(list.size()-1);
                        for (int i=0;i<list.size();i++){
                            list.get(i).setCanNotOpen(true);//不可编辑状态
                            list.get(i).getClinicList().remove(list.get(i).getClinicList().size()-1);
                        }
                        adapter.notifyDataSetChanged();
                        flag=true;
                        DepartListBean be=new DepartListBean();
                        be.setList(list);
                        String json= gson.gson.toJson(be);
                        Log.e("json",json);
                        model.submit(json,this);
                        break;
                    case 1://1科室名称不全
                        str="科室名称不能为空！";
                        break;
                    case 2://2门诊名称不全
                        str="门诊名称不能为空！";
                        break;
                    case 3://3科室名称重复
                        str="科室名称重复！";
                        break;
                    case 4://4，门诊名称重复
                        str="门诊名称重复！";
                        break;
                    case 5://5.没有添加过科室
                        str="您还没有添加过科室！";
                        break;
                }
                if (result!=0){
                    toast.toast(this,str);
                }
            }
        }
    }
    //补全所有的科室edit以及门诊的editext的文字
    //返回值说明：0成功，1科室名称不全，2门诊名称不全，3科室名称重复，4，门诊名称重复5.没有添加过科室（用户没有点击添加）
    private int isSuccess(){
        int flag=0;
        if (list.size()==1){
            flag=5;//没有添加过科室
        }
        else {
            for (int i = 0; i < list.size() - 1; i++) {//去除最后一项
                if (!list.get(i).isLast()) {//不是最后一项
                    if (!list.get(i).isHashWord()) {
                        flag = 1;//科室名称不全
                        break;
                    } else {
                        for (int j = 0; j < list.get(i).getClinicList().size(); j++) {
                            if (!list.get(i).getClinicList().get(j).isLast()) {//不是最后一项
                                if (!list.get(i).getClinicList().get(j).isHashWord()) {//门诊没有填写文字
                                    flag = 2;//门诊名称不全
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        if (flag==0){
            Set<String>parent=new HashSet<>();
            for (int i=0;i<list.size();i++){
                if (!list.get(i).isLast()){//不是最后一项
                    parent.add(list.get(i).getDepartmentName());
                }
                else {
                    parent.add("empty");
                }
                Set<String>child=new HashSet<>();
                for (int j=0;j<list.get(i).getClinicList().size();j++){
                    if (!list.get(i).getClinicList().get(j).isLast()){
                        child.add(list.get(i).getClinicList().get(j).getClinicName());
                    }
                    else {
                        child.add("empty");
                    }
                }
                if (child.size()!=list.get(i).getClinicList().size()){
                    for (String s:parent){
                        Log.e("门诊",s);
                    }
                    for (int j=0;j<list.get(i).getClinicList().size();j++){
                        Log.e("门诊list",list.get(i).getClinicList().get(j).getClinicName()==null?"最后一个":list.get(i).getClinicList().get(j).getClinicName());
                    }
                    Log.e("position==",i+"");
                    flag=4;//门诊重复
                }
            }
            if (parent.size()!=list.size()){
                for (String s:parent){
                    Log.e("科室",s);
                }
                for (int i=0;i<list.size();i++){
                    Log.e("科室list",list.get(i).getDepartmentName()==null?"最后一个":list.get(i).getDepartmentName());
                }
                flag=3;//科室重复
            }
        }
        return flag;
    }


}
