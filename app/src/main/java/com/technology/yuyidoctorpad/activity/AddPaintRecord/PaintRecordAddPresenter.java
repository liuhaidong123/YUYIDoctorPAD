package com.technology.yuyidoctorpad.activity.AddPaintRecord;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.technology.yuyidoctorpad.Enum.UserSex;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.lzhUtils.BitMapUtils;
import com.technology.yuyidoctorpad.lzhUtils.Empty;
import com.technology.yuyidoctorpad.lzhUtils.PhoneUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanyu on 2017/11/20.
 */

public class PaintRecordAddPresenter {
    PaintRecordAddModel model;
    IpaintAdd listener;
    PopupWindow popSucc;
    Context con;
    public PaintRecordAddPresenter( IpaintAdd listener,  Context con) {
     this.listener=listener;
        this.con=con;
        model=new PaintRecordAddModel();
    }

//    telethone      String      病人电话
//    name        String      病人姓名
//    age        String      病人年龄
//    gender        String      病人性别（0女1男）
//    medicalrecord      String      病人病历
//    physicianId      String      医生ID
//    返回值：   返回值名称                返回值类型    备注
//    code              0成功-1失败
    public void addRecord(String telethone, String name, String age, String  gender, String medicalrecord, String physicianId, List<String>list){
        if (Empty.notEmpty(telethone)){
            if (PhoneUtils.isPhone(telethone)){
                if (Empty.notEmpty(name)){
                    if (Empty.notEmpty(medicalrecord)){
                        if (Empty.notEmpty(age)){
                            if (Integer.parseInt(age)>0&&Integer.parseInt(age)<=110){
                                List<File>li=new ArrayList<>();
                                if (list!=null&&list.size()>0){
                                    Log.i("----list.size----",""+list.size());
                                    for (int i=0;i<list.size();i++){
                                        Bitmap bt= BitMapUtils.resizeImage2(Uri.fromFile(new File(list.get(i))),1000,1000);
                                        File f=saveFile(bt,i+"");
                                        if (f!=null){
                                            li.add(f);
                                        }
                                        else {
                                            listener.onAddError("图片上传失败！");
                                            return;
                                        }
                                    }
                                }

                                model.addRecord( telethone,  name,  age,   gender,  medicalrecord,  physicianId, li,listener);
                            }
                            else {
                                listener.onAddError("年龄不正确！");
                            }
                        }
                        else {
                            listener.onAddError("年龄不能为空！");
                        }
                    }
                    else {
                        listener.onAddError("病历描述不能为空！");
                    }
                }
                else {
                    listener.onAddError("姓名不能为空！");
                }
            }
            else {
                listener.onAddError("电话号码不正确！");
            }
        }
        else {
            listener.onAddError("电话不能为空！");
        }
    }
    //弹窗选择性别
    public void ShowWindowSex(final Activity ac,View parent){
        if (popSucc==null){
            popSucc=new PopupWindow();
        }

        popSucc = new PopupWindow();
        View v = LayoutInflater.from(ac).inflate(R.layout.departpop, null);
        ListView listView=v.findViewById(R.id.listView);
        ViewGroup.LayoutParams paramsd=listView.getLayoutParams();
        paramsd.width=parent.getWidth();
        paramsd.height= WindowManager.LayoutParams.WRAP_CONTENT;
        listView.setLayoutParams(paramsd);
        List<String>li=new ArrayList<>();
        li.add("女");
        li.add("男");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ac, android.R.layout.simple_list_item_1, li);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        listener.onSexSelect(UserSex.GIRL);
                        break;
                    case 1:
                        listener.onSexSelect(UserSex.BOY);
                        break;
                }
                popSucc.dismiss();
            }
        });
        ac.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = ac.getWindow().getAttributes();
        params.alpha = 0.8f;
        ac.getWindow().setAttributes(params);

        popSucc.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popSucc.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);

        popSucc.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popSucc.setContentView(v);
        popSucc.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        popSucc.setTouchable(true);
        popSucc.setFocusable(true);
        popSucc.setOutsideTouchable(true);


        popSucc.setAnimationStyle(R.style.popup4_anim);
        popSucc.showAsDropDown(parent,-35,10);
        popSucc.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ac. getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params = ac.getWindow().getAttributes();
                params.alpha = 1f;
                ac. getWindow().setAttributes(params);
            }
        });
    }


    public File saveFile(Bitmap bm, String fileName){
        File file=null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){//可写
            file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES),fileName+".jpg");
        }
        else {
            file=new File(con.getFilesDir(),fileName);
        }

        try{
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            Log.i("--file-----",file.length()/1024+"");
            return file;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
