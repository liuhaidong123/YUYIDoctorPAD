package com.technology.yuyidoctorpad.fragment.HospitalHomePageFragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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

import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.adapter.DepartmentAda;
import com.technology.yuyidoctorpad.lhdUtils.PicturePhotoUtils;
import com.technology.yuyidoctorpad.lhdUtils.RSCode;
import com.technology.yuyidoctorpad.lhdUtils.TelephoneUtils;
import com.technology.yuyidoctorpad.lhdUtils.ToastUtils;
import com.technology.yuyidoctorpad.lhdUtils.UserInfoPresenter;
import com.technology.yuyidoctorpad.lzhUtils.Empty;
import com.technology.yuyidoctorpad.lzhUtils.toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * 添加医生
 */
public class AddDoctorFragment extends Fragment implements View.OnClickListener {
    private UserInfoPresenter presenter;
    private RelativeLayout mJob_Rl, mDepartment_Rl, mWai_rl;
    private ImageView mAdd_Img, mLookDataPower_img, mAskPower_img, mRegister_img;
    private EditText mName_Edit, mTelephone_Edit;
    private TextView mJob_Tv, mDepartment_Tv, mSure_Tv;
    private ListView mJob_ListView;
    private JobAda mJobAda;
    private List<String> mJobList = new ArrayList<>();
    private ExpandableListView mDepartment_ListView;
    private DepartmentTwoAda mDepartmentAda;
    private List<String> mDeparmentListOne = new ArrayList<>();
    private List<String> mDeparmentListTwo = new ArrayList<>();

    private boolean askPowerFlag = false;
    private boolean lookDataPowerFlag = false;
    private boolean registerPowerFlag = false;

    private File outImage;
    private boolean isPhotoChange = false;

