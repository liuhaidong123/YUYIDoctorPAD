package com.technology.yuyidoctorpad;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.technology.yuyidoctorpad.PermissionCheck.PicturePhotoUtils;
import com.technology.yuyidoctorpad.code.RSCode;
import com.technology.yuyidoctorpad.lzhUtils.toast;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TestActivity extends AppCompatActivity {
    Unbinder unbinder;
    @BindView(R.id.img1)ImageView img1;
    @BindView(R.id.img2)ImageView img2;
    @BindView(R.id.img3)ImageView img3;
    int stat=0;
    File outImage1,outImage2,outImage3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        unbinder= ButterKnife.bind(this);
    }
    @OnClick({R.id.img1,R.id.img2,R.id.myBtn,R.id.img3})
    public void click(View vi){
        switch (vi.getId()){
            case R.id.img1:
                stat=1;
                outImage1= new File(getExternalFilesDir("DCIM").getAbsolutePath(), new Date().getTime() + "1.jpg");
                PicturePhotoUtils.getInstance().searchPhto(this,outImage1);
                break;
            case R.id.img2:
                stat=2;
                outImage2= new File(getExternalFilesDir("DCIM").getAbsolutePath(), new Date().getTime() + "2.jpg");
                PicturePhotoUtils.getInstance().searchPhto(this,outImage2);

                break;
            case R.id.img3:
                stat=3;
                outImage3= new File(getExternalFilesDir("DCIM").getAbsolutePath(), new Date().getTime() + "3.jpg");
                PicturePhotoUtils.getInstance().searchPhto(this,outImage3);
                break;
            case R.id.myBtn:
                sendMsg();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case RSCode.priCode_SearchPicture://图库
                if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    if (stat==1){
                        PicturePhotoUtils.getInstance().searchPhto(this,outImage1);
                    }
                    else if (stat==2){
                        PicturePhotoUtils.getInstance().searchPhto(this,outImage2);
                    }
                    else if(stat==3){
                        PicturePhotoUtils.getInstance().searchPhto(this,outImage3);
                    }
                }
                else {
                    toast.toast(this,"存储权限被禁用，无法获取相册信息");
                }
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case RSCode.rCode_SearchPicture://浏览相册
                    if (stat==1){
                        outImage1=new File(getExternalFilesDir("DCIM").getAbsolutePath(),new Date().getTime()+"1.jpg");
                        PicturePhotoUtils.getInstance().cutPhoto_Search(this,outImage1,data);
                    }
                    else if (stat==2){
                        outImage2=new File(getExternalFilesDir("DCIM").getAbsolutePath(),new Date().getTime()+"2.jpg");
                        PicturePhotoUtils.getInstance().cutPhoto_Search(this,outImage2,data);
                    }
                    else if (stat==3){
                        outImage3=new File(getExternalFilesDir("DCIM").getAbsolutePath(),new Date().getTime()+"3.jpg");
                        PicturePhotoUtils.getInstance().cutPhoto_Search(this,outImage3,data);
                    }
                    break;
                case RSCode.rCode_CutPicture://裁剪
                    try{
                        //将output_image.jpg对象解析成Bitmap对象，然后设置到ImageView中显示出来
                        if (stat==1){
                            Bitmap bitmap = BitmapFactory.decodeFile(outImage1.getAbsolutePath());
                            if (bitmap!=null){
                                img1.setImageBitmap(bitmap);
                            }
                            else {
                                Toast.makeText(this,"照片截取失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                      else if (stat==2){
                            Bitmap bitmap = BitmapFactory.decodeFile(outImage2.getAbsolutePath());
                            if (bitmap!=null){
                                img2.setImageBitmap(bitmap);
                            }
                            else {
                                Toast.makeText(this,"照片截取失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Bitmap bitmap = BitmapFactory.decodeFile(outImage3.getAbsolutePath());
                            if (bitmap!=null){
                                img3.setImageBitmap(bitmap);
                            }
                            else {
                                Toast.makeText(this,"照片截取失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(this,"照片截取失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    public void sendMsg(){
        Map<String,String>mp=new HashMap<>();
        mp.put("hospitalName","医院名称");
        mp.put("gradeString","1");
        mp.put("introduction","医院介绍");
        mp.put("administratorsName","管理员名称");
        mp.put("administratorsPosition","管理员职位");
        mp.put("administratorsTelephone","管理员电话号");
        mp.put("administratorsEmail","管理员邮箱");
        mp.put("address","医院地址");
        mp.put("tell","医院电话");
//        OkUtils.getFile(mp,outImage1,outImage2,outImage3,"http://192.168.1.44:8080/yuyi/hospital/AddHospitaInformation.do?").enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                e.printStackTrace();
//                Log.e("医院注册失败：","--------efawefw-----");
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                Log.e("医院注册：",response.body().string());
//            }
//        });
    }
}
