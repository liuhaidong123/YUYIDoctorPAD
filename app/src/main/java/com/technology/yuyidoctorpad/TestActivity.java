package com.technology.yuyidoctorpad;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.technology.yuyidoctorpad.Photo.PhotoPictureUtils;
import com.technology.yuyidoctorpad.Photo.PhotoRSCode;
import com.technology.yuyidoctorpad.lzhUtils.toast;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TestActivity extends AppCompatActivity implements PhotoPictureUtils.OnSavePictureListener{
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
                PhotoPictureUtils.getInstance().searchPicture(this);
                break;
            case R.id.img2:
                stat=2;
                PhotoPictureUtils.getInstance().searchPicture(this);
                break;
            case R.id.img3:
                stat=3;
                PhotoPictureUtils.getInstance().takePhoto(this);
                break;
            case R.id.myBtn:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode== PhotoRSCode.requestCode_SearchPermission){//选取图片的权限请求
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                PhotoPictureUtils.getInstance().searchPicture(this);
            }
            else {
                Toast.makeText(this,"请打开存储卡权限！",Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode==PhotoRSCode.requestCode_CameraPermission){//拍照的权限请求
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                PhotoPictureUtils.getInstance().takePhoto(this);
            }
            else {
                Toast.makeText(this,"请打开相机权限！",Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            switch (requestCode) {
                case PhotoRSCode.requestCode_Search://相册选取返回
                    PhotoPictureUtils.getInstance().savaPictureSearch(data.getData(),this,this);
                    break;
                case PhotoRSCode.requestCode_Camera://拍照
                    //cameraFile为保存后的文件，mImg：需要显示图片的ImageView
                    PhotoPictureUtils.getInstance().savaPictureCamera(this,this);
                    break;
            }
        }
    }
    @Override
    public void onSavePicture(boolean isSuccess, File result) {
        if (isSuccess){
            switch (stat){
                case 1:
                    outImage1=result;
                    img1.setImageBitmap(BitmapFactory.decodeFile(outImage1.getAbsolutePath()));
                    break;
                case 2:
                    outImage2=result;
                    img2.setImageBitmap(BitmapFactory.decodeFile(outImage2.getAbsolutePath()));
                    break;
                case 3:
                    outImage3=result;
                    img3.setImageBitmap(BitmapFactory.decodeFile(outImage3.getAbsolutePath()));
                    break;
            }
        }
       else {
            toast.toast(this,"无法获取到图片！");
        }
    }
}
