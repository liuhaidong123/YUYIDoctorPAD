package com.technology.yuyidoctorpad.activity.Hospital;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.activity.Hospital.Adapter.EditGridAdapter;
import com.technology.yuyidoctorpad.activity.Hospital.Listener.Iedit;
import com.technology.yuyidoctorpad.activity.Hospital.Listener.IonDeleteOld;
import com.technology.yuyidoctorpad.activity.Hospital.Presenter.HospitalEditPresenter;
import com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment.Depart.Bean.DepartmentBean;
import com.technology.yuyidoctorpad.lzhUtils.Empty;
import com.technology.yuyidoctorpad.lzhUtils.toast;
import com.technology.yuyidoctorpad.lzhViews.MyActivity;
import com.technology.yuyidoctorpad.lzhViews.MyGridView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

//intent.setClass(con, HospitalEditActivity.class);
//        intent.putExtra("data",list.get(pos));
public class HospitalEditActivity extends MyActivity implements Iedit,IonDeleteOld {
    int resultCode=100;//结果码
    DepartmentBean.ResultBean bean;
    boolean flag=false;
    boolean isEdit=false;
    HospitalEditPresenter presenter;
    String DeleteClinicId=""; //需要删除的门诊ID分号隔开
    String AddClinicName="";//需要添加的门诊名称分号隔开
    String departmentName="";//科室名称
    List<DepartmentBean.ResultBean.ClinicListBean>listChild;//gridView适配器
    EditGridAdapter adapter;
    @BindView(R.id.titleinclude_text)TextView title;
    @BindView(R.id.eidtDepartSubmit)TextView eidtDepartSubmit;
    @BindView(R.id.edit_edit)EditText edit_edit;//科室名称显示
    @BindView(R.id.editGridView)MyGridView editGridView;//menzhen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setChilidView(R.layout.activity_hospital_edit);
        title.setText("科室编辑");
        bean= (DepartmentBean.ResultBean) getIntent().getSerializableExtra("data");
        if (bean!=null){
         initView();
        }
        presenter=new HospitalEditPresenter();
    }

    private void initView() {
        listChild=new ArrayList<>();
        List<DepartmentBean.ResultBean.ClinicListBean>li=bean.getClinicList();
        if (li!=null&&li.size()>0){
            listChild.addAll(li);
        }
        DepartmentBean.ResultBean.ClinicListBean be=new DepartmentBean.ResultBean.ClinicListBean();
        be.setLast(true);//最后一项
        listChild.add(be);
        adapter=new EditGridAdapter(listChild,this,this);
        edit_edit.setText(bean.getDepartmentName());
        editGridView.setAdapter(adapter);
    }

    //删除原有的门诊项目（oldClinId：删除的id）
    @Override
    public void onDeleteOld(int oldClinId) {
        Log.e("删除id--",""+oldClinId);
        DeleteClinicId+=oldClinId+";";
    }
    @OnClick(R.id.eidtDepartSubmit)
    public void click(){
        if (isEdit){//返回
            setResult(resultCode);
            finish();
        }
        else {//保存编辑
            if (bean!=null){
                if (flag){
                    toast.toast(this,"正在操作，轻稍后！");
                }
                else {
                    departmentName=edit_edit.getText().toString();
                    AddClinicName="";//新增的项目的名字
                  if (Empty.notEmpty(departmentName)){//科室有名称
                        if (listChild.size()>1){//门诊 有内容
                            Set<String>se=new HashSet<>();//创建子item的集合
                            int success=0;//门诊中没有重复的并且名字没有空的 0:通过，1：门诊有空值，2门诊有重复项目
                            for (int i=0;i<listChild.size()-1;i++){//遍历除去最后一项的
                                if (!Empty.notEmpty(listChild.get(i).getClinicName())){//子项的名字有空的
                                    success=1;
                                    break;
                                }
                                else {
                                    if (listChild.get(i).isNew()){
                                        AddClinicName+=listChild.get(i).getClinicName()+";";
                                    }
                                }
                                se.add(Empty.notEmpty(listChild.get(i).getClinicName())?listChild.get(i).getClinicName():"empty");
                            }
                            if (se.size()!=listChild.size()-1){
                                success=2;
                            }
                            switch (success){
                                case 0://从公
                                    flag=true;
                                    presenter.submit(DeleteClinicId,AddClinicName,bean.getId()+"",departmentName,this);
                                    break;
                                case 1://空值
                                    toast.toast(this,"门诊名称不能为空！");
                                    break;
                                case 2://重复
                                    toast.toast(this,"门诊名称不能重复！");
                                    break;
                            }
                        }
                      else {//门诊没有内容
                            flag=true;
                            presenter.submit(DeleteClinicId,AddClinicName,bean.getId()+"",departmentName,this);
                        }
                  }
                    else {
                      toast.toast(this,"科室名称不能为空！");
                  }
                }
            }
            else {
                toast.toast(this,"当前科室信息不正确！");
            }
        }
    }

    @Override
    public void onSuccess() {
        flag=false;
        eidtDepartSubmit.setText("返回");
        isEdit=true;
        toast.toast(this,"更改成功！");
        listChild.remove(listChild.size()-1);
        adapter.setSuccess();
    }

    @Override
    public void onError(String msg) {
        flag=false;
        isEdit=false;
        toast.toast(this,msg);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction()==KeyEvent.KEYCODE_BACK){
            if (isEdit){
                setResult(resultCode);
            }
            finish();
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }


}
