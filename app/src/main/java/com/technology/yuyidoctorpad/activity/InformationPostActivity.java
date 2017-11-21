package com.technology.yuyidoctorpad.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.bean.HospitalInformationPost.Root;
import com.technology.yuyidoctorpad.lhdUtils.MyDialog;
import com.technology.yuyidoctorpad.lhdUtils.PicturePhotoUtils;
import com.technology.yuyidoctorpad.lhdUtils.RSCode;
import com.technology.yuyidoctorpad.lhdUtils.ToastUtils;
import com.technology.yuyidoctorpad.lhdUtils.UserInfoPresenter2;
import com.technology.yuyidoctorpad.lzhUtils.toast;

import net.tsz.afinal.http.AjaxParams;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

import static com.technology.yuyidoctorpad.R.id.post_title_lenth;

public class InformationPostActivity extends AppCompatActivity implements View.OnClickListener {
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
                            MyDialog.showDialog(getApplicationContext());
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RSCode.priCode_SearchPicture://图库
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PicturePhotoUtils.getInstance().searchPhto(this, outImage);
                } else {

                    toast.toast(getApplicationContext(), "存储权限被禁用，无法获取相册信息");
                }
                break;
            case RSCode.priCode_TakePhoto://拍照
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PicturePhotoUtils.getInstance().takePhoto(this, outImage);
                } else {
                    toast.toast(getApplicationContext(), "请打开相机权限");
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RSCode.rCode_SearchPicture://浏览相册
                    outImage = new File(getExternalFilesDir("DCIM").getAbsolutePath(), new Date().getTime() + ".jpg");
                    PicturePhotoUtils.getInstance().cutPhoto_Search(this, outImage, data);
                    break;
                case RSCode.rCode_TakePhoto://拍照
                    Uri uri = Uri.fromFile(outImage);
                    outImage = new File(getExternalFilesDir("DCIM").getAbsolutePath(), new Date().getTime() + ".jpg");
                    PicturePhotoUtils.getInstance().cutPhoto_Camera(this, uri, outImage);
                    break;
                case RSCode.rCode_CutPicture://裁剪
                    try {
                        //将output_image.jpg对象解析成Bitmap对象，然后设置到ImageView中显示出来
                        Bitmap bitmap = BitmapFactory.decodeFile(outImage.getAbsolutePath());
                        if (bitmap != null) {
                            try{
                                lastFile=new File(getExternalFilesDir("DCIM").getAbsolutePath(), new Date().getTime() + ".jpg");
                                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(lastFile));
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                                bos.flush();
                                bos.close();
                                isPhotoChange = true;
                                mAdd_img.setImageBitmap(bitmap);
                            }catch (Exception e){
                                isPhotoChange = false;
                                mAdd_img.setImageResource(R.mipmap.erroruser);
                                ToastUtils.myToast(getApplicationContext(),"头像保存失败，重新上传");
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "照片截取失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "照片截取失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

}
