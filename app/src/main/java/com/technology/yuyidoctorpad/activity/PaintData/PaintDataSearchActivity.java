package com.technology.yuyidoctorpad.activity.PaintData;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.technology.yuyidoctorpad.Enum.IntentValue;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.activity.PaintData.Adapter.My_paintDataList_Adapter;
import com.technology.yuyidoctorpad.activity.PaintData.Bean.Bean_MyPaintList;
import com.technology.yuyidoctorpad.activity.PaintData.IView.IPaintList;
import com.technology.yuyidoctorpad.activity.PaintData.Model.PaintDataListModel;
import com.technology.yuyidoctorpad.activity.PaintList.PaintMsgActivity;
import com.technology.yuyidoctorpad.lhdUtils.ToastUtils;
import com.technology.yuyidoctorpad.lzhUtils.Empty;
import com.technology.yuyidoctorpad.lzhUtils.MyListView;
import com.technology.yuyidoctorpad.lzhUtils.toast;
import com.technology.yuyidoctorpad.lzhViews.MyActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.R.attr.id;

public class PaintDataSearchActivity extends MyActivity implements IPaintList,MyListView.IonScrollBottomListener,AdapterView.OnItemClickListener{
    My_paintDataList_Adapter adapter;
    List<Bean_MyPaintList.RowsBean> list;
    int start=0;
    int limit=10;
    PaintDataListModel model;
    String content;
    @BindView(R.id.paintSearch_ListView)MyListView paintSearch_ListView;
    @BindView(R.id.paintSearch_edit)EditText paintSearch_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setChilidView(R.layout.activity_paint_data_search);
        list=new ArrayList<>();
        model=new PaintDataListModel();
        adapter=new My_paintDataList_Adapter(this,list);
        paintSearch_ListView.setAdapter(adapter);
        paintSearch_ListView.setOnScrollBottomListener(this);
        paintSearch_ListView.setFooterViewVisiable(false);
        paintSearch_ListView.setOnItemClickListener(this);
        paintSearch_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    if (!Empty.notEmpty(paintSearch_edit.getText().toString().trim())) {
                        toast.toast(PaintDataSearchActivity.this,"请输入搜索内容！");
                    } else {
                        if (id!=-1){
                            start=0;
                            list.clear();
                            adapter.notifyDataSetChanged();
                            paintSearch_ListView.setFooterViewVisiable(true);
                            paintSearch_ListView.setLoading();
                            content=paintSearch_edit.getText().toString();
                            model.getPaintListContent(start,limit,content,PaintDataSearchActivity.this);
                            //隐藏软键盘
                            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        }else {
                            ToastUtils.myToast(PaintDataSearchActivity.this, "搜索数据错误");
                        }

                    }
                    return true;
                }

                return false;
            }
        });
    }
    @OnClick(R.id.paintSearch_cancle)
    public void click(){
        finish();
    }
    @Override
    public void onGetPaintListError(String msg) {
        paintSearch_ListView.setEmpey(msg);
    }

    @Override
    public void onGetPaintListSuccess(Bean_MyPaintList bean) {
        if (bean.getRows()!=null&&bean.getRows().size()>0){
            start+=bean.getRows().size();
            list.addAll(bean.getRows());
            adapter.notifyDataSetChanged();
            if (bean.getRows().size()==limit){
                paintSearch_ListView.setScroll(true);
            }
            else {
                paintSearch_ListView.setLoadingComplete();
            }
        }
        else {
            paintSearch_ListView.setLoadingComplete();
        }
    }

    @Override
    public void onScrollBottom() {
        model.getPaintListContent(start,limit,content,this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent();
        intent.setClass(this, PaintMsgActivity.class);
        intent.putExtra(IntentValue.PaintMsg,BeanUtils.getBean(list.get(i)));
        startActivity(intent);
    }
}
