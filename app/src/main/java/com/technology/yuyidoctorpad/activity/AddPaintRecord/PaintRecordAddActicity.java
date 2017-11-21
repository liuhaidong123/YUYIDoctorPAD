package com.technology.yuyidoctorpad.activity.AddPaintRecord;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.technology.yuyidoctorpad.Enum.UserSex;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.activity.AddPaintRecord.PhotoSelect.Post_SelectPhotoActivity;
import com.technology.yuyidoctorpad.lzhUtils.Empty;
import com.technology.yuyidoctorpad.lzhUtils.ListChangePosition;
import com.technology.yuyidoctorpad.lzhUtils.toast;
import com.technology.yuyidoctorpad.lzhViews.MyActivity;
import com.technology.yuyidoctorpad.lzhViews.MyGridView;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

//病历添加
public class PaintRecordAddActicity extends MyActivity implements IpaintAdd,AdapterView.OnItemClickListener{
    private final int RequestCode = 100;
    private final int ResultCode = 200;
    PaintRecordAddPresenter presenter;
    @BindView(R.id.titleinclude_text)TextView title;
    @BindView(R.id.add_editVname)EditText add_editVname;//姓名
    @BindView(R.id.add_Tsex)TextView add_Tsex;//性别
    @BindView(R.id.add_sexRela)View add_sexRela;
    @BindView(R.id.add_editVage)EditText add_editVage;//年龄
    @BindView(R.id.add_Tphone)EditText add_Tphone;//手机号
    @BindView(R.id.add_recordNum)TextView add_recordNum;
    @BindView(R.id.add_editRecord)EditText add_editRecord;
    @BindView(R.id.myGridView)MyGridView myGridView;//病历报告的gridview
    private PostListAdapter adapter;
    private List<String> list;//list存放图片路径
    UserSex se=UserSex.GIRL;//默认女
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setChilidView(R.layout.activity_paint_record_add_acticity);
        title.setText("病历添加");
        presenter=new PaintRecordAddPresenter(this,this);
        list=new ArrayList<>();
        list.add("end");
        adapter=new PostListAdapter(this,list);
        myGridView.setAdapter(adapter);
        myGridView.setOnItemClickListener(this);
        add_editRecord.addTextChangedListener(new TextWatcher() {
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
                    int length=text.length();
                    add_recordNum.setText(length+"/1000");
                }
                else {
                    add_recordNum.setText("0/1000");
                }
            }
        });
    }
    @OnClick({R.id.add_sexRela,R.id.submit})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.add_sexRela://年龄选择
                presenter.ShowWindowSex(this,add_sexRela);
                break;
            case R.id.submit://tijao
                List<String>liResult=new ArrayList<>();
                liResult.addAll(list);
                if (liResult.size()>0){
                    liResult.remove(liResult.size()-1);
                }
                presenter.addRecord(add_Tphone.getText().toString(),add_editVname.getText().toString(),add_editVage.getText().toString(),
                        UserSex.UserSexToInt(se)+"",add_editRecord.getText().toString(), User.DocId,liResult);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ResultCode) {
            if (requestCode == RequestCode) {
                if (data != null) {
                    List<String> li = data.getStringArrayListExtra("data");
                    if (li!=null){
                        if (li!=null&&li.size()>0){
                            LinkedHashSet<String> hashSet=new LinkedHashSet<>();
                            hashSet.addAll(list);
                            hashSet.addAll(li);
                            list.clear();
                            list.add("end");
                            for (String s:hashSet){
                                for (int i=0;i<li.size();i++){
                                    if (s.equals(li.get(i))){
                                        Log.e("图片地址",s);
                                        list.add(s);
                                    }
                                }
                            }
                            ListChangePosition.getInstance().changeList(list,0,list.size()-1);
                            adapter.notifyDataSetChanged();
                        }
                        else {
                            list.clear();
                            list.add("end");
                            adapter.notifyDataSetChanged();
                        }
                    }

                }
            }
        }

    }


    @Override
    public void onSexSelect(UserSex sex) {
        this.se=sex;
        add_Tsex.setText(UserSex.getSexName(se));
    }

    @Override
    public void onAddError(String msg) {
        toast.toast(this,msg);
    }

    @Override
    public void onAddSuccess() {
        toast.toast(this,"添加成功！");
    }
    //
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (i==list.size()-1){//最后一项
//                if (list.size()==4){
//                    toast.toast(this,"最多可以选择3张图片！");
//                }
//                else {
                    Intent intent = new Intent();
                    intent.setClass(this, Post_SelectPhotoActivity.class);
                    List<String>li=new ArrayList<>();
                    li.addAll(list);
                    li.remove(li.size()-1);
                    intent.putStringArrayListExtra("data", (ArrayList<String>) li);
                    startActivityForResult(intent, RequestCode);
//                }
            }
    }
}
