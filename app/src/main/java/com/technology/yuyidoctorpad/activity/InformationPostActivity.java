package com.technology.yuyidoctorpad.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.technology.yuyidoctorpad.HttpTools.HttpTools;
import com.technology.yuyidoctorpad.Photo.PhotoPictureUtils;
import com.technology.yuyidoctorpad.Photo.PhotoRSCode;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.bean.HospitalInformationPost.Root;
import com.technology.yuyidoctorpad.lhdUtils.MyDialog;
import com.technology.yuyidoctorpad.lhdUtils.ToastUtils;
import com.technology.yuyidoctorpad.lhdUtils.UserInfoPresenter2;
import com.technology.yuyidoctorpad.lzhUtils.toast;

import net.tsz.afinal.http.AjaxParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;

public class InformationPostActivity extends AppCompatActivity implements View.OnClickListener ,PhotoPictureUtils.OnSavePictureListener{
    private ImageView mAdd_img;
    private EditText mTitile_edit, mSmallTitle_edit, mContent_edit;
    private TextView mYuYi_tv, mYuYiDoctor_tv, mSubmit_tv, mSmall_Title_Change_Tv, mBig_Title_Change_Tv, mContent_Change_Tv;
    private int mWayFlag = 1;
    private UserInfoPresenter2 presenter;
    private File outImage;
    private File lastFile;
    private boolean isPhotoChange = false;
    private RelativeLayout mData_rl;
    private HttpTools httpTools;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 62) {
                MyDialog.stopDia();
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root != null) {
                        if (root.getCode().equals("0")) {
                            ToastUtils.myToast(getApplicationContext(), "提交审核成功");
                            finish();
                        } else {
                            ToastUtils.myToast(getApplicationContext(), root.getMessage());
                        }
                    }
                } else {
                    ToastUtils.myToast(getApplicationContext(), "发布失败");
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_post);
        presenter = new UserInfoPresenter2();
        httpTools = HttpTools.getHttpToolsInstance();
        initUI();
    }

    private void initUI() {

        mData_rl = (RelativeLayout) findViewById(R.id.data_rl);
        mAdd_img = (ImageView) findViewById(R.id.post_img_btn);
        mAdd_img.setOnClickListener(this);
        mTitile_edit = (EditText) findViewById(R.id.post_title_edit);
        mSmallTitle_edit = (EditText) findViewById(R.id.post_small_title_edit);
        mContent_edit = (EditText) findViewById(R.id.post_content_edit);
        mSmall_Title_Change_Tv= (TextView) findViewById(R.id.post_title_small_lenth);
        mBig_Title_Change_Tv= (TextView) findViewById(R.id.post_title_lenth);
        mContent_Change_Tv= (TextView) findViewById(R.id.post_content_lenth);

//        //监视大标题输入内容
        mTitile_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mBig_Title_Change_Tv.setText(charSequence.length() + "/20");
                Log.e("charSequence=",charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //监视小标题输入内容
        mSmallTitle_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mSmall_Title_Change_Tv.setText(charSequence.length() + "/20");
                Log.e("charSequence=",charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //监视内容
        mContent_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mContent_Change_Tv.setText(charSequence.length() + "/1000");
                Log.e("charSequence=",charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mYuYi_tv = (TextView) findViewById(R.id.post_yuyi);
        mYuYi_tv.setOnClickListener(this);
        mYuYiDoctor_tv = (TextView) findViewById(R.id.post_yuyidoctor);
        mYuYiDoctor_tv.setOnClickListener(this);
        mSubmit_tv = (TextView) findViewById(R.id.post_submit_btn);
        mSubmit_tv.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == mAdd_img.getId()) {//添加图片
            outImage = new File(getExternalFilesDir("DCIM").getAbsolutePath(), new Date().getTime() + ".jpg");
            presenter.showWindow(this, mData_rl, outImage);
        } else if (id == mYuYi_tv.getId()) {//宇医
            mWayFlag = 1;
            mYuYi_tv.setTextColor(ContextCompat.getColor(this, R.color.color_ffffff));
            mYuYi_tv.setBackgroundResource(R.drawable.hospital_next_bg);
            mYuYiDoctor_tv.setTextColor(ContextCompat.getColor(this, R.color.color_999999));
            mYuYiDoctor_tv.setBackgroundResource(R.drawable.yuyidoctor_bg);
        } else if (id == mYuYiDoctor_tv.getId()) {//宇医医生
            mWayFlag = 2;
            mYuYi_tv.setTextColor(ContextCompat.getColor(this, R.color.color_999999));
            mYuYi_tv.setBackgroundResource(R.drawable.yuyidoctor_bg);
            mYuYiDoctor_tv.setTextColor(ContextCompat.getColor(this, R.color.color_ffffff));
            mYuYiDoctor_tv.setBackgroundResource(R.drawable.hospital_next_bg);
        } else if (id == mSubmit_tv.getId()) {//提交
            if (isPhotoChange) {
                if (!"".equals(getBigTitle())) {
                    if (!"".equals(getSmallTitle())) {
                        if (!"".equals(getContent())) {
                            MyDialog.showDialog(this);
                            AjaxParams ajaxParams = new AjaxParams();
                            ajaxParams.put("title", getBigTitle());
                            ajaxParams.put("smallTitle", getSmallTitle());
                            ajaxParams.put("content", getContent());
                            ajaxParams.put("hospitalId", User.HospitalId);
                            ajaxParams.put("Publishchannel", String.valueOf(mWayFlag));
                            try {
                                ajaxParams.put("file", lastFile);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            httpTools.postInformation(handler, ajaxParams);
                        } else {
                            ToastUtils.myToast(getApplicationContext(), "请填写内容");

                        }

                    } else {
                        ToastUtils.myToast(getApplicationContext(), "请填写小标题");

                    }

                } else {
                    ToastUtils.myToast(getApplicationContext(), "请填写标题");
                }

            } else {
                ToastUtils.myToast(getApplicationContext(), "请上传图片");
            }
        }
    }

    //标题
    private String getBigTitle() {

        if (!"".equals(mTitile_edit.getText().toString().trim())) {
            return mTitile_edit.getText().toString().trim();
        }
        return "";
    }

    //小标题
    private String getSmallTitle() {

        if (!"".equals(mSmallTitle_edit.getText().toString().trim())) {
            return mSmallTitle_edit.getText().toString().trim();
        }
        return "";
    }

    //内容
    private String getContent() {

        if (!"".equals(mContent_edit.getText().toString().trim())) {
            return mContent_edit.getText().toString().trim();
        }
        return "";
    }

    //调用相册拍照判断权限
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
            lastFile=result;
            mAdd_img.setImageBitmap(BitmapFactory.decodeFile(lastFile.getAbsolutePath()));
            isPhotoChange = true;
        }
        else {
            toast.toast(this,"图片获取失败！");
        }
    }

}
