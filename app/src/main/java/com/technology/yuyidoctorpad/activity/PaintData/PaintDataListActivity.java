package com.technology.yuyidoctorpad.activity.PaintData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.technology.yuyidoctorpad.Enum.IntentValue;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.activity.PaintData.Adapter.My_paintDataList_Adapter;
import com.technology.yuyidoctorpad.activity.PaintData.Bean.Bean_MyPaintList;
import com.technology.yuyidoctorpad.activity.PaintData.IView.IPaintList;
import com.technology.yuyidoctorpad.activity.PaintData.Model.PaintDataListModel;
import com.technology.yuyidoctorpad.activity.PaintList.PaintMsgActivity;
import com.technology.yuyidoctorpad.lzhUtils.MyListView;
import com.technology.yuyidoctorpad.lzhViews.MyActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

//患者数据
public class PaintDataListActivity extends MyActivity implements MyListView.IonScrollBottomListener,IPaintList,AdapterView.OnItemClickListener{
    @BindView(R.id.paintList_ListView)MyListView paintList_ListView;
    @BindView(R.id.titleinclude_text)TextView title;
    My_paintDataList_Adapter adapter;
    List<Bean_MyPaintList.RowsBean>list;
    int start=0;
    int limit=10;
    PaintDataListModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setChilidView(R.layout.activity_paint_data_list);
        title.setText("患者数据");
        list=new ArrayList<>();
        adapter=new My_paintDataList_Adapter(this,list);
        paintList_ListView.setAdapter(adapter);
        paintList_ListView.setOnScrollBottomListener(this);
        paintList_ListView.setOnItemClickListener(this);
        model=new PaintDataListModel();
        model.getPaintList(start,limit,this);
    }
    @OnClick(R.id.paintList_SearchLayout)
    public void click(View view){
        switch (view.getId()){
            case R.id.paintList_SearchLayout://搜索
                startActivity(new Intent(this,PaintDataSearchActivity.class));
                break;
        }
    }

    @Override
    public void onScrollBottom() {
        model.getPaintList(start,limit,this);
    }

    @Override
    public void onGetPaintListError(String msg) {
        paintList_ListView.setEmpey(msg);
    }

    @Override
    public void onGetPaintListSuccess(Bean_MyPaintList bean) {
        if (bean.getRows()!=null&&bean.getRows().size()>0){
            start+=bean.getRows().size();
            list.addAll(bean.getRows());
            adapter.notifyDataSetChanged();
            if (bean.getRows().size()==limit){
                paintList_ListView.setScroll(true);
            }
            else {
                paintList_ListView.setLoadingComplete();
            }
        }
        else {
            paintList_ListView.setLoadingComplete();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent=new Intent();
            intent.setClass(this, PaintMsgActivity.class);
            intent.putExtra(IntentValue.PaintMsg,BeanUtils.getBean(list.get(i)));
            startActivity(intent);
    }
}