    public AddDoctorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_doctor, container, false);
        initUI(view);
        presenter = new UserInfoPresenter();
        return view;
    }

    private void initUI(View view) {
        mWai_rl = view.findViewById(R.id.wai_rl);
        mAdd_Img = view.findViewById(R.id.add_head_img);//添加
        mAdd_Img.setOnClickListener(this);
        mLookDataPower_img = view.findViewById(R.id.ask_aggre_img1);//咨询权限
        mLookDataPower_img.setOnClickListener(this);
        mAskPower_img = view.findViewById(R.id.ask_aggre_img2);//查看数据权限
        mAskPower_img.setOnClickListener(this);
        mRegister_img = view.findViewById(R.id.ask_aggre_img3);//挂号接收权限
        mRegister_img.setOnClickListener(this);
        mName_Edit = view.findViewById(R.id.edit_name);//姓名
        mTelephone_Edit = view.findViewById(R.id.edit_telephone);//电话
        mJob_Tv = view.findViewById(R.id.job_tv);//职业
        mDepartment_Tv = view.findViewById(R.id.department_tv);//科室

        mJob_Rl = view.findViewById(R.id.job_rl);//职业按钮
        mJob_Rl.setOnClickListener(this);
        mDepartment_Rl = view.findViewById(R.id.department_rl);//科室按钮
        mDepartment_Rl.setOnClickListener(this);
        mSure_Tv = view.findViewById(R.id.sure_btn);//确定
        mSure_Tv.setOnClickListener(this);

        mDeparmentListOne.add("内科");
        mDeparmentListOne.add("外科");
        mDeparmentListOne.add("口腔科");

        mDeparmentListTwo.add("妇产科");
        mDeparmentListTwo.add("精神科");
        mDeparmentListTwo.add("牙科");
        mDeparmentListTwo.add("不孕不育科");
        mDepartment_ListView = view.findViewById(R.id.department_listview);//科室ListView
        mDepartmentAda = new DepartmentTwoAda();
        mDepartment_ListView.setAdapter(mDepartmentAda);
        mDepartment_ListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getContext(),mDeparmentListTwo.get(childPosition)+childPosition,Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        //默认展开二级菜单
        for(int i = 0; i < mDepartmentAda.getGroupCount(); i++){
            mDepartment_ListView.expandGroup(i);
        }
        //不能点击收缩
        mDepartment_ListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        //去掉箭头
        mDepartment_ListView.setGroupIndicator(null);
        //去掉下划线
        mDepartment_ListView.setDivider(null);

        mJobList.add("麻醉师");
        mJobList.add("手术师");
        mJobList.add("主治医师");
        mJob_ListView = view.findViewById(R.id.job_listview);//职称ListView
        mJobAda = new JobAda();
        mJob_ListView.setAdapter(mJobAda);
        mJob_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mJob_Tv.setText(mJobList.get(i));
                mJob_ListView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == mAdd_Img.getId()) {//添加图片
            outImage = new File(getContext().getExternalFilesDir("DCIM").getAbsolutePath(), new Date().getTime() + ".jpg");
            presenter.showWindow(this, mWai_rl, outImage);
        } else if (id == mJob_Rl.getId()) {//选择职称

            if (mDepartment_ListView.getVisibility() == View.VISIBLE) {
                mDepartment_ListView.setVisibility(View.GONE);
            }
            if (mJob_ListView.getVisibility() == View.GONE) {
                mJob_ListView.setVisibility(View.VISIBLE);
            } else if (mJob_ListView.getVisibility() == View.VISIBLE) {
                mJob_ListView.setVisibility(View.GONE);
            }


        } else if (id == mDepartment_Rl.getId()) {//选择科室

            if (mJob_ListView.getVisibility() == View.VISIBLE) {
                mJob_ListView.setVisibility(View.GONE);
            }
            if (mDepartment_ListView.getVisibility() == View.GONE) {
                mDepartment_ListView.setVisibility(View.VISIBLE);
            } else if (mDepartment_ListView.getVisibility() == View.VISIBLE) {
                mDepartment_ListView.setVisibility(View.GONE);
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

                            } else {
                                ToastUtils.myToast(getContext(), "请上传头像");
                            }

                        } else {
                            ToastUtils.myToast(getContext(), "请输入正确的电话号码");
                        }

                    } else {
                        ToastUtils.myToast(getContext(), "请选择科室");
                    }

                } else {
                    ToastUtils.myToast(getContext(), "请选择职称");
                }

            } else {
                ToastUtils.myToast(getContext(), "请输入姓名");
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
        if (!"".equals(mJob_Tv.getText().toString().trim())) {
            return mJob_Tv.getText().toString().trim();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RSCode.priCode_SearchPicture://图库
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PicturePhotoUtils.getInstance().searchPhtoFrag(this, outImage);
                } else {

                    toast.toast(getContext(), "存储权限被禁用，无法获取相册信息");
                }
                break;
            case RSCode.priCode_TakePhoto://拍照
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PicturePhotoUtils.getInstance().takePhotoFrag(this, outImage);
                } else {
                    toast.toast(getContext(), "请打开相机权限");
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RSCode.rCode_SearchPicture://浏览相册
                    outImage = new File(getContext().getExternalFilesDir("DCIM").getAbsolutePath(), new Date().getTime() + ".jpg");
                    PicturePhotoUtils.getInstance().cutPhoto_SearchFrag(this, outImage, data);
                    break;
                case RSCode.rCode_TakePhoto://拍照
                    Uri uri = Uri.fromFile(outImage);
                    outImage = new File(getContext().getExternalFilesDir("DCIM").getAbsolutePath(), new Date().getTime() + ".jpg");
                    PicturePhotoUtils.getInstance().cutPhoto_CameraFrag(this, uri, outImage);
                    break;
                case RSCode.rCode_CutPicture://裁剪
                    try {
                        //将output_image.jpg对象解析成Bitmap对象，然后设置到ImageView中显示出来
                        Bitmap bitmap = BitmapFactory.decodeFile(outImage.getAbsolutePath());
                        if (bitmap != null) {
                            isPhotoChange = true;
                            mAdd_Img.setImageBitmap(bitmap);
                        } else {
                            Toast.makeText(getContext(), "照片截取失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "照片截取失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
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
                view = LayoutInflater.from(getContext()).inflate(R.layout.department_listview_item, null);
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
            return mDeparmentListOne.size();
        }

        //获取每一个外层中的第二层数量
        @Override
        public int getChildrenCount(int i) {
            return mDeparmentListTwo.size();
        }

        //获取每一个外层的实体
        @Override
        public Object getGroup(int i) {
            return null;
        }

        //获取每一个外层中内层的实体
        @Override
        public Object getChild(int i, int i1) {
            return null;
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
            return false;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            OneHolder oneHolder = null;
            if (view == null) {
                oneHolder = new OneHolder();
                view = LayoutInflater.from(getContext()).inflate(R.layout.department_listview_item, null);
                oneHolder.t1 = view.findViewById(R.id.department_id);
                view.setTag(oneHolder);
            } else {
                oneHolder = (OneHolder) view.getTag();
            }
            oneHolder.t1.setText(mDeparmentListOne.get(i));
            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            TwoHolder twoHolder = null;
            if (view == null) {
                twoHolder = new TwoHolder();
                view = LayoutInflater.from(getContext()).inflate(R.layout.child_listview_item, null);
                twoHolder.t2 = view.findViewById(R.id.child_tv);
                view.setTag(twoHolder);
            } else {
                twoHolder = (TwoHolder) view.getTag();
            }
            twoHolder.t2.setText(mDeparmentListTwo.get(i1));
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
