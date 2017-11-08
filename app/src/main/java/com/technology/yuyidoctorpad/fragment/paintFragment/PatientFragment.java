package com.technology.yuyidoctorpad.fragment.paintFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.technology.yuyidoctorpad.DbUtils.IDbUtlis;
import com.technology.yuyidoctorpad.Enum.IntentValue;
import com.technology.yuyidoctorpad.Net.gson;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.activity.PaintList.PaintMsgActivity;
import com.technology.yuyidoctorpad.code.ExitLogin;
import com.technology.yuyidoctorpad.lzhUtils.Empty;
import com.technology.yuyidoctorpad.lzhUtils.MyListView;
import com.technology.yuyidoctorpad.lzhViews.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientFragment extends BaseFragment implements Ipaint,MyListView.IonScrollBottomListener,AdapterView.OnItemClickListener{
    int start=0;
    int limit=10;
    boolean flag=false;
    paintPresenter presenter;
    PaintListAdapter adapter;
    List<paintListBean.RowsBean>list;
    @BindView(R.id.titleinclude_text)TextView titleV;
    @BindView(R.id.paintListView)MyListView paintListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vi=inflater.inflate(R.layout.fragment_patient, container, false);
        unbinder= ButterKnife.bind(this,vi);
        presenter=new paintPresenter();
        presenter.getList(this,start,limit);
        list=new ArrayList<>();
        adapter=new PaintListAdapter(getActivity(),list);
        paintListView.setAdapter(adapter);
        paintListView.setOnScrollBottomListener(this);
        paintListView.setOnItemClickListener(this);
        titleV.setText("患者");
        return vi;
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(getActivity(), PaintMsgActivity.class);
        intent.putExtra(IntentValue.PaintMsg,list.get(i));
        startActivity(intent);
    }
    @Override
    public void onScrollBottom() {
        presenter.getList(this,start,limit);
    }

    @Override
    public void onError(String msg, String interfaceName) {
        if (flag==false){
            String resStr= IDbUtlis.getInstance().getOkhttpString(getActivity(),interfaceName);
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
        ExitLogin.getInstance().showLogin(getActivity());
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
