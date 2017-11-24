package com.technology.yuyidoctorpad.fragment.HospitalFragment;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.technology.yuyidoctorpad.Photo.PhotoPictureUtils;
import com.technology.yuyidoctorpad.Photo.PhotoRSCode;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.activity.WriteHospitalMessageActivity;
import com.technology.yuyidoctorpad.lhdUtils.TelephoneUtils;
import com.technology.yuyidoctorpad.lhdUtils.ToastUtils;
import com.technology.yuyidoctorpad.lhdUtils.UserInfoPresenter;
import com.technology.yuyidoctorpad.lzhUtils.toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class HospitalMessageFragment extends Fragment implements View.OnClickListener ,PhotoPictureUtils.OnSavePictureListener{

    private EditText mH_Name_Edit, mH_Grade_Edit, mH_Content_Edit, mH_Telephone_Edit,mH_Address_Edit;
    private ImageView mAdd_Hospital_Img;
    private TextView mNext_Btn,mJJ_length_tv;
    private GridView mBook_GridView;
    private BookAda mBookAda;
    private List<File> mBookList = new ArrayList<>();
    private RelativeLayout mData_Rl;
    private WriteHospitalMessageActivity mActivity;

    private UserInfoPresenter presenter;
    private File file;
    private File hospitalLastFile;
    private int flag = 1;//
    private boolean isHospitalChange = false;
    //删除证书弹框
    private AlertDialog.Builder builder;
    private AlertDialog mAlert;
    private View mAlertView;
    private TextView mSure, mCancle;
    private int mBookPosition = -1;

    public HospitalMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hospital_message, container, false);
        presenter = new UserInfoPresenter();
        ;
        InitUI(view);
        return view;
    }

    private void InitUI(View view) {
        mData_Rl = view.findViewById(R.id.data_rl);
        mH_Name_Edit = view.findViewById(R.id.hospital_name_edit);
        mH_Grade_Edit = view.findViewById(R.id.hospital_grade_edit);
        mH_Content_Edit = view.findViewById(R.id.hospital_content_edit);
        mJJ_length_tv=view.findViewById(R.id.hospital_max_length);
        mH_Telephone_Edit = view.findViewById(R.id.hospital_telephone_edit);
        mH_Address_Edit = view.findViewById(R.id.hospital_address_edit);
        mAdd_Hospital_Img = view.findViewById(R.id.hospital_picture_img);
        mAdd_Hospital_Img.setOnClickListener(this);
        mH_Content_Edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mJJ_length_tv.setText(charSequence.length()+"/500");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        mBook_GridView = view.findViewById(R.id.hospital_book_gridview);
        mBookAda = new BookAda();
        mBook_GridView.setAdapter(mBookAda);
        mBook_GridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mBookList.size() == 0) {//跳转相册
                    flag = 2;
                    file = new File(getContext().getExternalFilesDir("DCIM").getAbsolutePath(), new Date().getTime() + ".jpg");
                    presenter.showWindow(HospitalMessageFragment.this, mData_Rl, file);
                } else {
                    if (i == mBookList.size()) {//跳转相册
                        flag = 2;
                        file = new File(getContext().getExternalFilesDir("DCIM").getAbsolutePath(), new Date().getTime() + ".jpg");
                        presenter.showWindow(HospitalMessageFragment.this, mData_Rl, file);
                    } else {
                        //删除已经选择的相关证书相片
                        mBookPosition = i;
                        mAlert.show();
                    }
                }
            }
        });
        mNext_Btn = view.findViewById(R.id.hospital_next_btn);
        mNext_Btn.setOnClickListener(this);
        mActivity = (WriteHospitalMessageActivity) getActivity();

        //删除图片是的弹框
        builder = new AlertDialog.Builder(getContext());
        mAlert = builder.create();
        mAlertView = LayoutInflater.from(getContext()).inflate(R.layout.alert_box, null);
        mAlert.setView(mAlertView);
        mSure = (TextView) mAlertView.findViewById(R.id.sure);
        mCancle = (TextView) mAlertView.findViewById(R.id.cancle);
        mSure.setOnClickListener(this);
        mCancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == mNext_Btn.getId()) {//下一步
            if (!"".equals(getHospitalName())) {

                if (!"".equals(getHospitalGrade())) {

                    if (!"".equals(getHospitalJJ())) {

                        if (!"".equals(getPhone())) {

                            if (!"".equals(getAddress())){
                                if (isHospitalChange) {

                                    if (mBookList.size() != 0) {

                                        mActivity.showManagerFragment(getHospitalName(),getHospitalGrade(),getHospitalJJ(),getPhone(),hospitalLastFile,mBookList,getAddress());
                                    } else {
                                        ToastUtils.myToast(getContext(), "请上传相关证书");
                                    }

                                } else {
                                    ToastUtils.myToast(getContext(), "请上传医院图片");
                                }
                            }else {
                                ToastUtils.myToast(getContext(), "请填写地址");
                            }

                        } else {
                            ToastUtils.myToast(getContext(), "请填写正确电话");
                        }
                    } else {
                        ToastUtils.myToast(getContext(), "请填写医院简介");
                    }
                } else {
                    ToastUtils.myToast(getContext(), "请填写医院等级");
                }
            } else {
                ToastUtils.myToast(getContext(), "请填写医院名称");
            }


        } else if (id == mAdd_Hospital_Img.getId()) {//添加医院图片
            flag = 1;
            file = new File(getContext().getExternalFilesDir("DCIM").getAbsolutePath(), new Date().getTime() + ".jpg");
            presenter.showWindow(this, mData_Rl, file);
        } else if (id == mSure.getId()) {//确定删除
            mBookList.remove(mBookPosition);
            mBookAda.notifyDataSetChanged();
            mAlert.dismiss();
        } else if (id == mCancle.getId()) {//取消
            mAlert.dismiss();
        }
    }


    private String getHospitalName() {
        if (!"".equals(mH_Name_Edit.getText().toString().trim())) {
            return mH_Name_Edit.getText().toString().trim();
        }
        return "";
    }

    private String getHospitalGrade() {
        if (!"".equals(mH_Grade_Edit.getText().toString().trim())) {
            return mH_Grade_Edit.getText().toString().trim();
        }
        return "";
    }

    private String getHospitalJJ() {
        if (!"".equals(mH_Content_Edit.getText().toString().trim())) {
            return mH_Content_Edit.getText().toString().trim();
        }
        return "";
    }

    private String getPhone() {
        if (TelephoneUtils.isFixedPhone(mH_Telephone_Edit.getText().toString().trim())) {
            return mH_Telephone_Edit.getText().toString().trim();
        }

        return "";
    }

    private String getAddress() {
        if (!"".equals(mH_Address_Edit.getText().toString().trim())) {
            return mH_Address_Edit.getText().toString().trim();
        }
        return "";
    }



    class BookAda extends BaseAdapter {

        @Override
        public int getCount() {
            return mBookList.size() + 1;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            BookHolder bookHolder = null;
            if (view == null) {
                bookHolder = new BookHolder();
                view = LayoutInflater.from(getContext()).inflate(R.layout.book_gridview_item, null);

                bookHolder.img = view.findViewById(R.id.book_img);
                view.setTag(bookHolder);
            } else {
                bookHolder = (BookHolder) view.getTag();
            }
            if (mBookList.size() == 0) {
                bookHolder.img.setImageResource(R.mipmap.hospital_add);

            } else {
                if (i == mBookList.size()) {
                    bookHolder.img.setImageResource(R.mipmap.hospital_add);
                } else {
                    Bitmap bitmap = BitmapFactory.decodeFile(mBookList.get(i).getAbsolutePath());
                    if (bitmap != null) {
                        bookHolder.img.setImageBitmap(bitmap);
                    } else {
                        bookHolder.img.setImageResource(R.mipmap.errorpicture);
                    }
                }


            }
            return view;
        }

        class BookHolder {
            ImageView img;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode== PhotoRSCode.requestCode_SearchPermission){//选取图片的权限请求
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                PhotoPictureUtils.getInstance().searchPictureFragment(this);
            }
            else {
                Toast.makeText(getActivity(),"请打开存储卡权限！",Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode==PhotoRSCode.requestCode_CameraPermission){//拍照的权限请求
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                PhotoPictureUtils.getInstance().takePhotoFragment(this);
            }
            else {
                Toast.makeText(getActivity(),"请打开相机权限！",Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            switch (requestCode) {
                case PhotoRSCode.requestCode_Search://相册选取返回
                    PhotoPictureUtils.getInstance().savaPictureSearch(data.getData(),this,getActivity());
                    break;
                case PhotoRSCode.requestCode_Camera://拍照
                    //cameraFile为保存后的文件，mImg：需要显示图片的ImageView
                    PhotoPictureUtils.getInstance().savaPictureCamera(this,getActivity());
                    break;
            }
        }
    }

    @Override
    public void onSavePicture(boolean isSuccess, File result) {
            if (isSuccess){
                switch (flag){
                    case 1:
                        hospitalLastFile=result;
                        mAdd_Hospital_Img.setImageBitmap(BitmapFactory.decodeFile(hospitalLastFile.getAbsolutePath()));
                        isHospitalChange = true;
                        break;
                    case 2:
                        mBookList.add(result);
                        mBookAda.notifyDataSetChanged();
                        break;
                }
            }
         else {
                toast.toast(getActivity(),"图片获取失败！");
            }
    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case RSCode.rCode_SearchPicture://浏览相册
//                    file = new File(getContext().getExternalFilesDir("DCIM").getAbsolutePath(), new Date().getTime() + ".jpg");
//                    PicturePhotoUtils.getInstance().cutPhoto_SearchFrag(this, file, data);
//                    break;
//                case RSCode.rCode_TakePhoto://拍照
//                    Uri uri = Uri.fromFile(file);
//                    file = new File(getContext().getExternalFilesDir("DCIM").getAbsolutePath(), new Date().getTime() + ".jpg");
//                    PicturePhotoUtils.getInstance().cutPhoto_CameraFrag(this, uri, file);
//                    break;
//                case RSCode.rCode_CutPicture://裁剪
//                    try {
//                        //将output_image.jpg对象解析成Bitmap对象，然后设置到ImageView中显示出来
//                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//                        if (bitmap != null) {
//                            try {
//                                if (flag == 1) {//医院图片
//                                    hospitalLastFile = new File(getContext().getExternalFilesDir("DCIM").getAbsolutePath(), new Date().getTime() + ".jpg");
//                                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(hospitalLastFile));
//                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//                                    bos.flush();
//                                    bos.close();
//                                    isHospitalChange = true;
//                                    mAdd_Hospital_Img.setImageBitmap(bitmap);
//                                } else {//证书集合
//                                    File f = new File(getContext().getExternalFilesDir("DCIM").getAbsolutePath(), new Date().getTime() + ".jpg");
//                                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
//                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//                                    bos.flush();
//                                    bos.close();
//                                    mBookList.add(f);
//                                    mBookAda.notifyDataSetChanged();
//                                }
//
//
//                            } catch (Exception e) {
//                                isHospitalChange = false;
//                                ToastUtils.myToast(getContext(), "重新上传");
//                                e.printStackTrace();
//                            }
//
//
//                        } else {
//                            Toast.makeText(getContext(), "照片截取失败", Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        Toast.makeText(getContext(), "照片截取失败", Toast.LENGTH_SHORT).show();
//                    }
//                    break;
//            }
//        }
//    }
}
