package com.technology.yuyidoctorpad.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.technology.yuyidoctorpad.HttpTools.HttpTools;
import com.technology.yuyidoctorpad.HttpTools.UrlTools;
import com.technology.yuyidoctorpad.Photo.PhotoPictureUtils;
import com.technology.yuyidoctorpad.Photo.PhotoRSCode;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.User.User;
import com.technology.yuyidoctorpad.bean.DepartmentBean.Result;
import com.technology.yuyidoctorpad.bean.DepartmentBean.Root;
import com.technology.yuyidoctorpad.lhdUtils.MyDialog;
import com.technology.yuyidoctorpad.lhdUtils.TelephoneUtils;
import com.technology.yuyidoctorpad.lhdUtils.ToastUtils;
import com.technology.yuyidoctorpad.lhdUtils.UserInfoPresenter2;
import com.technology.yuyidoctorpad.lzhUtils.toast;

import net.tsz.afinal.http.AjaxParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddDcotorActivity extends AppCompatActivity implements View.OnClickListener,PhotoPictureUtils.OnSavePictureListener {
    private RelativeLayout mJob_Rl, mDepartment_Rl, mWai_rl;
    private ImageView mAdd_Img, mLookDataPower_img, mAskPower_img, mRegister_img;
    private EditText mName_Edit, mTelephone_Edit,mJob_Edit;
    private TextView  mDepartment_Tv, mSure_Tv, mTitle;
   // private ListView mJob_ListView;
   // private JobAda mJobAda;
    private List<String> mJobList = new ArrayList<>();
    private ExpandableListView mDepartment_ListView;
    private DepartmentTwoAda mDepartmentAda;
    private List<Result> mDeparmentListOne1 = new ArrayList<>();
    private int OneID = -1;
    private int TwoID = -1;
    private boolean askPowerFlag = false;
    private boolean lookDataPowerFlag = false;
    private boolean registerPowerFlag = false;

    private UserInfoPresenter2 presenter;
    private File outImage;
    private File lastFile;
    private boolean isPhotoChange = false;

    private HttpTools mHttpTools;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 55) {//获取科室信息
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root != null && root.getResult() != null) {
                        mDeparmentListOne1 = root.getResult();
                        if (mDeparmentListOne1.size() != 0) {
                            mDepartmentAda.notifyDataSetChanged();
                            //默认展开二级菜单
                            for (int i = 0; i < mDepartmentAda.getGroupCount(); i++) {
                                mDepartment_ListView.expandGroup(i);
                            }
                        } else {
                            ToastUtils.myToast(AddDcotorActivity.this, "无法获取科室列表");
                        }
                    }
                }
            } else if (msg.what == 56) {//添加医生
                Object o = msg.obj;
                MyDialog.stopDia();
                mSure_Tv.setClickable(true);
                if (o != null && o instanceof com.technology.yuyidoctorpad.bean.DeparmentSubmitBean.Root) {
                    com.technology.yuyidoctorpad.bean.DeparmentSubmitBean.Root root = (com.technology.yuyidoctorpad.bean.DeparmentSubmitBean.Root) o;
                    if ("0".equals(root.getCode())) {
                        ToastUtils.myToast(AddDcotorActivity.this, "添加医生成功");
                        mSure_Tv.setClickable(true);
                        setResult(RESULT_OK,getIntent());
                        finish();

                    } else {
                        ToastUtils.myToast(AddDcotorActivity.this, root.getMessage());
                    }
                }else {
                    ToastUtils.myToast(AddDcotorActivity.this, "添加医生失败");
                }
            }else if (msg.what == 64) {//修改医生
                Object o = msg.obj;
                mSure_Tv.setClickable(true);
                MyDialog.stopDia();
                if (o != null && o instanceof com.technology.yuyidoctorpad.bean.DeparmentSubmitBean.Root) {
                    com.technology.yuyidoctorpad.bean.DeparmentSubmitBean.Root root = (com.technology.yuyidoctorpad.bean.DeparmentSubmitBean.Root) o;
                    if ("0".equals(root.getCode())) {
                        ToastUtils.myToast(AddDcotorActivity.this, "修改医生成功");
                        mSure_Tv.setClickable(true);
                        finish();
                    } else {
                        ToastUtils.myToast(AddDcotorActivity.this, root.getMessage());
                    }
                    mSure_Tv.setClickable(true);
                }else {
                    mSure_Tv.setClickable(true);
                }
            }



            else if (msg.what == 58) {//获取修改详情
                Object o = msg.obj;
                if (o != null && o instanceof com.technology.yuyidoctorpad.bean.DoctorDetails.Root) {
                    com.technology.yuyidoctorpad.bean.DoctorDetails.Root root = (com.technology.yuyidoctorpad.bean.DoctorDetails.Root) o;
                    if (root != null) {
                        isPhotoChange = true;
                        Picasso.with(getApplicationContext()).load(UrlTools.BASE + root.getAvatar()).error(R.mipmap.erroruser).into(mAdd_Img);
                        mName_Edit.setText(root.getTrueName());
                        mJob_Edit.setText(root.getTitle());
                        mDepartment_Tv.setText(root.getDepartmentName());
                        mTelephone_Edit.setText(root.getTelephone() + "");
                        OneID=root.getDepartmentId();
                        TwoID=root.getClinicId();
                        if (root.getPermissionInfo()) {
                            mAskPower_img.setImageResource(R.mipmap.aggree_select);
                            askPowerFlag = true;
                        } else {
                            mAskPower_img.setImageResource(R.mipmap.agree_no);
                            askPowerFlag = false;
                        }
                        if (root.getPermissionData()) {
                            mLookDataPower_img.setImageResource(R.mipmap.aggree_select);
                            lookDataPowerFlag = true;
                        } else {
                            mLookDataPower_img.setImageResource(R.mipmap.agree_no);
                            lookDataPowerFlag = false;
                        }
                        if (root.getPermissionRegister()) {
                            mRegister_img.setImageResource(R.mipmap.aggree_select);
                            registerPowerFlag = true;
                        } else {
                            mRegister_img.setImageResource(R.mipmap.agree_no);
                            registerPowerFlag = false;
                        }



                    } else {
                        ToastUtils.myToast(getApplicationContext(), "获取医生详情失败");
                    }
                }
            }
        }
    };

    private String doctorId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dcotor);
        initUI();
        mHttpTools = HttpTools.getHttpToolsInstance();
        mHttpTools.getDepartmentMessage(mHandler, User.HospitalId);//获取科室
        long id = getIntent().getLongExtra("doctorId", -1);
        Log.e("医生ID", id + "");
        if (id!=-1) {
            mHttpTools.getDoctorDetails(mHandler, id);//获取医生详情
            doctorId = String.valueOf(id);
            mTitle.setText("修改医生信息");
        }
        presenter = new UserInfoPresenter2();
    }

    private void initUI() {
        mWai_rl = (RelativeLayout) findViewById(R.id.wai_rl);
        mTitle = (TextView) findViewById(R.id.h_title);
        mAdd_Img = (ImageView) findViewById(R.id.add_head_img);//添加
        mAdd_Img.setOnClickListener(this);
        mLookDataPower_img = (ImageView) findViewById(R.id.ask_aggre_img1);//咨询权限
        mLookDataPower_img.setOnClickListener(this);
        mAskPower_img = (ImageView) findViewById(R.id.ask_aggre_img2);//查看数据权限
        mAskPower_img.setOnClickListener(this);
        mRegister_img = (ImageView) findViewById(R.id.ask_aggre_img3);//挂号接收权限
        mRegister_img.setOnClickListener(this);
        mName_Edit = (EditText) findViewById(R.id.edit_name);//姓名
        mTelephone_Edit = (EditText) findViewById(R.id.edit_telephone);//电话
        mJob_Edit= (EditText) findViewById(R.id.edit_job);//职业
        mDepartment_Tv = (TextView) findViewById(R.id.department_tv);//科室

        mJob_Rl = (RelativeLayout) findViewById(R.id.job_rl);//职业按钮
        mJob_Rl.setOnClickListener(this);
        mDepartment_Rl = (RelativeLayout) findViewById(R.id.department_rl);//科室按钮
        mDepartment_Rl.setOnClickListener(this);
        mSure_Tv = (TextView) findViewById(R.id.sure_btn);//确定
        mSure_Tv.setOnClickListener(this);

        mDepartment_ListView = (ExpandableListView) findViewById(R.id.department_listview);//科室ListView
        mDepartmentAda = new DepartmentTwoAda();
        //去掉箭头
        mDepartment_ListView.setGroupIndicator(null);
        mDepartment_ListView.setAdapter(mDepartmentAda);
        mDepartment_ListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Log.e("外层ID", mDeparmentListOne1.get(groupPosition).getId() + "");
                Log.e("内层ID", mDeparmentListOne1.get(groupPosition).getClinicList().get(childPosition).getId() + "");
                Log.e("外层名称", mDeparmentListOne1.get(groupPosition).getDepartmentName() + "");
                Log.e("内层名称", mDeparmentListOne1.get(groupPosition).getClinicList().get(childPosition).getClinicName());
                OneID = mDeparmentListOne1.get(groupPosition).getId();
                TwoID = mDeparmentListOne1.get(groupPosition).getClinicList().get(childPosition).getId();
                mDepartment_Tv.setText(mDeparmentListOne1.get(groupPosition).getClinicList().get(childPosition).getClinicName());
                mDepartment_ListView.setVisibility(View.GONE);
                return true;
            }
        });


        //不能点击收缩
        mDepartment_ListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });


