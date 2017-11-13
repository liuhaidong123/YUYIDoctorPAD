package com.technology.yuyidoctorpad.activity.MyPraise;

import android.os.Bundle;
import android.widget.TextView;

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.lzhUtils.MyListView;
import com.technology.yuyidoctorpad.lzhViews.MyActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyPraiseActivity extends MyActivity implements IPraise ,MyListView.IonScrollBottomListener{
    MyPraiseModel model;
    int start=0;
    int limit=10;
    @BindView(R.id.titleinclude_text)TextView titleinclude_text;
    @BindView(R.id.myPraiseListview)MyListView myPraiseListview;
    private List<Bean_MyPraise.RowsBean> list;
    private My_PraiseAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setChilidView(R.layout.activity_my_praise);
        titleinclude_text.setText("我的点赞");
        list=new ArrayList<>();
        adapter=new My_PraiseAdapter(list,MyPraiseActivity.this);
        myPraiseListview.setAdapter(adapter);
        myPraiseListview.setOnScrollBottomListener(this);
        model=new MyPraiseModel();
        start=list.size();
        model.getPraiseData(start,limit,this);
    }

    @Override
    public void onSuccess(Bean_MyPraise bean) {
            if (bean.getRows()!=null&&bean.getRows().size()>0){
                start+=bean.getRows().size();
                if (bean.getRows().size()!=limit){
                    myPraiseListview.setLoadingComplete();
                }
                else {
                    myPraiseListview.setScroll(true);
                }
                list.addAll(bean.getRows());
                adapter.notifyDataSetChanged();
            }
            else {
                myPraiseListview.setEmpey("没有查询到数据！");
                myPraiseListview.setLoadingComplete();
            }
    }

    @Override
    public void onError(String msg) {
        myPraiseListview.setEmpey(msg);
        myPraiseListview.setLoadingComplete();
    }

    @Override
    public void onScrollBottom() {
        start=list.size();
        model.getPraiseData(start,limit,this);
    }
}
