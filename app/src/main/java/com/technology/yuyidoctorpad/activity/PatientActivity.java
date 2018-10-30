package com.technology.yuyidoctorpad.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.technology.yuyidoctorpad.DbUtils.IDbUtlis;
import com.technology.yuyidoctorpad.Enum.IntentValue;
import com.technology.yuyidoctorpad.Net.gson;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.activity.PaintList.PaintMsgActivity;
import com.technology.yuyidoctorpad.code.ExitLogin;
import com.technology.yuyidoctorpad.fragment.paintFragment.Ipaint;
import com.technology.yuyidoctorpad.fragment.paintFragment.PaintListAdapter;
import com.technology.yuyidoctorpad.fragment.paintFragment.paintListBean;
import com.technology.yuyidoctorpad.fragment.paintFragment.paintPresenter;
import com.technology.yuyidoctorpad.lzhUtils.Empty;
import com.technology.yuyidoctorpad.lzhUtils.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PatientActivity extends AppCompatActivity implements Ipaint,MyListView.IonScrollBottomListener,AdapterView.OnItemClickListener{
    int start=0;
    int limit=10;
    boolean flag=false;
    paintPresenter presenter;
    PaintListAdapter adapter;
    List<paintListBean.RowsBean> list;
    @BindView(R.id.titleinclude_text)TextView titleV;
    @BindView(R.id.paintListView)MyListView paintListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        titleV= (TextView) findViewById(R.id.titleinclude_text);
        presenter=new paintPresenter();
        presenter.getList(this,start,limit);
        list=new ArrayList<>();
        adapter=new PaintListAdapter(this,list);
        paintListView= (MyListView) findViewById(R.id.paintListView);
        paintListView.setAdapter(adapter);
        paintListView.setOnScrollBottomListener(this);
        paintListView.setOnItemClickListener(this);
        titleV.setText("患者");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(this, PaintMsgActivity.class);
        intent.putExtra(IntentValue.PaintMsg,list.get(i));
        startActivity(intent);
    }

    @Override
    public void onError(String msg, String interfaceName) {
        if (flag==false){
            String resStr= IDbUtlis.getInstance().getOkhttpString(this,interfaceName);
            if (Empty.notEmpty(resStr)){
                try{
                    paintListBean bean= gson.gson.fromJson(resStr,paintListBean.class);
                    if (bean!=null){
                        initListView(bean);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    paintListView.setEmpey(msg);
                }
            }
        }
        paintListView.setLoadingComplete();
    }

    @Override
    public void onSuccess(paintListBean bean) {
        flag=true;//表示数据成功请求到过至少一次
        initListView(bean);
    }

    @Override
    public void onTokenError() {
        ExitLogin.getInstance().showLogin(this);
    }

    @Override
    public void onScrollBottom() {
        presenter.getList(this,start,limit);
    }

    private void  initListView(paintListBean bean){
        List<paintListBean.RowsBean>lis=bean.getRows();
        start+=lis.size();
        list.addAll(lis);
        adapter.notifyDataSetChanged();
        if (lis.size()!=limit){
            paintListView.setLoadingComplete();
        }
        else {
            paintListView.setScroll(true);
        }
    }
}