//        mJobList.add("麻醉师");
//        mJobList.add("手术师");
//        mJobList.add("主治医师");
//        mJob_ListView = (ListView) findViewById(R.id.job_listview);//职称ListView
//        mJobAda = new JobAda();
//        mJob_ListView.setAdapter(mJobAda);
//        mJob_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                mJob_Tv.setText(mJobList.get(i));
//                mJob_ListView.setVisibility(View.GONE);
//            }
//        });
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == mAdd_Img.getId()) {//添加图片
            outImage = new File(getExternalFilesDir("DCIM").getAbsolutePath(), new Date().getTime() + ".jpg");
            presenter.showWindow(this, mWai_rl, outImage);
        } else if (id == mJob_Edit.getId()) {//选择职称

            if (mDepartment_ListView.getVisibility() == View.VISIBLE) {
                mDepartment_ListView.setVisibility(View.GONE);
            }
//            if (mJob_ListView.getVisibility() == View.GONE) {
//                mJob_ListView.setVisibility(View.VISIBLE);
//            } else if (mJob_ListView.getVisibility() == View.VISIBLE) {
//                mJob_ListView.setVisibility(View.GONE);
//            }


        } else if (id == mDepartment_Rl.getId()) {//选择科室

//            if (mJob_ListView.getVisibility() == View.VISIBLE) {
//                mJob_ListView.setVisibility(View.GONE);
//            }

            if (mDepartment_ListView.getVisibility() == View.GONE) {
                mDepartment_ListView.setVisibility(View.VISIBLE);
            } else if (mDepartment_ListView.getVisibility() == View.VISIBLE) {
                mDepartment_ListView.setVisibility(View.GONE);
            }
            if (mDeparmentListOne1.size() == 0) {
                ToastUtils.myToast(AddDcotorActivity.this, "正在获取科室...");
                mHttpTools.getDepartmentMessage(mHandler, User.HospitalId);//获取科室
            }

        } else if (id == mAskPower_img.getId()) {//咨询权限
            if (askPowerFlag) {
                mAskPower_img.setImageResource(R.mipmap.agree_no);
                askPowerFlag = false;
            } else {
                mAskPower_img.setImageResource(R.mipmap.aggree_select);
                askPowerFlag = true;
            }

        } else if (id == mLookDataPower_img.getId()) {//查看数据权限
            if (lookDataPowerFlag) {
                mLookDataPower_img.setImageResource(R.mipmap.agree_no);
                lookDataPowerFlag = false;
            } else {
                mLookDataPower_img.setImageResource(R.mipmap.aggree_select);
                lookDataPowerFlag = true;
            }

        } else if (id == mRegister_img.getId()) {//挂号接收权限
            if (registerPowerFlag) {
                mRegister_img.setImageResource(R.mipmap.agree_no);
                registerPowerFlag = false;
            } else {
                mRegister_img.setImageResource(R.mipmap.aggree_select);
                registerPowerFlag = true;
            }

        } else if (id == mSure_Tv.getId()) {//确定

            if (!"".equals(getName())) {
                if (!"".equals(getJob())) {
                    if (!"".equals(getDepartment())) {
                        if (!"".equals(getPhone())) {
                            if (isPhotoChange) {
                                AjaxParams ajaxParams = new AjaxParams();
                                if (!"".equals(doctorId)){
                                    ajaxParams.put("id", doctorId);
                                }
                                Log.e("医生ID", doctorId + "");
                                ajaxParams.put("trueName", getName());
                                ajaxParams.put("title", getJob());
                                ajaxParams.put("telephone", getPhone());
                                ajaxParams.put("hospitalId", User.HospitalId);
                                ajaxParams.put("departmentId", String.valueOf(OneID));
                                ajaxParams.put("clinicId", String.valueOf(TwoID));
                                ajaxParams.put("permissionInfo", String.valueOf(askPowerFlag));
                                ajaxParams.put("permissionData", String.valueOf(lookDataPowerFlag));
                                ajaxParams.put("permissionRegister", String.valueOf(registerPowerFlag));
                                try {
                                    if (lastFile != null) {
                                        ajaxParams.put("file", lastFile);
                                        Log.e("头像",lastFile.getAbsolutePath());
                                    }

                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                MyDialog.showDialog(AddDcotorActivity.this);
                                if (!"".equals(doctorId)){
                                    mHttpTools.submitUpdateDoctor(mHandler, ajaxParams);//提交修改数据
                                }else {
                                    mHttpTools.submitAddDoctor(mHandler, ajaxParams);//添加数据
                                }

                                mSure_Tv.setClickable(false);
                            } else {
                                ToastUtils.myToast(AddDcotorActivity.this, "请上传头像");
                            }

                        } else {
                            ToastUtils.myToast(AddDcotorActivity.this, "请输入正确的电话号码");
                        }

                    } else {
                        ToastUtils.myToast(AddDcotorActivity.this, "请选择科室");
                    }

                } else {
                    ToastUtils.myToast(AddDcotorActivity.this, "请选择职称");
                }

            } else {
                ToastUtils.myToast(AddDcotorActivity.this, "请输入姓名");
            }
        }
    }

    //获取姓名
    public String getName() {
        if (!"".equals(mName_Edit.getText().toString().trim())) {
            return mName_Edit.getText().toString().trim();
        }
        return "";

    }

    //获取电话
    public String getPhone() {
        if (TelephoneUtils.checkPhone(mTelephone_Edit.getText().toString().trim())) {
            return mTelephone_Edit.getText().toString().trim();
        }
        return "";
    }

    //获取职称

    public String getJob() {
        if (!"".equals(mJob_Edit.getText().toString().trim())) {
            return mJob_Edit.getText().toString().trim();
        }
        return "";
    }

    //获取科室
    public String getDepartment() {
        if (!"".equals(mDepartment_Tv.getText().toString().trim())) {
            return mDepartment_Tv.getText().toString().trim();
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
            mAdd_Img.setImageBitmap(BitmapFactory.decodeFile(lastFile.getAbsolutePath()));
            isPhotoChange = true;
        }
        else {
            toast.toast(this,"图片保存失败！");
        }
    }

    class JobAda extends BaseAdapter {

        @Override
        public int getCount() {
            return mJobList.size();
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
            JobHolder jobHolder = null;
            if (view == null) {
                jobHolder = new JobHolder();
                view = LayoutInflater.from(AddDcotorActivity.this).inflate(R.layout.department_listview_item, null);
                jobHolder.job = view.findViewById(R.id.department_id);
                view.setTag(jobHolder);
            } else {
                jobHolder = (JobHolder) view.getTag();
            }
            jobHolder.job.setText(mJobList.get(i));
            return view;
        }

        class JobHolder {
            TextView job;
        }
    }


    class DepartmentTwoAda extends BaseExpandableListAdapter {

        //获取外层集合数量
        @Override
        public int getGroupCount() {
            return mDeparmentListOne1.size();
        }

        //获取每一个外层中的第二层数量
        @Override
        public int getChildrenCount(int i) {
            Log.e("getChildrenCount==", mDeparmentListOne1.get(i).getClinicList().size() + "");
            return mDeparmentListOne1.get(i).getClinicList().size();
        }

        //获取每一个外层的实体
        @Override
        public Object getGroup(int i) {
            return mDeparmentListOne1.get(i);
        }

        //获取每一个外层中内层的实体
        @Override
        public Object getChild(int i, int i1) {
            Log.e("getChild==", "" + i + "=i1=" + i1 + "----" + mDeparmentListOne1.get(i).getClinicList().get(i1).hashCode() + "");
            return mDeparmentListOne1.get(i).getClinicList().get(i1);
        }

        //获取外层下标
        @Override
        public long getGroupId(int i) {
            return i;
        }

        //获取内层下标
        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            OneHolder oneHolder = null;
            if (view == null) {
                oneHolder = new OneHolder();
                view = LayoutInflater.from(AddDcotorActivity.this).inflate(R.layout.department_listview_item, null);
                oneHolder.t1 = view.findViewById(R.id.department_id);
                view.setTag(oneHolder);
            } else {
                oneHolder = (OneHolder) view.getTag();
            }
            oneHolder.t1.setText(mDeparmentListOne1.get(i).getDepartmentName());
            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            TwoHolder twoHolder = null;
            if (view == null) {
                twoHolder = new TwoHolder();
                view = LayoutInflater.from(AddDcotorActivity.this).inflate(R.layout.child_listview_item, null);
                twoHolder.t2 = view.findViewById(R.id.child_tv);
                view.setTag(twoHolder);
            } else {
                twoHolder = (TwoHolder) view.getTag();
            }
            Log.e("====" + i, mDeparmentListOne1.get(i).getClinicList().size() + "");
            twoHolder.t2.setText(mDeparmentListOne1.get(i).getClinicList().get(i1).getClinicName());
            return view;
        }

        //内层item是否可点击
        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }

        class OneHolder {
            TextView t1;
        }

        class TwoHolder {
            TextView t2;
        }
    }


}
