package com.technology.yuyidoctorpad.fragment.HospitalFragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.technology.yuyidoctorpad.HttpTools.HttpTools;
import com.technology.yuyidoctorpad.R;
import com.technology.yuyidoctorpad.activity.WriteHospitalMessageActivity;
import com.technology.yuyidoctorpad.bean.DeparmentSubmitBean.Root;
import com.technology.yuyidoctorpad.lhdUtils.EmailUtils;
import com.technology.yuyidoctorpad.lhdUtils.MyDialog;
import com.technology.yuyidoctorpad.lhdUtils.TelephoneUtils;
import com.technology.yuyidoctorpad.lhdUtils.ToastUtils;

import net.tsz.afinal.http.AjaxParams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * 管理员信息
 */
public class ManagerMessageFragment extends Fragment implements View.OnClickListener {
    private TextView mNext_Btn;
    private EditText mManagerName_Edit, mManagerJob_Edit, mManagerEmail_Edit, mManagerTele_Edit;
    private WriteHospitalMessageActivity mActivity;

    private String mHospitalName, mHospitalGrade, mHospitalJJ, mHospitalTele,mHospitalAddress;
    private File mHospitalFile;
    private List<File> mBookFileList;

    private HttpTools httpTools;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 65) {
                MyDialog.stopDia();
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root != null) {
                        if (root.getCode().equals("0")) {
                            ToastUtils.myToast(getContext(), "提交信息成功，等到审核");
                            mActivity.showSubmitFragment();
                        } else {
                            ToastUtils.myToast(getContext(), root.getMessage());
                        }
                    }

                } else {
                    ToastUtils.myToast(getContext(), "提交管理员信息错误");
                }
            }
        }
    };

    public ManagerMessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager_message, container, false);
        Log.e("onCreateView", "onCreateView");
        Log.e("医院名称", mHospitalName);
        Log.e("医院等级", mHospitalGrade);
        Log.e("医院简介", mHospitalJJ);
        Log.e("医院电弧", mHospitalTele);
        Log.e("医院图片", mHospitalFile.getAbsolutePath());
        for (int i = 0; i < mBookFileList.size(); i++) {
            Log.e("证书" + i, mBookFileList.get(i).getAbsolutePath());
        }
        initUI(view);
        httpTools = HttpTools.getHttpToolsInstance();

        return view;
    }

    private void initUI(View view) {
        mNext_Btn = view.findViewById(R.id.manager_next_btn);
        mNext_Btn.setOnClickListener(this);
        mManagerName_Edit = view.findViewById(R.id.manager_name_edit);
        mManagerJob_Edit = view.findViewById(R.id.manager_job_edit);
        mManagerEmail_Edit = view.findViewById(R.id.manager_email_edit);
        mManagerTele_Edit = view.findViewById(R.id.manager_phone_edit);

        mActivity = (WriteHospitalMessageActivity) getActivity();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == mNext_Btn.getId()) {
            if (!"".equals(mHospitalName) && !"".equals(mHospitalGrade) && !"".equals(mHospitalTele) && !"".equals(mHospitalJJ) && mHospitalFile != null && mBookFileList.size() != 0) {

                if (!"".equals(getManagerName())) {

                    if (!"".equals(getManagerJob())) {

                        if (!"".equals(getManagerEmail())) {

                            if (!"".equals(getManagerTele())) {

                                AjaxParams ajaxParams = new AjaxParams();
                                ajaxParams.put("hospitalName", mHospitalName);
                                ajaxParams.put("gradeString", mHospitalGrade);
                                ajaxParams.put("introduction", mHospitalJJ);
                                ajaxParams.put("tell", mHospitalTele);
                                ajaxParams.put("address", mHospitalAddress);
                                ajaxParams.put("administratorsName", getManagerName());
                                ajaxParams.put("administratorsPosition", getManagerJob());
                                ajaxParams.put("administratorsEmail", getManagerEmail());
                                ajaxParams.put("administratorsTelephone", getManagerTele());
                                try {
                                    ajaxParams.put("hospitalPicture", new FileInputStream(mHospitalFile), "picture");
                                    for (int i = 0; i < mBookFileList.size(); i++) {
                                        ajaxParams.put("bookPicture" + i, new FileInputStream(mBookFileList.get(i)), "certificate");
                                    }
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                    Log.e("文件上传错误", "");
                                }

                                httpTools.submitManagerMsg(handler, ajaxParams);

                                MyDialog.showDialog(getContext());

                            } else {
                                ToastUtils.myToast(getContext(), "请填写正确的电话");
                            }

                        } else {
                            ToastUtils.myToast(getContext(), "请填写正确的邮箱");
                        }
                    } else {
                        ToastUtils.myToast(getContext(), "请填写管理员职位");
                    }
                } else {
                    ToastUtils.myToast(getContext(), "请填写管理员姓名");
                }


            } else {
                ToastUtils.myToast(getContext(), "请检查医院信息是否填写正确");
            }


        }
    }


    private String getManagerName() {
        if (!"".equals(mManagerName_Edit.getText().toString().trim())) {
            return mManagerName_Edit.getText().toString().trim();
        }
        return "";
    }

    private String getManagerJob() {
        if (!"".equals(mManagerJob_Edit.getText().toString().trim())) {
            return mManagerJob_Edit.getText().toString().trim();
        }
        return "";
    }

    private String getManagerEmail() {
        if (EmailUtils.checkEmaile(mManagerEmail_Edit.getText().toString().trim())) {
            return mManagerEmail_Edit.getText().toString().trim();
        }
        return "";
    }

    private String getManagerTele() {
        if (TelephoneUtils.checkPhone(mManagerTele_Edit.getText().toString().trim())) {
            return mManagerTele_Edit.getText().toString().trim();
        }
        return "";
    }

    //上个页面传过来的数据
    public void setHospitalMsg(String name, String grade, String jj, String tele, File hospitalFile, List<File> bookFileList,String address) {
        this.mHospitalName = name;
        this.mHospitalGrade = grade;
        this.mHospitalJJ = jj;
        this.mHospitalTele = tele;
        this.mHospitalFile = hospitalFile;
        this.mBookFileList = bookFileList;
this.mHospitalAddress=address;

    }
}
